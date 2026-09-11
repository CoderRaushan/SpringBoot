package Coder.Raushan.LoginIntegration.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public static final String COOKIE_NAME = "token";

    @Value("${jwt.expiry}")
    private long jwtExpirySeconds;

    /**
     * cookie.secure is driven by app.cookie-secure so it's easy to flip to
     * true once the app is served over HTTPS in production.
     */
    @Value("${app.cookie-secure:false}")
    private boolean cookieSecure;

    public ResponseCookie buildTokenCookie(String token) {
        return ResponseCookie.from(COOKIE_NAME, token)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(jwtExpirySeconds)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie buildExpiredTokenCookie() {
        return ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
    }
}
