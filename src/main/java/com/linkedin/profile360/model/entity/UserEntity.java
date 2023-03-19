package com.linkedin.profile360.model.entity;


import com.linkedin.profile360.model.common.Audit;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class UserEntity {
    @Id
    private String id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String designation;
    private String organizationName;
    private String emailId;
    private String phoneNo;
    private Audit audit;
}
