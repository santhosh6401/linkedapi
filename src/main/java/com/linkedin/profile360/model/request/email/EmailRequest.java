package com.linkedin.profile360.model.request.email;

import lombok.Data;

@Data
public class EmailRequest {
    private String subject;
    private String requestBody;
}
