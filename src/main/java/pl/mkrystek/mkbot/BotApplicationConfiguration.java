package pl.mkrystek.mkbot;

import java.awt.AWTException;
import java.awt.Robot;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pl.mkrystek.mkbot.external.ExternalAccessProvider;
import pl.mkrystek.mkbot.window.Keyboard;

@SpringBootApplication
public class BotApplicationConfiguration {

    @Bean
    public Keyboard keyboard() throws AWTException {
        return new Keyboard(new Robot());
    }

    @Bean
    public ExternalAccessProvider externalAccessProvider() {
        return new ExternalAccessProvider(new RestTemplate());
    }
}
