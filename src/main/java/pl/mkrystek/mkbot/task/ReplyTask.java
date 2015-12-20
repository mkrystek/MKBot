package pl.mkrystek.mkbot.task;

import pl.mkrystek.mkbot.message.SkypeMessage;

public abstract class ReplyTask extends BotTask {

    public ReplyTask(String taskName) {
        super(taskName);
    }

    public abstract void performAction(SkypeMessage skypeMessage);
}
