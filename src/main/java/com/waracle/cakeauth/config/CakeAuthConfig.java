package com.waracle.cakeauth.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultBackoffStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import({SecurityConfig.class, AuthorizationServerConfig.class})
public class CakeAuthConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer
                = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource("cake-auth.properties"));
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public RestTemplate restTemplate(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(20);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setConnectionReuseStrategy(new DefaultConnectionReuseStrategy())
                .setUserAgent("Customer")
                .disableAutomaticRetries()
                .setConnectionBackoffStrategy(new DefaultBackoffStrategy())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
}
