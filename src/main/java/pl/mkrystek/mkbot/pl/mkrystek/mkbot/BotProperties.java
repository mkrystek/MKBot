package pl.mkrystek.mkbot.pl.mkrystek.mkbot;

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

    static {
        props = new Properties();
        try {
            FileInputStream propertiesFile = new FileInputStream(PROPERTIES_PATH);
            props.load(propertiesFile);
            skypeDbPath = props.getProperty("skype_db_path");
            chatName = props.getProperty("chat_name");
            propertiesFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSkypeDbPath() {
        return skypeDbPath;
    }

    public static String getChatName() {
        return chatName;
    }
}
