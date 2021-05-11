package com.waracle.cakeauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.DefaultUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String CLIENT_ID = "client";
    private static final String CLIENT_SECRET = "secret";
    private static final String PASSWORD = "password";
    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String IMPLICIT = "implicit";
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final String TRUST = "trust";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 7200;
    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 21600;
    private static final String RESOURCE_ID = "resource_id";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                .tokenKeyAccess("denyAll()")
                .checkTokenAccess("hasAuthority('USER')")
                .passwordEncoder(passwordEncoder)
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

        configurer.inMemory()
                .withClient(CLIENT_ID)
                .secret(passwordEncoder.encode(CLIENT_SECRET))
                .authorizedGrantTypes(PASSWORD, REFRESH_TOKEN, CLIENT_CREDENTIALS, AUTHORIZATION_CODE, IMPLICIT)
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                .authorities("USER","ADMIN")
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() throws Exception {
        TokenStore tokenStore = new InMemoryTokenStore();
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(inMemoryClientDetailsService());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }

    @Bean
    public ClientDetailsService inMemoryClientDetailsService() throws Exception{
        return new InMemoryClientDetailsServiceBuilder()
                .withClient(CLIENT_ID)
                .secret(passwordEncoder.encode(CLIENT_SECRET))
                .authorizedGrantTypes(PASSWORD, REFRESH_TOKEN, CLIENT_CREDENTIALS, AUTHORIZATION_CODE, IMPLICIT)
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                .authorities("USER","ADMIN")
                .resourceIds(RESOURCE_ID)
                .and()
                .build();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenServices(tokenServices())
                .userApprovalHandler(new DefaultUserApprovalHandler())
                .authenticationManager(authenticationManager)
                .requestFactory(requestFactory());
    }

    @Bean
    public OAuth2RequestFactory requestFactory() throws Exception {
        return new DefaultOAuth2RequestFactory(inMemoryClientDetailsService());
    }
}
