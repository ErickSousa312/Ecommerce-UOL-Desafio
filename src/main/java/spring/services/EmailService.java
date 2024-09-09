package spring.services;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String to, String subject, String text) {
        try {
            SimpleMailMessage mailMessage  = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(text);

            mailSender.send(mailMessage);

        } catch (MailException e) {
            e.getMessage();
        }
    }

}
