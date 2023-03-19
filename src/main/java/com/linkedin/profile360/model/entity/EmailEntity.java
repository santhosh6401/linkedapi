package com.linkedin.profile360.model.entity;

import com.linkedin.profile360.model.common.Audit;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "email")
public class EmailEntity {
    private String from;
    private String to;
    private List<String> cc;
    private String body;
    private Audit audit;
}
