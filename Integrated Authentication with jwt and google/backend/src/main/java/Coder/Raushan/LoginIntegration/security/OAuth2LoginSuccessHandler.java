package Coder.Raushan.LoginIntegration.security;

import Coder.Raushan.LoginIntegration.entity.User;
import Coder.Raushan.LoginIntegration.repository.UserRepository;
import Coder.Raushan.LoginIntegration.service.JwtService;
import Coder.Raushan.LoginIntegration.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CookieUtil cookieUtil;

    @Value("${app.frontend-oauth-redirect}")
    private String frontendRedirectUrl;

    public OAuth2LoginSuccessHandler(UserRepository userRepository, JwtService jwtService, CookieUtil cookieUtil) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String email = oidcUser.getClaimAsString("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User should have been created by CustomOidcUserService"));

        String token = jwtService.generateToken(user);
        ResponseCookie cookie = cookieUtil.buildTokenCookie(token);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        response.sendRedirect(frontendRedirectUrl);
    }
}
