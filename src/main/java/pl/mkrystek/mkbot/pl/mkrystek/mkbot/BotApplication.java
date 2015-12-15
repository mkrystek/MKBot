package pl.mkrystek.mkbot.pl.mkrystek.mkbot;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import pl.mkrystek.mkbot.pl.mkrystek.mkbot.task.TaskExecutionEngine;

public class BotApplication {

    private final TaskExecutionEngine taskExecutionEngine;

    public BotApplication() {
        try {
            Scheduler taskScheduler = StdSchedulerFactory.getDefaultScheduler();
            taskExecutionEngine = new TaskExecutionEngine(taskScheduler);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void init() {
        taskExecutionEngine.init();
    }

    public void startApplication() {
        taskExecutionEngine.start();
    }

    public void shutdown() {
        taskExecutionEngine.shutdown();
    }
}
