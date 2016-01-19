package pl.mkrystek.mkbot;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mkrystek.mkbot.task.TaskExecutionEngine;
import pl.mkrystek.mkbot.task.TaskProvider;
import pl.mkrystek.mkbot.window.SkypeWindow;

@Component
public class BotApplication {

    @Autowired
    private TaskExecutionEngine taskExecutionEngine;

    @Autowired
    private TaskProvider taskProvider;

    @Autowired
    private SkypeWindow skypeWindow;

    private final CountDownLatch latch;

    public BotApplication() {
        this.latch = new CountDownLatch(1);
    }

    public void init() {
        taskExecutionEngine.init(taskProvider.getReplyTasks(), taskProvider.getScheduledTasks());
    }

    public void startApplication() throws InterruptedException {
        if (skypeWindow.bringToForeground()) {
            taskExecutionEngine.start();
        }
        latch.await();
    }

    public void shutdown() {
        taskExecutionEngine.shutdown();
        skypeWindow.close();
    }
}
