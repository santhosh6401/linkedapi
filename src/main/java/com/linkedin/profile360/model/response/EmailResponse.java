package com.linkedin.profile360.model.response;


import com.linkedin.profile360.model.common.Audit;
import lombok.Data;

import java.util.List;

@Data
public class EmailResponse {
    private String from;
    private String to;
    private List<String> cc;
    private String body;
    private Audit audit;
}
