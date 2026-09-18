package Coder.Raushan.ApiManagementService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HealthResponseDTO {
    private String serviceName;
    private String serviceDescription;
    private String appStatus;
}
