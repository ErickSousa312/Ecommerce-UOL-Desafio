package spring.web.exceptions;

import org.springframework.http.HttpStatus;

public class MustNotBeNullException extends CustomRunTimeException {
    public MustNotBeNullException(String message, HttpStatus httpStatus) {
        super(message + " cannot be null", httpStatus);
    }
}
