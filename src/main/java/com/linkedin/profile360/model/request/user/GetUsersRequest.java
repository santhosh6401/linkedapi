package com.linkedin.profile360.model.request.user;

import lombok.Data;

@Data
public class GetUsersRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String designation;
    private String organizationName;
    private String emailId;
    private String phoneNo;
}
