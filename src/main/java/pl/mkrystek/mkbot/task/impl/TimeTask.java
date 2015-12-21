package pl.mkrystek.mkbot.task.impl;

import java.util.Date;
import pl.mkrystek.mkbot.message.SkypeMessage;
import pl.mkrystek.mkbot.task.ReplyTask;

public class TimeTask extends ReplyTask {

    private static final String TASK_NAME = "time";

    public TimeTask() {
        super(TASK_NAME);
    }

    @Override
    public String performAction(SkypeMessage skypeMessage) {
        return String.format("Current time is %s", new Date().toString());
    }
}
