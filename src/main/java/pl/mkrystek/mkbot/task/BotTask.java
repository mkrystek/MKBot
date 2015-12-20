package pl.mkrystek.mkbot.task;

import pl.mkrystek.mkbot.message.SkypeMessage;

public abstract class BotTask {

    protected final String taskName;

    public BotTask(String taskName) {
        this.taskName = taskName;
    }

    public boolean checkIfApplies(SkypeMessage skypeMessage) {
        return this.taskName.equals(skypeMessage.getTaskName());
    }

    public String getTaskName() {
        return taskName;
    }
}
