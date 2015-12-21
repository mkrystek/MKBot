package pl.mkrystek.mkbot.task;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import pl.mkrystek.mkbot.message.SkypeMessage;

public abstract class BotTask {

    protected final List<String> validNames;

    public BotTask(String... validNames) {
        this.validNames = newArrayList(validNames);
    }

    public boolean checkIfApplies(SkypeMessage skypeMessage) {
        return validNames.contains(skypeMessage.getTaskName());
    }

    public List<String> getTaskValidNames() {
        return validNames;
    }
}
