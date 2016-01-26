package pl.mkrystek.mkbot.event;

import org.springframework.context.ApplicationEvent;

public class StopApplicationEvent extends ApplicationEvent {

    private String message;

    public StopApplicationEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
