package pl.mkrystek.mkbot.window;

import static com.google.common.collect.Lists.newArrayList;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.base.Joiner;
import pl.mkrystek.mkbot.BotProperties;
import pl.mkrystek.mkbot.message.MessageParser;
import pl.mkrystek.mkbot.message.MessageProvider;
import pl.mkrystek.mkbot.message.SkypeMessage;

@Component
public class SkypeWindow {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkypeWindow.class);

    @Autowired
    private Keyboard keyboard;

    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private MessageParser messageParser;

    @Autowired
    private BotProperties botProperties;

    public List<SkypeMessage> getNewMessages() {
        List<SkypeMessage> skypeMessages = newArrayList();
        List<String> rawMessages = messageProvider.getNewMessages();

        for (String rawMessage : rawMessages) {
            try {
                String botNamePrompt = "!MKBot";//TODO extract bot name to config
                if (rawMessage.contains(botNamePrompt)) {
                    skypeMessages.add(messageParser.parseSkypeMessage(rawMessage.replace(botNamePrompt, "")));
                }
            } catch (Exception e) {
                LOGGER.warn("Problem with parsing rawMessage: {}, reason: {}. Ignoring...", rawMessage, e.getMessage());
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
            Thread.sleep(3000); //Skype URI is sloooow so we need to wait here
            return true;
        } catch (IOException | URISyntaxException | InterruptedException e) {
            return false;
        }
    }

    private URI createSkypeURI() throws URISyntaxException {
        List<String> participants = messageProvider.getChatParticipants();
        participants.remove(botProperties.getSkypeUsername());
        String uriString = Joiner.on(";").join(participants);
        return new URI(String.format("skype:%s?chat", uriString));
    }

    public void close() {
        messageProvider.close();
    }
}
