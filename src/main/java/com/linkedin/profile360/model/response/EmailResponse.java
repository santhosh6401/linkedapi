package com.linkedin.profile360.model.response;


import com.linkedin.profile360.model.common.Audit;
import lombok.Data;

@Data
public class EmailResponse {
    private String from;
    private String to;
    private String[] cc;
    private String subject;
    private String body;
    private Audit audit;
    private String createdOn;
}
