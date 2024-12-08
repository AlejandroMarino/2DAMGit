package org.marino.server.domain.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class ServicesEmail {

    private final JavaMailSender mailSender;

    public ServicesEmail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(String to, String activationCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Welcome to Pagao");

        String emailBody = "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<p>Hello! Thank you for register to Pagao.</p>" +
                "<p>Please, click the button below to activate your account:</p>" +
                "<a href=\"http://localhost:8080/pagao/login/activation?code=" + activationCode + "\" " +
                "style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: #000000; " +
                "background-color: #A06E1D; text-decoration: none; border-radius: 5px; border: none; " +
                "font-weight: bold; text-align: center;\">Activate Account</a>" +
                "</body>" +
                "</html>";

        helper.setText(emailBody, true);

        mailSender.send(message);
    }
}
