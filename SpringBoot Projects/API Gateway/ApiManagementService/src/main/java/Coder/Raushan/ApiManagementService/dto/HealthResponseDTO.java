package Coder.Raushan.ApiManagementService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class HealthResponseDTO {
    private String serviceStatus;
    private String dbStatus;
    private LocalDateTime time;
}
