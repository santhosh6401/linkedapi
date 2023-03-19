package com.linkedin.profile360.model.request.user;

import lombok.Data;

@Data
public class SignInRequest {
    private String userName;
    private String password;
}
