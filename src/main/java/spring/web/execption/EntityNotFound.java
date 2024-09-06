package spring.web.execption;

import org.springframework.http.HttpStatus;

public class EntityNotFound extends CustomRunTimeException {
    public EntityNotFound(String message, HttpStatus httpStatus) {
        super(message + " Not found", httpStatus);
    }
}
