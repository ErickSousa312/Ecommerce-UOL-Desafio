package spring.config.advice;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;


@Getter @Setter
@RequiredArgsConstructor
public class ErrorResponseImpl {
    private String message;
    private HttpStatusCode errorCode;

    public ErrorResponseImpl(String message, HttpStatusCode errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}