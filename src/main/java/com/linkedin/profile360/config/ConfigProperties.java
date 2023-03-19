package com.linkedin.profile360.config;


import lombok.Data;

@Data
public class ConfigProperties {
    private String host;
    private String port;
    private String userName;
    private String password;
    private String auth;
    private String starTtls;
    private String nubelaUserMailId;
    private String nubelaSecretKey;
}
