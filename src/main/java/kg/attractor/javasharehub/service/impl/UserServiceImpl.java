package kg.attractor.javasharehub.service.impl;

import kg.attractor.javasharehub.dto.FileEntityDto;
import kg.attractor.javasharehub.dto.UserDto;
import kg.attractor.javasharehub.dto.UserRegistrationDto;
import kg.attractor.javasharehub.exceptions.RoleNotFoundException;
import kg.attractor.javasharehub.exceptions.UserAlreadyExistsException;
import kg.attractor.javasharehub.exceptions.UserNotFoundException;
import kg.attractor.javasharehub.model.Role;
import kg.attractor.javasharehub.model.User;
import kg.attractor.javasharehub.repository.RoleRepository;
import kg.attractor.javasharehub.repository.UserRepository;
import kg.attractor.javasharehub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto findByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        List<FileEntityDto> fileDtos = user.getFiles().stream()
                .map(file -> FileEntityDto.builder()
                        .id(file.getId())
                        .name(file.getName())
                        .path(file.getPath())
                        .status(file.getStatus())
                        .downloadCount(file.getDownloadCount())
                        .build())
                .collect(Collectors.toList());

        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .files(fileDtos)
                .build();
    }

    @Override
    public void register(UserRegistrationDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        Role userRole = roleRepository.findByRole("USER")
                .orElseThrow(RoleNotFoundException::new);

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(userRole);
        user.setEnabled(true);

        userRepository.save(user);
    }
}
