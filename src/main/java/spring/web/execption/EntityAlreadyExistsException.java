package spring.web.execption;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class EntityAlreadyExistsException extends CustomRunTimeException {
    public EntityAlreadyExistsException(String message, HttpStatus httpStatus) {
        super("Entity "+ message +" already exists in the database", httpStatus);
    }
}
