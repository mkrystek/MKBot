package pl.mkrystek.mkbot.pl.mkrystek.mkbot.task;

import pl.mkrystek.mkbot.pl.mkrystek.mkbot.message.ContextMessage;

public abstract class BotTask {

    protected final String taskName;

    public BotTask(String taskName) {
        this.taskName = taskName;
    }

    public boolean checkIfApplies(ContextMessage contextMessage) {
        return this.taskName.equals(contextMessage.getTaskName());
    }

    public String getTaskName() {
        return taskName;
    }
}
