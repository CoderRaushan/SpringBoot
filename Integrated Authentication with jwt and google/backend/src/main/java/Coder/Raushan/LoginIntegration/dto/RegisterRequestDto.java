package Coder.Raushan.LoginIntegration.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;
}
