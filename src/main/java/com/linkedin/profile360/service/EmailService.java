package com.linkedin.profile360.service;

import com.linkedin.profile360.model.request.email.EmailRequest;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.EmailResponse;

import javax.mail.MessagingException;
import java.util.List;

public interface EmailService {
    CommonResponse sendEmail(String emailId, EmailRequest request) throws MessagingException;

    CommonResponse sendEmailAll(String batch, EmailRequest request);

    List<EmailResponse> getHistory();
}
