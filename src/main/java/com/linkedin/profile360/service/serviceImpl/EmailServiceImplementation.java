package com.linkedin.profile360.service.serviceImpl;

import com.linkedin.profile360.model.request.email.EmailRequest;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.EmailResponse;
import com.linkedin.profile360.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImplementation implements EmailService {

    @Override
    public CommonResponse sendEmail(String emailId, EmailRequest request) {
        return null;
    }

    @Override
    public CommonResponse sendEmailAll(EmailRequest request) {
        return null;
    }

    @Override
    public List<EmailResponse> getHistory() {
        return null;
    }
}
