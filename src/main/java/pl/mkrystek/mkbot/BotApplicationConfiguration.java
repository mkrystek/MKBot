package pl.mkrystek.mkbot;

import java.awt.AWTException;
import java.awt.Robot;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.mkrystek.mkbot.window.Keyboard;

@SpringBootApplication
public class BotApplicationConfiguration {

    @Bean
    public Keyboard keyboard() throws AWTException {
        return new Keyboard(new Robot());
    }
}
