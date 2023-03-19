package com.linkedin.profile360.model.request.user;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String userName;
    private String emailId;
    private String password;
    private String confirmPassword;
}
