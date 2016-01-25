package pl.mkrystek.mkbot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BotProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotProperties.class);
    private static final String PROPERTIES_FILE_NAME = "config.properties";

    private String skypeDbPath;
    private String chatName;
    private Long pollingFrequency;
    private String skypeUsername;
    private String replyTasksToLoad;
    private String scheduledTasksToLoad;

    public BotProperties() {
        try {
            Properties props = new Properties();
            InputStream propertiesFile = BotProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
            props.load(propertiesFile);
            skypeUsername = props.getProperty("skype_username");
            skypeDbPath = String.format("%s\\Skype\\%s\\main.db", System.getenv("APPDATA"), skypeUsername);
            chatName = props.getProperty("chat_name");
            pollingFrequency = Long.parseLong(props.getProperty("polling_frequency"));
            replyTasksToLoad = props.getProperty("reply_tasks_to_load");
            scheduledTasksToLoad = props.getProperty("scheduled_tasks_to_load");
            propertiesFile.close();
        } catch (IOException e) {
            LOGGER.error("Error occurred while reading properties file: ", e);
        }
    }

    public String getSkypeDbPath() {
        return skypeDbPath;
    }

    public String getSkypeUsername() {
        return skypeUsername;
    }

    public String getChatName() {
        return chatName;
    }

    public Long getPollingFrequency() {
        return pollingFrequency;
    }

    public String getReplyTasksToLoad() {
        return replyTasksToLoad;
    }

    public String getScheduledTasksToLoad() {
        return scheduledTasksToLoad;
    }
}
