package pl.mkrystek.mkbot.message;

public class SkypeMessage {

    private String username;
    private String taskName;
    private String messageBody;

    public SkypeMessage(String username, String taskName, String messageBody) {
        this.username = username;
        this.taskName = taskName;
        this.messageBody = messageBody;
    }

    public String getUsername() {
        return username;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getMessageBody() {
        return messageBody;
    }
}
