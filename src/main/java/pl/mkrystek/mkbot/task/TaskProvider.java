package pl.mkrystek.mkbot.task;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.reflect.Constructor;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mkrystek.mkbot.BotProperties;

@Component
public class TaskProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProvider.class);

    @Autowired
    private BotProperties botProperties;

    List<ReplyTask> replyTasks;
    List<ScheduledTask> scheduledTasks;

    public TaskProvider() {
        replyTasks = newArrayList();
        scheduledTasks = newArrayList();
        loadTasksFromConfig();
    }

    private void loadTasksFromConfig() {
        List<String> replyTasksToLoad = newArrayList(botProperties.getReplyTasksToLoad().split(" "));
        List<String> scheduledTasksToLoad = newArrayList(botProperties.getScheduledTasksToLoad().split(" "));

        replyTasksToLoad.remove("");
        scheduledTasksToLoad.remove("");

        for (String taskName : replyTasksToLoad) {
            try {
                Class<?> clazz = Class.forName("pl.mkrystek.mkbot.task.impl." + taskName);
                Constructor<?> ctor = clazz.getConstructor();
                Object task = ctor.newInstance();
                LOGGER.debug("Registering reply task {}", task.getClass());
                replyTasks.add((ReplyTask) task);
            } catch (Exception e) {
                LOGGER.error("Error loading reply task: ", e);
            }
        }

        for (String taskName : scheduledTasksToLoad) {
            try {
                Class<?> clazz = Class.forName("pl.mkrystek.mkbot.task.impl." + taskName);
                Constructor<?> ctor = clazz.getConstructor();
                Object task = ctor.newInstance();
                LOGGER.debug("Registering scheduled task {}", task.getClass());
                scheduledTasks.add((ScheduledTask) task);
            } catch (Exception e) {
                LOGGER.error("Error loading scheduled task: ", e);
            }
        }
    }

    public List<ReplyTask> getReplyTasks() {
        return replyTasks;
    }

    public List<ScheduledTask> getScheduledTasks() {
        return scheduledTasks;
    }
}
