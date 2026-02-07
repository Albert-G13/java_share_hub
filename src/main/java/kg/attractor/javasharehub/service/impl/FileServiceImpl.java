package kg.attractor.javasharehub.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.javasharehub.dto.FileEntityDto;
import kg.attractor.javasharehub.exceptions.CategoryNotFoundException;
import kg.attractor.javasharehub.exceptions.IllegalFileException;
import kg.attractor.javasharehub.exceptions.MyFileNotFoundException;
import kg.attractor.javasharehub.exceptions.UserNotFoundException;
import kg.attractor.javasharehub.model.Category;
import kg.attractor.javasharehub.model.FileEntity;
import kg.attractor.javasharehub.model.User;
import kg.attractor.javasharehub.repository.CategoryRepository;
import kg.attractor.javasharehub.repository.FileRepository;
import kg.attractor.javasharehub.repository.UserRepository;
import kg.attractor.javasharehub.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<FileEntityDto> findAllByOwnerEmail(String email) {
        log.info("Запрос списка файлов для пользователя: {}", email);
        return fileRepository.findAllByUser_Email(email).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ResponseEntity<?> processPublicDownload(Long id) {
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Файл не найден"));

        if (!"public".equals(file.getStatus())) {
            log.warn("Попытка несанкционированного доступа к приватному файлу ID: {}", id);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Этот файл приватный");
        }

        file.setDownloadCount(file.getDownloadCount() + 1);
        fileRepository.save(file);

        log.info("Публичный файл {} скачан. Всего скачиваний: {}", file.getName(), file.getDownloadCount());

        return downloadFile(file.getPath(), "public", MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    public String generatePrivateLink(Long fileId, String userEmail) {
        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(MyFileNotFoundException::new);

        if (!file.getUser().getEmail().equals(userEmail)) {
            log.error("Попытка доступа к чужому файлу юзером: {}", userEmail);
            throw new IllegalFileException();
        }

        String token = UUID.randomUUID().toString();
        file.setDownloadToken(token);
        fileRepository.save(file);

        log.info("Сгенерирован токен для файла ID: {}", fileId);
        return "/download?token=" + token;
    }
    @Transactional
    @Override
    public ResponseEntity<?> processDownloadByToken(String token) {
        FileEntity file = fileRepository.findByDownloadToken(token)
                .orElseThrow(() -> {
                    log.warn("Попытка скачать файл по неверному токену: {}", token);
                    return new ResponseStatusException(HttpStatus.GONE, "Ссылка недействительна");
                });

        file.setDownloadToken(null);
        file.setDownloadCount(file.getDownloadCount() + 1);
        fileRepository.save(file);

        String subDir = file.getStatus().equals("private") ? "private" : "public";

        log.info("Файл {} успешно отдан по одноразовой ссылке", file.getName());
        return downloadFile(file.getPath(), subDir, MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    public List<FileEntityDto> findAllByStatusAndCategoryId(String status, Long categoryId) {
        return fileRepository.findAllByStatusAndCategory_Id(status, categoryId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FileEntityDto> findAllByStatus(String status) {
        return fileRepository.findAllByStatus(status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private FileEntityDto mapToDto(FileEntity entity) {
        return FileEntityDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .path(entity.getPath())
                .status(entity.getStatus())
                .downloadCount(entity.getDownloadCount())
                .build();
    }

    @Transactional
    @Override
    public void upload(MultipartFile file, String email, Long categoryId, String status) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        String subDir = status.equals("private") ? "private" : "public";
        String savedPath = saveUploadFile(file, subDir);

        FileEntity entity = FileEntity.builder()
                .name(file.getOriginalFilename())
                .path(savedPath)
                .status(status)
                .user(user)
                .category(category)
                .downloadCount(0L)
                .build();

        fileRepository.save(entity);
        log.info("Пользователь {} загрузил файл: {}", email, file.getOriginalFilename());
    }

    @SneakyThrows
    public String saveUploadFile(MultipartFile file, String subDir) {
        String uuidFile = UUID.randomUUID().toString();
        String fileName = uuidFile + "_" + file.getOriginalFilename();
        Path pathDir = Paths.get("data/" + subDir);
        Files.createDirectories(pathDir);
        Path filePath = pathDir.resolve(fileName);

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(file.getBytes());
        }
        return fileName;
    }

    public ResponseEntity<?> downloadFile(String fileName, String subDir, MediaType mediaType) {
        try {
            Path path = Paths.get("data/" + subDir + "/" + fileName);
            byte[] data = Files.readAllBytes(path);
            Resource resource = new ByteArrayResource(data);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }

}
