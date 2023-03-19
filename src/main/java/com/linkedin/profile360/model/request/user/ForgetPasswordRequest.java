package com.linkedin.profile360.model.request.user;

import lombok.Data;

@Data
public class ForgetPasswordRequest {
    private String userName;
    private String emailId;
}
