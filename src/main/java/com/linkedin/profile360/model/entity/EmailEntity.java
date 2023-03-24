package com.linkedin.profile360.model.entity;

import com.linkedin.profile360.model.common.Audit;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "email")
public class EmailEntity {
    private String from;
    private String to;
    private String[] cc;
    private String subject;
    private String body;
    private Audit audit;
}
