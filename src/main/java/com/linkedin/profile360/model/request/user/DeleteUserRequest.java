package com.linkedin.profile360.model.request.user;

import lombok.Data;

@Data
public class DeleteUserRequest {
    private String userName;
    private String reason;
    private String password;
}
