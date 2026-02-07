package kg.attractor.javasharehub.service;

import kg.attractor.javasharehub.dto.UserDto;
import kg.attractor.javasharehub.dto.UserRegistrationDto;

public interface UserService {
    UserDto findByEmail(String email);

    void register(UserRegistrationDto dto);
}
