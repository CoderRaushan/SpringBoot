package Coder.Raushan.ApiManagementService.Controller;

import Coder.Raushan.ApiManagementService.dto.HealthResponseDTO;
import Coder.Raushan.ApiManagementService.service.HealthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/health")
public class HealthController {
    public final HealthService healthService;
    public HealthController(HealthService healthService)
    {
        this.healthService=healthService;
    }
    @RequestMapping
    public ResponseEntity<HealthResponseDTO> health()
    {
        String dbStatus = healthService.getHealth();
        return ResponseEntity.ok(new HealthResponseDTO("UP",dbStatus, LocalDateTime.now()));
    }
}
