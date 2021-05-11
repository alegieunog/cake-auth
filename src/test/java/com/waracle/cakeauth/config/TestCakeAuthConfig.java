package com.waracle.cakeauth.config;

import com.waracle.cakeauth.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class TestCakeAuthConfig extends CakeAuthConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

}