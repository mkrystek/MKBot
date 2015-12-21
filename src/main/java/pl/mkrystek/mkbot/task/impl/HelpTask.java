package pl.mkrystek.mkbot.task.impl;

import static pl.mkrystek.mkbot.task.TaskExecutionEngine.COMMAND_HELP_BODY;

import pl.mkrystek.mkbot.message.SkypeMessage;
import pl.mkrystek.mkbot.task.ReplyTask;

public class HelpTask extends ReplyTask {
    private static final String TASK_NAME = "help";

    public HelpTask() {
        super(TASK_NAME);
    }

    @Override
    public String performAction(SkypeMessage skypeMessage) {
        return "Help requested! Sample bot usage: \"!MKBot <commandName> <body>\n" +
            "If you want to know more about particular command, type \"!MKBot <commandName> " + COMMAND_HELP_BODY + "\"";
    }
}
