package com.derster.sendemailwithspringboot;

import com.derster.sendemailwithspringboot.entity.Email;
import com.derster.sendemailwithspringboot.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailSendController {
    private final EmailService emailService;


    public EmailSendController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendMail(@RequestBody Email email) throws Exception {
        return emailService.sendEmail(email);
    }

    @PostMapping("/sendEmailWithAttachment")
    public ResponseEntity<String> sendEmailWithAttachment(@ModelAttribute Email email) throws Exception {
        return emailService.senMailWithAttachment(email);
    }
}
