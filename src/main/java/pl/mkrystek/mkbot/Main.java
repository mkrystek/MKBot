package pl.mkrystek.mkbot;

import static org.springframework.boot.Banner.Mode.OFF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder().bannerMode(OFF).web(true).logStartupInfo(false)
            .sources(BotApplicationConfiguration.class).headless(false).run(args);
        LOGGER.debug("Starting application");
        BotApplication application = ctx.getBean(BotApplication.class);

        try {
            application.init();
            application.startApplication();
        } catch (Exception e) {
            LOGGER.error("Error : ", e);
        } finally {
            LOGGER.debug("Shutting down application");
            if (application != null) application.shutdown();
        }
    }
}
