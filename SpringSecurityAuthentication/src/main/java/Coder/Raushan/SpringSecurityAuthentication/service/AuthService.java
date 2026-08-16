package Coder.Raushan.SpringSecurityAuthentication.service;


import Coder.Raushan.SpringSecurityAuthentication.Entity.Role;
import Coder.Raushan.SpringSecurityAuthentication.Entity.User;
import Coder.Raushan.SpringSecurityAuthentication.dto.UserRegisterRequestDto;
import Coder.Raushan.SpringSecurityAuthentication.dto.UserRegisterResponseDto;
import Coder.Raushan.SpringSecurityAuthentication.repository.RoleRepository;
import Coder.Raushan.SpringSecurityAuthentication.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.desktop.UserSessionEvent;

@Service
public class AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, RoleRepository roleRepository)
    {
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
//        this.passwordEncoder=passwordEncoder;
    }
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto registerRequestDto)
    {
            User user = new User();

            user.setUsername(registerRequestDto.getUsername());

            String encodedPassword= passwordEncoder.encode(registerRequestDto.getPassword());

            user.setPassword(encodedPassword);

            user.setEnabled(true);

            Role role = roleRepository.findByName("ROLE_USER").get();

            user.getRoles().add(role);

            userRepository.save(user);

            UserRegisterResponseDto responseDto = new UserRegisterResponseDto();

            responseDto.setUsername(user.getUsername());
            responseDto.setMessage("User Registered Successfully!");

            return responseDto;

    }
}
