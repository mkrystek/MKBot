package pl.mkrystek.mkbot.task;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mkrystek.mkbot.BotProperties;
import pl.mkrystek.mkbot.external.ExternalAccessProvider;
import pl.mkrystek.mkbot.window.SkypeWindow;

@Component
public class TaskExecutionEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutionEngine.class);
    public static final String COMMAND_HELP_BODY = "?";

    @Autowired
    private SkypeWindow skypeWindow;

    @Autowired
    private BotProperties botProperties;

    @Autowired
    private ExternalAccessProvider externalAccessProvider;

    private final ScheduledExecutorService scheduler;

    private List<ReplyTask> replyTasks;
    private List<ScheduledTask> scheduledTasks;

    public TaskExecutionEngine() {
        this.scheduler = newSingleThreadScheduledExecutor();
    }

    public void init(List<ReplyTask> replyTasks, List<ScheduledTask> scheduledTasks) {
        this.replyTasks = replyTasks;
        this.scheduledTasks = scheduledTasks;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            skypeWindow.getNewMessages().forEach(skypeMessage -> replyTasks.forEach(replyTask -> {
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
            }));
            externalAccessProvider.getMessages().forEach(externalMessage -> {
                LOGGER.debug("Writing external message on skype: {}", externalMessage);
                skypeWindow.writeMessage(externalMessage);
            });
        }, 0, botProperties.getPollingFrequency(), TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
}
