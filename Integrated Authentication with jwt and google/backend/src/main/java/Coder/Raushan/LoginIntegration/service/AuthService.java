package Coder.Raushan.LoginIntegration.service;

import Coder.Raushan.LoginIntegration.entity.AuthProvider;
import Coder.Raushan.LoginIntegration.entity.Role;
import Coder.Raushan.LoginIntegration.entity.User;
import Coder.Raushan.LoginIntegration.dto.RegisterRequestDto;
import Coder.Raushan.LoginIntegration.dto.RegisterResponseDto;
import Coder.Raushan.LoginIntegration.exception.EmailAlreadyExistsException;
import Coder.Raushan.LoginIntegration.repository.RoleRepository;
import Coder.Raushan.LoginIntegration.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto) {

        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("An account with this email already exists");
        }

        User user = new User();
        user.setEmail(registerRequestDto.getEmail());
        user.setName(registerRequestDto.getName());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setProvider(AuthProvider.LOCAL);
        user.setEnabled(true);

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("ROLE_USER not seeded"));
        user.getRoles().add(role);

        userRepository.save(user);

        return new RegisterResponseDto(user.getName(), user.getEmail(), "User registered successfully!");
    }
}
