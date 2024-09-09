package spring.web.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidSaleException extends CustomRunTimeException  {
    public InvalidSaleException(String message, HttpStatus httpStatus) {
        super(message , httpStatus);
    }
}
