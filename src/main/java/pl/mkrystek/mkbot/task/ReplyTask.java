package pl.mkrystek.mkbot.task;

import pl.mkrystek.mkbot.message.SkypeMessage;

public abstract class ReplyTask extends BotTask {

    public ReplyTask(String... validNames) {
        super(validNames);
    }

    public abstract String performAction(SkypeMessage skypeMessage);
}
