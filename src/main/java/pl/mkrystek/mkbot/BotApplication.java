package pl.mkrystek.mkbot;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

import pl.mkrystek.mkbot.task.TaskExecutionEngine;
import pl.mkrystek.mkbot.task.TaskProvider;
import pl.mkrystek.mkbot.window.SkypeWindow;

public class BotApplication {

    private final TaskExecutionEngine taskExecutionEngine;
    private final TaskProvider taskProvider;
    private final SkypeWindow skypeWindow;

    public BotApplication() throws Exception {
        this.skypeWindow = new SkypeWindow();
        this.taskExecutionEngine = new TaskExecutionEngine(newSingleThreadScheduledExecutor(), skypeWindow);
        this.taskProvider = new TaskProvider();
    }

    public void init() {
        taskExecutionEngine.init(taskProvider.getReplyTasks(), taskProvider.getScheduledTasks());
    }

    public void startApplication() {
        if (skypeWindow.bringToForeground()) {
            taskExecutionEngine.start();
        }
    }

    public void shutdown() {
        taskExecutionEngine.shutdown();
    }
}
