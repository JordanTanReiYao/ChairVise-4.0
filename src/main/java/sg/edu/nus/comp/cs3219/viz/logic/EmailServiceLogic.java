package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceLogic {

    @Autowired
    private JavaMailSender mailSender;

    public EmailServiceLogic(JavaMailSender sender) {
        this.mailSender = sender;
    }

    /**
     * This method will compose and send the message
     */
    public void sendEmail(String sender, String receiver, String subject) {

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject(subject);
            // TODO(https://github.com/CS3219-SE-Principles-and-Patterns/cs3219-ay2021-s1-project-2020-s1-g05/issues/9)
            helper.setText("<h1>You have created a new conference!</h1>", true);
        } catch (MessagingException me) {
            me.printStackTrace();
        }
        mailSender.send(message);
        System.out.println("Email sent!");
    }
}