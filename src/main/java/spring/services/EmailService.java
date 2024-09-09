package spring.services;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender mailSender;
    private final JWTService jwtService;


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

    public Boolean ResetPasswordByEmail(String email) {
        String token =jwtService.generateJWTResetPassword(email);
        String Url = "http://localhost:5000/user/call_back_password?token=" + token;
        send(email, "Reset Password", "Para resetar sua senha senha click no link a seguir "+Url);
        return true;
    }

}
