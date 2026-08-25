package in.strikes.oauthDemo.controller;

import in.strikes.oauthDemo.entity.User;
import in.strikes.oauthDemo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return """
                Public Home
                
                Login Using : localhost:8080/oauth2/authorization/google
                """;
    }

    @GetMapping("/profile")
    public Map<String, Object> profile(
            @AuthenticationPrincipal OidcUser oidcUser) {

        User user = userService.findByProviderAndSubject(
                        "google", oidcUser.getSubject())
                .orElseThrow();

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "internalUserId",
                user.getId()
        );

        response.put(
                "provider",
                user.getProvider()
        );

        response.put(
                "subject",
                oidcUser.getSubject()
        );

        response.put(
                "name",
                oidcUser.getClaimAsString("name")
        );

        response.put(
                "email",
                oidcUser.getClaimAsString("email")
        );

        return response;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        // Session destroy
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        // JSESSIONID cookie delete
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "Logged out successfully. Now login again using: " +
                "http://localhost:8080/oauth2/authorization/google";
    }
}