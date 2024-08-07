package spring.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import spring.domain.users.User;
import spring.repositories.UserRepository;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Você pode configurar dados de teste aqui se necessário
    }

    @Test
    public void testFindByUsername() {
        User user = userRepository.findByUsername("admin")
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(user);
        Assertions.assertThat(user.getUsername()).isEqualTo("admin");
    }
}
