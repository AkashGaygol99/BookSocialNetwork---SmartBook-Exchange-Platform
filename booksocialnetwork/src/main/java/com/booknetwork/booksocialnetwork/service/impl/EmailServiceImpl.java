package com.booknetwork.booksocialnetwork.service.impl;

import com.booknetwork.booksocialnetwork.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Override
    public void sendActivationEmail(String to, String activationCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String subject = "Activate Your Book Social Network Account";

            String activationLink = "http://localhost:4200/activate?code=" + activationCode;

            String content =
                    "<h2>Welcome to Book Social Network!</h2>" +
                            "<p>Click the link below to activate your account:</p>" +
                            "<a href='" + activationLink + "' style='padding:10px 20px; background:#4CAF50; color:white; text-decoration:none;'>Activate Account</a>" +
                            "<br><br>" +
                            "<p>If you didn’t request this, please ignore this email.</p>";

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom(mailUsername);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send activation email: " + e.getMessage());
        }
    }
}