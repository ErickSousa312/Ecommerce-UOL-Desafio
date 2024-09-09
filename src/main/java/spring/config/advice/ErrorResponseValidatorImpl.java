package spring.config.advice;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Getter @Setter
@RequiredArgsConstructor
public class ErrorResponseValidatorImpl {
    private LocalDateTime timestamp;
    private int status;
    private HttpStatusCode error;
    private List<String> errors;

    public ErrorResponseValidatorImpl(MethodArgumentNotValidException e, HttpStatusCode errorCode) {
        this.timestamp = LocalDateTime.now();
        this.status = errorCode.value();
        this.error = errorCode;
        this.errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }
}