package com.linkedin.profile360.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "config.rest.service")
public class ConfigProperties {
    private String host;
    private int port;
    private String userName;
    private String password;
    private String auth;
    private String starTtls;
    private String nubelaUserMailId;
    private String nubelaSecretKey;
    private String linkedInUrl;
}
