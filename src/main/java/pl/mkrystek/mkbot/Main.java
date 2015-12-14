package pl.mkrystek.mkbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mkrystek.mkbot.pl.mkrystek.mkbot.BotApplication;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        try {
            BotApplication application = new BotApplication();
            application.init();
            application.startApplication();
            application.shutdown();
        } catch (RuntimeException e) {
            LOGGER.error("Error :", e);
        }
    }
}
