package pl.mkrystek.mkbot.pl.mkrystek.mkbot.task;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class TaskProvider {

    public List<ReplyTask> getReplyTasks() {
        return newArrayList();
    }

    public List<ScheduledTask> getScheduledTasks() {
        return newArrayList();
    }
}
