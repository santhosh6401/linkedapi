package com.linkedin.profile360.model.entity;

import com.linkedin.profile360.model.common.Audit;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "deleted-user")
public class DeletedUserEntity {
    private String userName;
    private String firstName;
    private String lastName;
    private String designation;
    private String reason;
    private String organizationName;
    private String emailId;
    private String phoneNo;
    private Audit audit;
}
