package pl.mkrystek.mkbot.pl.mkrystek.mkbot.task;

import java.util.List;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public class TaskExecutionEngine {

    private final Scheduler taskScheduler;
    private List<ReplyTask> replyTasks;
    private List<ScheduledTask> scheduledTasks;

    public TaskExecutionEngine(final Scheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void init(List<ReplyTask> replyTasks, List<ScheduledTask> scheduledTasks) {
        this.replyTasks = replyTasks;
        this.scheduledTasks = scheduledTasks;
    }

    public void start() {
        try {
            taskScheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            taskScheduler.shutdown(false);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
