package com.linkedin.profile360.service;

import com.linkedin.profile360.model.request.email.EmailRequest;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.EmailResponse;

import java.util.List;

public interface EmailService {
    CommonResponse sendEmail(String emailId, EmailRequest request);

    CommonResponse sendEmailAll(EmailRequest request);

    List<EmailResponse> getHistory();
}
