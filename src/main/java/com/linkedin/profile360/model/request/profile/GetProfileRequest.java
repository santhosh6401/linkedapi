package com.linkedin.profile360.model.request.profile;

import lombok.Data;

@Data
public class GetProfileRequest {
    private String emailId;
    private String batch;
    private String department;
}
