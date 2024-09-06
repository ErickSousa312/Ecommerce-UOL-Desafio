package spring.web.execption;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class CustomRunTimeException extends RuntimeException {
    private final HttpStatus httpStatus;

    public CustomRunTimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
