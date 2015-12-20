package pl.mkrystek.mkbot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class BotProperties {

    private static final String PROPERTIES_PATH = "src/main/resources/config.properties";

    private BotProperties() {
    }

    private static final Properties props;

    private static String skypeDbPath;
    private static String chatName;
    private static String pollingFrequency;
    private static String skypeUsername;
    private static String replyTasksToLoad;
    private static String scheduledTasksToLoad;

    static {
        props = new Properties();
        try {
            FileInputStream propertiesFile = new FileInputStream(PROPERTIES_PATH);
            props.load(propertiesFile);
            skypeDbPath = props.getProperty("skype_db_path");
            skypeUsername = props.getProperty("skype_username");
            chatName = props.getProperty("chat_name");
            pollingFrequency = props.getProperty("polling_frequency");
            replyTasksToLoad = props.getProperty("reply_tasks_to_load");
            scheduledTasksToLoad = props.getProperty("scheduled_tasks_to_load");
            propertiesFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSkypeDbPath() {
        return skypeDbPath;
    }

    public static String getSkypeUsername() {
        return skypeUsername;
    }

    public static String getChatName() {
        return chatName;
    }

    public static String getPollingFrequency() {
        return pollingFrequency;
    }

    public static String getReplyTasksToLoad() {
        return replyTasksToLoad;
    }

    public static String getScheduledTasksToLoad() {
        return scheduledTasksToLoad;
    }
}
