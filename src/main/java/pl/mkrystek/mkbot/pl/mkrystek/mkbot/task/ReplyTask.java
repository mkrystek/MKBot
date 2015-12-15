package pl.mkrystek.mkbot.pl.mkrystek.mkbot.task;

import pl.mkrystek.mkbot.pl.mkrystek.mkbot.message.ContextMessage;

public abstract class ReplyTask extends BotTask {

    public ReplyTask(String taskName) {
        super(taskName);
    }

    public abstract void performAction(ContextMessage contextMessage);
}
