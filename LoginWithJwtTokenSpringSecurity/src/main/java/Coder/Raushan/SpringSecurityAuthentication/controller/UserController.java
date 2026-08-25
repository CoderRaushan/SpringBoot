package Coder.Raushan.SpringSecurityAuthentication.controller;

import Coder.Raushan.SpringSecurityAuthentication.dto.UserRegisterRequestDto;
import Coder.Raushan.SpringSecurityAuthentication.dto.UserRegisterResponseDto;
import Coder.Raushan.SpringSecurityAuthentication.service.AuthService;
import jakarta.security.auth.message.config.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private AuthService authService;
    public UserController(AuthService authService)
    {
        this.authService=authService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> registerUser(
            @RequestBody UserRegisterRequestDto userRegisterRequestDto
    ){
        UserRegisterResponseDto userRegisterResponseDto = authService.registerUser(userRegisterRequestDto);
        return ResponseEntity.ok(userRegisterResponseDto);
    }

    @GetMapping("/token")
    public CsrfToken getCsrfToken(CsrfToken csrfToken)
    {
        return csrfToken;
    }
} 
