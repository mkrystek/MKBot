package pl.mkrystek.mkbot.task;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.mkrystek.mkbot.external.ExternalMessagesService;
import pl.mkrystek.mkbot.window.SkypeWindow;

@Component
public class TaskExecutionEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutionEngine.class);
    public static final String COMMAND_HELP_BODY = "?";

    @Autowired
    private SkypeWindow skypeWindow;

    @Autowired
    private ExternalMessagesService externalMessagesService;

    @Value("${polling_frequency}")
    private long pollingFrequency;

    private final ScheduledExecutorService scheduler;

    private Map<String, ReplyTask> replyTasks = newHashMap();
    private Map<String, ScheduledTask> scheduledTasks = newHashMap();

    public TaskExecutionEngine() {
        this.scheduler = newSingleThreadScheduledExecutor();
    }

    public void init(List<ReplyTask> replyTasks, List<ScheduledTask> scheduledTasks) {
        this.replyTasks = createTaskMap(replyTasks);
        this.scheduledTasks = createTaskMap(scheduledTasks);
    }

    private <T extends BotTask> Map<String, T> createTaskMap(List<T> taskList) {
        Map<String, T> map = newHashMap();
        taskList.forEach(task -> task.getTaskValidNames().forEach(taskName -> map.put(taskName, task)));
        return map;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            skypeWindow.getNewMessages().forEach(skypeMessage -> {
                ReplyTask replyTask = replyTasks.get(skypeMessage.getTaskName());
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
            });
            externalMessagesService.getExternalMessages().forEach(externalMessage -> {
                LOGGER.debug("Writing external message on skype: {}", externalMessage);
                skypeWindow.writeMessage(externalMessage);
            });
        }, 0, pollingFrequency, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
}
