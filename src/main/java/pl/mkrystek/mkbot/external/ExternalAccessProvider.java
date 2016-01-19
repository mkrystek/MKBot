package pl.mkrystek.mkbot.external;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import com.google.common.collect.ImmutableList;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalAccessProvider {

    public List<String> messages;

    public ExternalAccessProvider() {
        this.messages = newArrayList();
    }

    @RequestMapping(method = POST, value = "/message")
    public String postMessage(@RequestBody String message) {
        messages.add(message);
        return "OK " + message;
    }

    public List<String> getMessages() {
        List<String> tmp = ImmutableList.copyOf(messages);
        messages.clear();
        return tmp;
    }
}
