package com.linkedin.profile360.model.response;

import com.linkedin.profile360.model.common.Audit;
import com.linkedin.profile360.model.common.Education;
import com.linkedin.profile360.model.common.Experience;
import lombok.Data;

import java.util.List;

@Data
public class ProfileResponse {
    private String firstname;
    private String lastname;
    private String department;
    private String batch;
    private String mobileNo;
    private String emailId;
    private String linkedInUrl;
    private String linkedInProfileUrl;
    private String currentOccupation;
    private String workedCompaniesCount;
    private List<Experience> companyExperienceDetails;
    private List<Education> educationDetails;
    private Audit audit;
}
