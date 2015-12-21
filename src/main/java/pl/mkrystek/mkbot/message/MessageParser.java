package pl.mkrystek.mkbot.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {

    private static final Pattern skypeMessagePattern = Pattern
        .compile("(?<username>[A-Za-z0-9_.-]*) -\\s*(?<taskname>\\w+)\\s*(?<messagebody>.*)");

    public SkypeMessage parseSkypeMessage(String rawMessage) {
        Matcher matcher = skypeMessagePattern.matcher(rawMessage);
        if (matcher.find()) {
            return new SkypeMessage(getUsername(matcher), getTaskname(matcher), getMessageBody(matcher));
        } else {
            throw new RuntimeException("Message '" + rawMessage + "' does not match message pattern");
        }
    }

    private static String getUsername(Matcher matcher) {
        return getGroupValueOrThrowError(matcher, "username");
    }

    private static String getTaskname(Matcher matcher) {
        return getGroupValueOrThrowError(matcher, "taskname");
    }

    private static String getGroupValueOrThrowError(Matcher matcher, String groupName) {
        String value = matcher.group(groupName);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        throw new RuntimeException("Problem parsing '" + groupName + "' group");
    }

    private static String getMessageBody(Matcher matcher) {
        String messageBody = matcher.group("messagebody");
        if (messageBody != null) {
            return messageBody;
        }
        throw new RuntimeException("Problem parsing 'messagebody' group");
    }
}
