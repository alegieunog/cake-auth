package com.waracle.cakeauth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@PropertySource("classpath:cake-auth.properties")
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${cake.username}")
    private String username;

    @Value("${cake.password}")
    private String password;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!username.equals(s)) {
            throw new UsernameNotFoundException("Invalid username");
        }

        return new User(username, password, getAuthorities());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN"));
    }
}
