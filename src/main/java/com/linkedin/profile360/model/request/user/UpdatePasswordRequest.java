package com.linkedin.profile360.model.request.user;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String userName;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
