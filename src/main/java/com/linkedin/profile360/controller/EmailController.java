package com.linkedin.profile360.controller;


import com.linkedin.profile360.model.request.email.EmailRequest;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.EmailResponse;
import com.linkedin.profile360.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public CommonResponse sendEmail(String emailId, EmailRequest request) throws MessagingException {
        return emailService.sendEmail(emailId, request);
    }

    @PostMapping("/send/all")
    public CommonResponse sendAll(@RequestParam String batch, EmailRequest request) {
        return emailService.sendEmailAll(batch, request);
    }

    @GetMapping("/history")
    public List<EmailResponse> emailHistory() {
        return emailService.getHistory();
    }
}
