package pl.mkrystek.mkbot.task;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.reflect.Constructor;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProvider.class);

    private String replyTasksToLoad;
    private String scheduledTasksToLoad;
    List<ReplyTask> replyTasks;
    List<ScheduledTask> scheduledTasks;

    public List<ReplyTask> getReplyTasks() {
        if (replyTasks == null) {
            replyTasks = newArrayList();
            List<String> tasks = newArrayList(replyTasksToLoad.split(" "));
            tasks.remove("");

            for (String taskName : tasks) {
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
        }
        return replyTasks;
    }

    public List<ScheduledTask> getScheduledTasks() {
        if (scheduledTasks == null) {
            scheduledTasks = newArrayList();
            List<String> tasks = newArrayList(scheduledTasksToLoad.split(" "));
            tasks.remove("");

            for (String taskName : tasks) {
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
        return scheduledTasks;
    }

    @Value("${reply_tasks_to_load}")
    public void setReplyTasksToLoad(String replyTasksToLoad) {
        this.replyTasksToLoad = replyTasksToLoad;
    }

    @Value("${scheduled_tasks_to_load}")
    public void setScheduledTasksToLoad(String scheduledTasksToLoad) {
        this.scheduledTasksToLoad = scheduledTasksToLoad;
    }
}
