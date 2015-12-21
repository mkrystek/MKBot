package pl.mkrystek.mkbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        BotApplication application = null;
        try {
            application = new BotApplication();
            application.init();
            application.startApplication();
        } catch (RuntimeException e) {
            LOGGER.error("Error : ", e);
        } finally {
            if (application != null) application.shutdown();
        }
    }
}
