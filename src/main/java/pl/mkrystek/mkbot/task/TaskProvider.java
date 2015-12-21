package pl.mkrystek.mkbot.task;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.reflect.Constructor;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mkrystek.mkbot.BotProperties;

public class TaskProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProvider.class);

    List<ReplyTask> replyTasks;
    List<ScheduledTask> scheduledTasks;

    public TaskProvider() {
        replyTasks = newArrayList();
        scheduledTasks = newArrayList();
        loadTasksFromConfig();
    }

    private void loadTasksFromConfig() {
        List<String> replyTasksToLoad = newArrayList(BotProperties.getReplyTasksToLoad().split(" "));
        List<String> scheduledTasksToLoad = newArrayList(BotProperties.getScheduledTasksToLoad().split(" "));

        for (String taskName : replyTasksToLoad) {
            try {
                Class<?> clazz = Class.forName("pl.mkrystek.mkbot.task.impl." + taskName);
                Constructor<?> ctor = clazz.getConstructor();
                Object task = ctor.newInstance();
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
