package com.waracle.cakeauth.service;

import com.waracle.cakeauth.config.TestCakeAuthConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestCakeAuthConfig.class})
public class UserDetailsServiceTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void loadUserByUsernameForValidUser(){
        final UserDetails cakeUser = userDetailsService.loadUserByUsername("cakeuser");
        assertThat(cakeUser, not(is(nullValue())));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameForInvalidUser(){
        final UserDetails cakeUser = userDetailsService.loadUserByUsername("badUser");
    }
}