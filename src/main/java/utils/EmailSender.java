package utils;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Send an email.
 */
@Component("emailSender")
@EnableAutoConfiguration
public class EmailSender {

    private final JavaMailSender javaMailSender;

    public EmailSender(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(final List<String> toEmails, final String subject, final String content) {
        if (toEmails.size() > 0) {
            final SimpleMailMessage message = new SimpleMailMessage();
            final String[] toEmailsArray = new String[toEmails.size()];
            message.setTo(toEmailsArray);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
        }
    }

}
