package pl.mkrystek.mkbot.task.impl;

import pl.mkrystek.mkbot.message.SkypeMessage;
import pl.mkrystek.mkbot.task.ReplyTask;

public class EchoTask extends ReplyTask {

    private static final String TASK_NAME = "echo";

    public EchoTask() {
        super(TASK_NAME);
    }

    @Override
    public String performAction(SkypeMessage skypeMessage) {
        return String.format("@%s: %s", skypeMessage.getUsername(), skypeMessage.getMessageBody());
    }
}
