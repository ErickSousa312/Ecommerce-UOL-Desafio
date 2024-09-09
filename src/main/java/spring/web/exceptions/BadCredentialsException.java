package spring.web.exceptions;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends CustomRunTimeException {
    public BadCredentialsException(String message, HttpStatus status) {
        super(message,status);
    }
}