package spring.config.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.web.execption.CustomRunTimeException;

@RestControllerAdvice
public class AdviceExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, HttpStatusCode statusCode) {
        return ResponseEntity.status(statusCode).body(new ErrorResponseImpl(ex.getMessage(), statusCode));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseImpl> handleIllegalArgumentException(IllegalArgumentException ex) {
        HttpStatusCode statusCode = HttpStatus.BAD_REQUEST;
        ErrorResponseImpl errorResponse = new ErrorResponseImpl(ex.getMessage(), statusCode);
        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseImpl> handleNullPointerException(NullPointerException ex) {
        HttpStatusCode statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseImpl errorResponse = new ErrorResponseImpl("Null pointer encountered: " + ex.getMessage(), statusCode);
        return ResponseEntity.status(statusCode).body(errorResponse);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseImpl> handleRuntimeException(RuntimeException ex) {
        ErrorResponseImpl errorResponse = new ErrorResponseImpl(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(CustomRunTimeException.class)
    public ResponseEntity<ErrorResponseImpl> customHandleRuntimeException(CustomRunTimeException ex) {
        ErrorResponseImpl errorResponse = new ErrorResponseImpl(ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseImpl> HandleFilterRuntimeException(AuthenticationException ex) {
        ErrorResponseImpl errorResponse = new ErrorResponseImpl(ex.getMessage(), HttpStatus.ACCEPTED);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
    }

}