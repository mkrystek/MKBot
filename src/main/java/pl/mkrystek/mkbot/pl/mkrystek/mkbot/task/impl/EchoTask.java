package pl.mkrystek.mkbot.pl.mkrystek.mkbot.task.impl;

import pl.mkrystek.mkbot.pl.mkrystek.mkbot.message.SkypeMessage;
import pl.mkrystek.mkbot.pl.mkrystek.mkbot.task.ReplyTask;

public class EchoTask extends ReplyTask {

    private static final String TASK_NAME = "Echo";

    public EchoTask() {
        super(TASK_NAME);
    }

    @Override
    public void performAction(SkypeMessage skypeMessage) {

    }
}
