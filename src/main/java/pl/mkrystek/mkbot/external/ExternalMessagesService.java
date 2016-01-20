package pl.mkrystek.mkbot.external;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import com.google.common.collect.ImmutableList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalMessagesService {

    private static final int MAX_MESSAGES_COUNT = 50; //TODO configurable

    public List<String> messages;

    public ExternalMessagesService() {
        this.messages = newArrayList();
    }

    @RequestMapping(method = POST, value = "/message")
    public ResponseEntity<String> postMessage(@RequestBody String message) {
        if (messages.size() < MAX_MESSAGES_COUNT) {
            messages.add(message);
            return ResponseEntity.ok("Message accepted");
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many messages");
    }

    public List<String> getExternalMessages() {
        List<String> tmp = ImmutableList.copyOf(messages);
        messages.clear();
        return tmp;
    }
}
