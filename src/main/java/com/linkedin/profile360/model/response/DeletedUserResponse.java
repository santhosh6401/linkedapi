package com.linkedin.profile360.model.response;

import com.linkedin.profile360.model.common.Audit;
import lombok.Data;

@Data
public class DeletedUserResponse {
    private String userName;
    private String firstName;
    private String lastName;
    private String designation;
    private String organizationName;
    private String emailId;
    private String phoneNo;
    private Audit audit;
}
