package pl.mkrystek.mkbot.window;

import static com.google.common.collect.Lists.newArrayList;

import java.awt.Desktop;
import java.awt.Robot;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Joiner;
import pl.mkrystek.mkbot.message.MessageParser;
import pl.mkrystek.mkbot.message.MessageProvider;
import pl.mkrystek.mkbot.message.SkypeMessage;

public class SkypeWindow {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkypeWindow.class);

    private Robot robot;
    private Keyboard keyboard;
    private MessageProvider messageProvider;
    private MessageParser messageParser;

    public SkypeWindow(Robot robot) throws Exception {
        try {
            this.robot = robot;
            this.keyboard = new Keyboard(robot);
            this.messageProvider = new MessageProvider();
            this.messageParser = new MessageParser();
        } catch (SQLException e) {
            LOGGER.error("Unable to create SkypeWindow class, reason: ", e);
            throw new Exception("Unable to create SkypeWindow class", e);
        }
    }

    public List<SkypeMessage> getNewMessages() {
        List<SkypeMessage> skypeMessages = newArrayList();
        List<String> rawMessages = messageProvider.getNewMessages();

        for (String rawMessage : rawMessages) {
            try {
                String botNamePrompt = "!MKBot";//TODO extract bot name to config
                if (rawMessage.contains(botNamePrompt)) {
                    skypeMessages.add(messageParser.parseSkypeMessage(rawMessage.replace(botNamePrompt + " ", "")));
                }
            } catch (Exception e) {
                LOGGER.warn("Problem with parsing rawMessage: {}, reason: {}. Ignoring...", rawMessage, e);
            }
        }
        return skypeMessages;
    }

    public void writeMessage(String message) {
        keyboard.sendMessage(message);
    }

    public boolean bringToForeground() {
        try {
            Desktop.getDesktop().browse(createSkypeURI());
            robot.delay(500);
            return true;
        } catch (IOException | URISyntaxException e) {
            return false;
        }
    }

    private URI createSkypeURI() throws URISyntaxException {
        List<String> participants = messageProvider.getChatParticipants();
        String uriString = Joiner.on(";").join(participants);
        return new URI(String.format("skype:%s?chat", uriString));
    }
}
