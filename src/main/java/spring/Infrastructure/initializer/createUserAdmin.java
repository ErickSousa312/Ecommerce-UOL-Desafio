package spring.Infrastructure.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import spring.domain.users.User;
import spring.repositories.UserRepository;

@Component
public class createUserAdmin {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultAdminUser() {
//        userRepository.save(
//                    new User(
//                            "admin",
//                            "admin"
//                    )
//            );
    }
}
