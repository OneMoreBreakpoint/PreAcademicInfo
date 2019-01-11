package utils;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import bussiness_layer.dto.EmailNotificationDto;

/**
 * Send an email.
 */
@Component("emailSender")
@EnableAutoConfiguration
@EnableAsync
public class EmailSender {

    private final JavaMailSender javaMailSender;

    public EmailSender(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(final List<EmailNotificationDto> notificationList) {
        if (notificationList.size() > 0) {
            notificationList.forEach(this::sendEmail);
        }
    }

    private void sendEmail(EmailNotificationDto emailNotificationDto) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailNotificationDto.getEmailAddress());
        message.setSubject(EmailMessage.getSubject(emailNotificationDto.getCourseName()));
        message.setText(EmailMessage.getContent(emailNotificationDto));
        javaMailSender.send(message);
    }

}
