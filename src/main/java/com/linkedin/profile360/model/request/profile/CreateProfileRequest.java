package com.linkedin.profile360.model.request.profile;

import com.linkedin.profile360.model.common.Education;
import com.linkedin.profile360.model.common.Experience;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateProfileRequest {
    private String firstname;
    private String lastname;
    private String department;
    private String batch;
    private String mobileNo;
    private String emailId;
    private String linkedInUrl;

}
