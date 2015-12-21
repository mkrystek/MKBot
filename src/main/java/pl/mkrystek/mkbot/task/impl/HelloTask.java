package pl.mkrystek.mkbot.task.impl;

import pl.mkrystek.mkbot.message.SkypeMessage;
import pl.mkrystek.mkbot.task.ReplyTask;

public class HelloTask extends ReplyTask {

    private static final String TASK_NAME = "hello";

    public HelloTask() {
        super(TASK_NAME);
    }

    @Override
    public String performAction(SkypeMessage skypeMessage) {
        return String.format("Hello to you too, dear %s!", skypeMessage.getUsername());
    }
}
