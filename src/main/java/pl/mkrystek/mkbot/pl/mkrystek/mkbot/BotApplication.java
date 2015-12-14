package pl.mkrystek.mkbot.pl.mkrystek.mkbot;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import pl.mkrystek.mkbot.pl.mkrystek.mkbot.task.TaskExecutionEngine;

public class BotApplication {

    private final Scheduler taskScheduler;
    private final TaskExecutionEngine taskExecutionEngine;

    public BotApplication() {
        try {
            taskScheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        taskExecutionEngine = new TaskExecutionEngine(taskScheduler);
    }

    public void init() {
        taskExecutionEngine.init();
    }

    public void startApplication() {
        try {
            taskScheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        taskExecutionEngine.run();
    }

    public void shutdown() {
        try {
            taskScheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
