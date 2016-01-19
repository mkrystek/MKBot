package pl.mkrystek.mkbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        BotApplication application = ctx.getBean(BotApplication.class);

        try {
            application.init();
            application.startApplication();
        } catch (Exception e) {
            LOGGER.error("Error : ", e);
        } finally {
            if (application != null) application.shutdown();
        }
    }
}
