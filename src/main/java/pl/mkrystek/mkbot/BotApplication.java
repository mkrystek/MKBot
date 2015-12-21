package pl.mkrystek.mkbot;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

import java.util.concurrent.CountDownLatch;
import pl.mkrystek.mkbot.task.TaskExecutionEngine;
import pl.mkrystek.mkbot.task.TaskProvider;
import pl.mkrystek.mkbot.window.SkypeWindow;

public class BotApplication {

    private final TaskExecutionEngine taskExecutionEngine;
    private final TaskProvider taskProvider;
    private final SkypeWindow skypeWindow;
    private final CountDownLatch latch;

    public BotApplication() throws Exception {
        this.skypeWindow = new SkypeWindow();
        this.taskExecutionEngine = new TaskExecutionEngine(newSingleThreadScheduledExecutor(), skypeWindow);
        this.taskProvider = new TaskProvider();
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
