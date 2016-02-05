package pl.mkrystek.mkbot;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.mkrystek.mkbot.event.StopApplicationEvent;
import pl.mkrystek.mkbot.task.TaskExecutionEngine;
import pl.mkrystek.mkbot.task.TaskProvider;
import pl.mkrystek.mkbot.window.SkypeWindow;

@Component
public class BotApplication implements ApplicationListener<StopApplicationEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotApplication.class);

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

    public void init() throws Exception{
        skypeWindow.init();
        taskExecutionEngine.init(taskProvider.getReplyTasks(), taskProvider.getScheduledTasks());
    }

    public void startApplication() throws InterruptedException {
        if (skypeWindow.bringToForeground()) {
            taskExecutionEngine.start();
            LOGGER.debug("Application started!");
            latch.await();
        }
    }

    public void shutdown() {
        taskExecutionEngine.shutdown();
        skypeWindow.close();
    }

    @Override
    public void onApplicationEvent(StopApplicationEvent stopApplicationEvent) {
        LOGGER.debug("Received stop application event with message '{}', stopping application", stopApplicationEvent.getMessage());
        latch.countDown();
    }
}
