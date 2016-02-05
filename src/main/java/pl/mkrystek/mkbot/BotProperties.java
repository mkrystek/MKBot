package pl.mkrystek.mkbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotProperties {

    @Value("${chat_name}")
    private String chatName;

    @Value("${polling_frequency}")
    private Long pollingFrequency;

    @Value("${skype_username}")
    private String skypeUsername;

    @Value("${reply_tasks_to_load}")
    private String replyTasksToLoad;

    @Value("${scheduled_tasks_to_load}")
    private String scheduledTasksToLoad;

    private String skypeDbPath;

    public String getSkypeDbPath() {
        if (skypeDbPath == null) {
            skypeDbPath = String.format("%s\\Skype\\%s\\main.db", System.getenv("APPDATA"), skypeUsername);
        }
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
