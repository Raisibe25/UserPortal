package com.user.config;

import com.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    @Profile("!test")   // this bean won’t be created in the "test" profile
    CommandLineRunner seed(UserRepository repo, PasswordEncoder enc) {
        return args -> {
            // same logic
        };
    }
}