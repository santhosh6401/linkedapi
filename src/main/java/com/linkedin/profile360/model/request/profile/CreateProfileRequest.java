package com.linkedin.profile360.model.request.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileRequest {
    private String firstname;
    private String lastname;
    private String department;
    private String batch;
    private String mobileNo;
    private String emailId;
    private String linkedInUrl;
    private String linkedInProfileUrl;

}
