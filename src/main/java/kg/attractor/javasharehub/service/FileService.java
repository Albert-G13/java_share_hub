package kg.attractor.javasharehub.service;

import jakarta.transaction.Transactional;
import kg.attractor.javasharehub.dto.FileEntityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<FileEntityDto> findAllByOwnerEmail(String email);

    @Transactional
    ResponseEntity<?> processPublicDownload(Long id);

    String generatePrivateLink(Long fileId, String userEmail);

    @Transactional
    ResponseEntity<?> processDownloadByToken(String token);

    List<FileEntityDto> findAllByStatusAndCategoryId(String aPublic, Long categoryId);

    List<FileEntityDto> findAllByStatus(String status);

    @Transactional
    void upload(MultipartFile file, String email, Long categoryId, String status);
}
