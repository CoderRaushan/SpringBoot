package Coder.Raushan.GatewayService.controller;

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
 public ResponseEntity<String> health()
 {

 }
}
