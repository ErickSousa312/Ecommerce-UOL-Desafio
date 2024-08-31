package spring.eventListener;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class EventSuccessfulLogin extends ApplicationEvent {
    private final String message;
    public EventSuccessfulLogin(Object source, String message) {
        super(source);
        this.message = message;
    }

    public EventSuccessfulLogin(Object source, Clock clock, String message) {
        super(source, clock);
        this.message = message;
    }
}
