package pl.mkrystek.mkbot.pl.mkrystek.mkbot.message;

public class ContextMessage {

    private String username;
    private String taskName;
    private String messageBody;

    public ContextMessage(String username, String taskName, String messageBody) {
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
