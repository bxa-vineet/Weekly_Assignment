package com.auth_service.config;


import com.auth_service.entity.Role;
import com.auth_service.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initRoles(AppUserRepository appUserRepository) {
        return args -> {
            if (appUserRepository.findByName("ADMIN").isEmpty()) appUserRepository.save(new Role("ADMIN"));
            if (appUserRepository.findByName("TRADER").isEmpty()) appUserRepository.save(new Role("TRADER"));
            if (appUserRepository.findByName("VIEWER").isEmpty()) appUserRepository.save(new Role("VIEWER"));
        };
    }
}

