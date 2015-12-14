package pl.mkrystek.mkbot.pl.mkrystek.mkbot.task;

import pl.mkrystek.mkbot.pl.mkrystek.mkbot.Keyboard;

public abstract class BotTask {

    private final Keyboard keyboard;

    public BotTask(final Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public abstract void run();
}
