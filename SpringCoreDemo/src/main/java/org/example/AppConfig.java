package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example")
public class AppConfig {
    @Bean
   public User createUser()
   {
       return new User("Raushan",22);
   }
}
