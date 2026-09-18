package Coder.Raushan.AnalyticsService.Controller;

import Coder.Raushan.AnalyticsService.dto.HealthResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    private String serviceName;
    private String serviceDescription;

    public HealthController(@Value("${info.app.name}")String Name,
                            @Value("${info.app.description}")String Description
    )
    {
        serviceName=Name;
        serviceDescription=Description;
    }
    @RequestMapping
    public ResponseEntity<HealthResponseDTO> health()
    {
        return ResponseEntity.ok(new HealthResponseDTO(serviceName,serviceDescription,"up"));
    }
}
