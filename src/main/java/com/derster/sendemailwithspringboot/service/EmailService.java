package com.derster.sendemailwithspringboot.service;

import com.derster.sendemailwithspringboot.entity.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);


    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public ResponseEntity<String> sendEmail (Email email) throws Exception {

        logger.info("Sender email {}", sender);
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Modeste KOUADIO <"+sender+">");
            mailMessage.setTo(email.getRecipient());
            mailMessage.setSubject(email.getSubject());
            mailMessage.setText(email.getMessage());

            javaMailSender.send(mailMessage);

            return new ResponseEntity<>("Email send successfully", HttpStatus.OK);

        }catch (Exception exception){
            throw new Exception("Email sending error !");
        }
    }

    public ResponseEntity<String> senMailWithAttachment(Email email) throws Exception {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("Modeste KOUADIO <"+sender+">");
            mimeMessageHelper.setTo(email.getRecipient());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getMessage());
            // Add attachment
            mimeMessageHelper.addAttachment(Objects.requireNonNull(email.getAttachment().getOriginalFilename()), email.getAttachment());
            // send the mail
            javaMailSender.send(mimeMessage);


            return new ResponseEntity<>("Email send successfully", HttpStatus.OK);

        }catch (Exception exception){
            throw new Exception("Email sending error !");
        }



    }


}
