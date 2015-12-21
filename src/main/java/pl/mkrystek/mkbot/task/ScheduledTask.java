package pl.mkrystek.mkbot.task;

public abstract class ScheduledTask extends BotTask {

    public ScheduledTask(String... validTaskNames) {
        super(validTaskNames);
    }
}
