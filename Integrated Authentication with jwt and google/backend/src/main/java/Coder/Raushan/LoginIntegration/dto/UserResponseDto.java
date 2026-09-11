package Coder.Raushan.LoginIntegration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    private String name;
    private String email;
    private String provider;
}
