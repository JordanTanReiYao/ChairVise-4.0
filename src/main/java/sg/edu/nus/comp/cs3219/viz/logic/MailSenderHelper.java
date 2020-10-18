package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailSenderHelper {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String from, String subject, String text) {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            emailSender.send(message);

        } catch (MessagingException e) {
            // logger.error(e.getMessage());
        }
    }
}