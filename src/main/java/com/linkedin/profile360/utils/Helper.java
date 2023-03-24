package com.linkedin.profile360.utils;

import com.linkedin.profile360.config.ConfigProperties;
import com.linkedin.profile360.model.common.Audit;
import com.linkedin.profile360.model.entity.EmailEntity;
import com.linkedin.profile360.model.entity.ProfileEntity;
import com.linkedin.profile360.model.entity.UserEntity;
import com.linkedin.profile360.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Properties;


@Service
public class Helper {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ConfigProperties configProperties;

    public void sendEmail(String recipient, String[] cc, String subject, String body) throws MessagingException {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configProperties.getHost());
        mailSender.setPort(configProperties.getPort());
        mailSender.setUsername(configProperties.getUserName());
        mailSender.setPassword(configProperties.getPassword());

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", configProperties.getAuth());
        properties.setProperty("mail.smtp.starttls.enable", configProperties.getStarTtls());

        mailSender.setJavaMailProperties(properties);

        String from = configProperties.getUserName();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setTo(recipient);
        if(Objects.nonNull(cc)){
            helper.setCc(cc);
        }
        helper.setText(body);
        mailSender.send(message);

        emailRepository.save(createHistory(recipient, cc, subject, body));

    }

    private EmailEntity createHistory(String recipient, String[] cc, String subject, String body) {
        EmailEntity entity = new EmailEntity();
        entity.setFrom(configProperties.getUserName());
        entity.setTo(recipient);
        entity.setSubject(subject);
        entity.setBody(body);
        entity.setCc(cc);
        return entity;
    }

    public void setAudit(UserEntity entity) {

        if (!Objects.isNull(entity) && Objects.isNull(entity.getAudit())) {
            Audit audit = new Audit();
            audit.setCreatedOn(LocalDateTime.now().toString());
            audit.setUpdatedOn(LocalDateTime.now().toString());
            entity.setAudit(audit);
        }
        entity.getAudit().setUpdatedOn(LocalDateTime.now().toString());
    }

    public void setAudit(EmailEntity entity) {

        if (!Objects.isNull(entity) && Objects.isNull(entity.getAudit())) {
            Audit audit = new Audit();
            audit.setCreatedOn(LocalDateTime.now().toString());
            audit.setUpdatedOn(LocalDateTime.now().toString());
            entity.setAudit(audit);
        }
        entity.getAudit().setUpdatedOn(LocalDateTime.now().toString());
    }

    public void setAudit(ProfileEntity entity) {

        if (!Objects.isNull(entity) && Objects.isNull(entity.getAudit())) {
            Audit audit = new Audit();
            audit.setCreatedOn(LocalDateTime.now().toString());
            audit.setUpdatedOn(LocalDateTime.now().toString());
            entity.setAudit(audit);
        }
        entity.getAudit().setUpdatedOn(LocalDateTime.now().toString());
    }
}