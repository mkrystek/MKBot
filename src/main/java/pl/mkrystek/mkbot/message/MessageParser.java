package pl.mkrystek.mkbot.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {

    private static final Pattern skypeMessagePattern = Pattern.compile("(?<username>[A-Za-z0-9_.-]*) - (?<taskname>\\w+) (?<messagebody>.*)");

    public SkypeMessage parseSkypeMessage(String rawMessage) {
        Matcher matcher = skypeMessagePattern.matcher(rawMessage);
        if (matcher.find()) {
            return new SkypeMessage(getGroupValueOrThrowError(matcher, "username"), getGroupValueOrThrowError(matcher, "taskname"),
                getGroupValueOrThrowError(matcher, "messagebody"));
        } else {
            throw new RuntimeException("Message '" + rawMessage + "' does not match message pattern");
        }
    }

    private static String getGroupValueOrThrowError(Matcher matcher, String groupName) {
        String value = matcher.group(groupName);
        if (value != null && !value.isEmpty()) {
            return value;
        } else {
            throw new RuntimeException("Problem parsing '" + groupName + "' group");
        }
    }
}
