package Coder.Raushan.LoginIntegration.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;

@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {

    @Value("${app.frontend-login-url}")
    private String frontendLoginUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        String message = URLEncoder.encode("Google login failed. Please try again.", StandardCharsets.UTF_8);
        response.sendRedirect(frontendLoginUrl + "?error=" + message);
    }
}
