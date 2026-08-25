package Coder.Raushan.SpringSecurityAuthentication.controller;

import Coder.Raushan.SpringSecurityAuthentication.dto.LoginRequestDto;
import Coder.Raushan.SpringSecurityAuthentication.dto.LoginResponseDto;
import Coder.Raushan.SpringSecurityAuthentication.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/auth")
public class LoginController {
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    public LoginController(AuthenticationManager authenticationManager,
                           JwtService jwtService
    )
    {
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto)
    {
        Authentication authenticationReq = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        );
        Authentication authentication
                = authenticationManager.authenticate(authenticationReq);

        String token = jwtService.generateToken(authentication);
        return new LoginResponseDto(token);
    }
}

