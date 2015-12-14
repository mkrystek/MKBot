package pl.mkrystek.mkbot.pl.mkrystek.mkbot.task;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.quartz.Scheduler;

public class TaskExecutionEngine {

    private final Scheduler taskScheduler;
    private List<ReplyTask> replyTasks;
    private List<ScheduledTask> scheduledTasks;

    public TaskExecutionEngine(final Scheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void init() {
        replyTasks = newArrayList();
        scheduledTasks = newArrayList();
    }

    public void run() {
        replyTasks.forEach(ReplyTask::run);
        scheduledTasks.forEach(ScheduledTask::run);
    }
}
