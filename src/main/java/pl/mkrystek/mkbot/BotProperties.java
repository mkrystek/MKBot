package pl.mkrystek.mkbot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class BotProperties {

    private static final String PROPERTIES_FILE_NAME = "config.properties";

    private BotProperties() {
    }

    private static final Properties props;

    private static String skypeDbPath;
    private static String chatName;
    private static Long pollingFrequency;
    private static String skypeUsername;
    private static String replyTasksToLoad;
    private static String scheduledTasksToLoad;

    static {
        props = new Properties();
        try {
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

    public static Long getPollingFrequency() {
        return pollingFrequency;
    }

    public static String getReplyTasksToLoad() {
        return replyTasksToLoad;
    }

    public static String getScheduledTasksToLoad() {
        return scheduledTasksToLoad;
    }
}
