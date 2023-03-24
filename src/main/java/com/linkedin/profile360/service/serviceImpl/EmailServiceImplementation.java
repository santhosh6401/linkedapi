package com.linkedin.profile360.service.serviceImpl;

import com.linkedin.profile360.model.entity.EmailEntity;
import com.linkedin.profile360.model.entity.ProfileEntity;
import com.linkedin.profile360.model.request.email.EmailRequest;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.EmailResponse;
import com.linkedin.profile360.repository.EmailRepository;
import com.linkedin.profile360.repository.ProfileRepository;
import com.linkedin.profile360.service.EmailService;
import com.linkedin.profile360.utils.Helper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmailServiceImplementation implements EmailService {

    @Autowired
    private Helper helper;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public CommonResponse sendEmail(String emailId, EmailRequest request) throws MessagingException {
        CommonResponse response = new CommonResponse();
        if (Objects.nonNull(request)) {
            helper.sendEmail(emailId, null, request.getSubject(), request.getRequestBody());
            response.setResult("MAIL SENT SUCCESSFULLY - " + emailId);
            return response;
        }
        response.setResult("REQUEST IS EMPTY OR NULL");
        return response;
    }

    @Override
    public CommonResponse sendEmailAll(String batch, EmailRequest request) {
        CommonResponse response = new CommonResponse();
        if (Objects.nonNull(request)) {
            List<ProfileEntity> entities = profileRepository.findByBatch(batch);
            if (entities.isEmpty()) {
                response.setResult("BATCH IS NOT FOUND");
            }
            entities.forEach(profileEntity -> {
                if (Objects.nonNull(profileEntity.getEmailId())) {
                    try {
                        helper.sendEmail(profileEntity.getEmailId(), null, request.getSubject(), request.getRequestBody());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        response.setResult("REQUEST IS EMPTY OR NULL");
        return response;
    }

    @Override
    public List<EmailResponse> getHistory() {
        List<EmailEntity> entities = emailRepository.findAll();
        List<EmailResponse> emailResponses = new ArrayList<>();
        entities.forEach(emailEntity -> {
            EmailResponse emailResponse = new EmailResponse();
            BeanUtils.copyProperties(emailEntity, emailResponse);
            emailResponses.add(emailResponse);
        });
        return emailResponses;
    }
}
