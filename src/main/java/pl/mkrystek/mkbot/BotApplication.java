package pl.mkrystek.mkbot;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import pl.mkrystek.mkbot.task.TaskExecutionEngine;
import pl.mkrystek.mkbot.task.TaskProvider;

public class BotApplication {

    private final TaskExecutionEngine taskExecutionEngine;
    private final TaskProvider taskProvider;

    public BotApplication() {
        try {
            Scheduler taskScheduler = StdSchedulerFactory.getDefaultScheduler();
            taskExecutionEngine = new TaskExecutionEngine(taskScheduler);
            taskProvider = new TaskProvider();
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void init() {
        taskExecutionEngine.init(taskProvider.getReplyTasks(), taskProvider.getScheduledTasks());
    }

    public void startApplication() {
        taskExecutionEngine.start();
    }

    public void shutdown() {
        taskExecutionEngine.shutdown();
    }
}
