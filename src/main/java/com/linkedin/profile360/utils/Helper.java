package com.linkedin.profile360.utils;

import com.linkedin.profile360.model.common.Audit;
import com.linkedin.profile360.model.entity.UserEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Properties;


@Service
public class Helper {

    public void sendEmail(String recipient, String[] cc, String subject, String body) throws MessagingException {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("thozhajunction@gmail.com");
        mailSender.setPassword("nnhfviofdszstcqi");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(properties);

        String from = "thozhajunction@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setTo(recipient);
        helper.setCc(cc);

        boolean html = true;
        helper.setText(body, html);

        mailSender.send(message);

    }

    public void setAudit(UserEntity entity){

        if(!Objects.isNull(entity) && Objects.isNull(entity.getAudit())){
            Audit audit = new Audit();
            audit.setCreatedOn(LocalDateTime.now().toString());
            audit.setUpdatedOn(LocalDateTime.now().toString());
            entity.setAudit(audit);
        }
        entity.getAudit().setUpdatedOn(LocalDateTime.now().toString());
    }

}