package pl.mkrystek.mkbot.task;

import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public class TaskExecutionEngine {

    private final Scheduler scheduler;
    private List<ReplyTask> replyTasks;
    private List<ScheduledTask> scheduledTasks;

    public TaskExecutionEngine(final Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void init(List<ReplyTask> replyTasks, List<ScheduledTask> scheduledTasks) {
        this.replyTasks = replyTasks;
        this.scheduledTasks = scheduledTasks;
    }

    public void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            scheduler.shutdown(false);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
