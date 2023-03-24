package com.linkedin.profile360.model.entity;


import com.linkedin.profile360.model.common.Audit;
import com.linkedin.profile360.model.common.Education;
import com.linkedin.profile360.model.common.Experience;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "profile")
public class ProfileEntity {
    @Id
    private String id;
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
