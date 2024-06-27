package spring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import spring.domain.Calculator;

@RestController
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        Calculator calculator = new Calculator();
        System.out.println(calculator.sumTwoNumber(1,2));

    }
}
