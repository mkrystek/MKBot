package pl.mkrystek.mkbot.pl.mkrystek.mkbot.task;

import pl.mkrystek.mkbot.pl.mkrystek.mkbot.message.ContextMessage;

public abstract class BotTask {

    protected final String taskName;

    public BotTask(String taskName) {
        this.taskName = taskName;
    }

    public abstract boolean checkIfApplies(ContextMessage contextMessage);

    public String getTaskName() {
        return taskName;
    }
}
