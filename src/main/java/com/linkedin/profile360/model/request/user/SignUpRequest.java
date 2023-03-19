package com.linkedin.profile360.model.request.user;

import lombok.Data;

@Data
public class SignUpRequest {
    private String userName;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String designation;
    private String organizationName;
    private String emailId;
    private String phoneNo;
}
