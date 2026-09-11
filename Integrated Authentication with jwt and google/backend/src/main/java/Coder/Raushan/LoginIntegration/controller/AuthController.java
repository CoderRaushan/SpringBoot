package Coder.Raushan.LoginIntegration.controller;

import Coder.Raushan.LoginIntegration.dto.LoginRequestDto;
import Coder.Raushan.LoginIntegration.dto.RegisterRequestDto;
import Coder.Raushan.LoginIntegration.dto.RegisterResponseDto;
import Coder.Raushan.LoginIntegration.dto.UserResponseDto;
import Coder.Raushan.LoginIntegration.entity.CustomUserDetails;
import Coder.Raushan.LoginIntegration.entity.User;
import Coder.Raushan.LoginIntegration.service.AuthService;
import Coder.Raushan.LoginIntegration.service.JwtService;
import Coder.Raushan.LoginIntegration.util.CookieUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtService jwtService;
    private final CookieUtil cookieUtil;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService,
                           JwtService jwtService, CookieUtil cookieUtil) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtService = jwtService;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto responseDto = authService.registerUser(registerRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        Authentication authenticationReq = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationReq);

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = principal.getUser();

        String token = jwtService.generateToken(user);
        ResponseCookie cookie = cookieUtil.buildTokenCookie(token);

        UserResponseDto responseDto = new UserResponseDto(user.getName(), user.getEmail(), user.getProvider().name());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = cookieUtil.buildExpiredTokenCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> me(@AuthenticationPrincipal Jwt jwt) {
        UserResponseDto responseDto = new UserResponseDto(
                jwt.getClaimAsString("name"),
                jwt.getSubject(),
                jwt.getClaimAsString("provider")
        );
        return ResponseEntity.ok(responseDto);
    }
}
