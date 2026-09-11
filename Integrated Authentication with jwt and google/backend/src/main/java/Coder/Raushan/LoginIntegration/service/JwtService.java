package Coder.Raushan.LoginIntegration.service;

import Coder.Raushan.LoginIntegration.entity.Role;
import Coder.Raushan.LoginIntegration.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry}")
    private Long expiry;

    /**
     * Builds a JWT whose subject is the user's email, carrying their roles,
     * name and login provider as claims so downstream requests don't need an
     * extra DB lookup.
     */
    public String generateToken(User user) {
        Instant now = Instant.now();

        List<String> authorities = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(user.getEmail())
                .claim("authorities", authorities)
                .claim("name", user.getName())
                .claim("provider", user.getProvider().name())
                .build();

        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims));

        return jwt.getTokenValue();
    }
}
