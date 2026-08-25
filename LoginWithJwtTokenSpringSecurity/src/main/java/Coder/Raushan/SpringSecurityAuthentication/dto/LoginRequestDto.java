package Coder.Raushan.SpringSecurityAuthentication.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;


@Setter
@Getter

public class LoginRequestDto {
    private String username;
    private String password;
}
