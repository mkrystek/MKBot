package pl.mkrystek.mkbot.task;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.reflect.Constructor;
import java.util.List;
import pl.mkrystek.mkbot.BotProperties;

public class TaskProvider {

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
                e.printStackTrace();
            }
        }

        for (String taskName : scheduledTasksToLoad) {
            try {
                Class<?> clazz = Class.forName("pl.mkrystek.mkbot.task.impl." + taskName);
                Constructor<?> ctor = clazz.getConstructor();
                Object task = ctor.newInstance();
                scheduledTasks.add((ScheduledTask) task);
            } catch (Exception e) {
                e.printStackTrace();
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
