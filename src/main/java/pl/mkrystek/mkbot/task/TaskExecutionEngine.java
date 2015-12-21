package pl.mkrystek.mkbot.task;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mkrystek.mkbot.window.SkypeWindow;

public class TaskExecutionEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutionEngine.class);
    private static final String COMMAND_HELP_BODY = "?";

    private final ScheduledExecutorService scheduler;
    private final SkypeWindow skypeWindow;
    private List<ReplyTask> replyTasks;
    private List<ScheduledTask> scheduledTasks;

    public TaskExecutionEngine(ScheduledExecutorService scheduler, SkypeWindow skypeWindow) {
        this.skypeWindow = skypeWindow;
        this.scheduler = scheduler;
    }

    public void init(List<ReplyTask> replyTasks, List<ScheduledTask> scheduledTasks) {
        this.replyTasks = replyTasks;
        this.scheduledTasks = scheduledTasks;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> skypeWindow.getNewMessages().forEach(skypeMessage -> replyTasks.forEach(replyTask -> {
            if (replyTask.checkIfApplies(skypeMessage)) {
                String reply;
                if (skypeMessage.getMessageBody().equals(COMMAND_HELP_BODY)) {
                    reply = replyTask.performHelpAction(skypeMessage);
                } else {
                    reply = replyTask.performAction(skypeMessage);
                }
                LOGGER.debug("Writing message on skype: {}", reply);
                skypeWindow.writeMessage(reply);
            }
        })), 0, 100, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
}
