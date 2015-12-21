package pl.mkrystek.mkbot.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Before;
import org.junit.Test;

public class MessageParserTest {

    private MessageParser parser;

    @Before
    public void setUp() {
        parser = new MessageParser();
    }

    @Test
    public void shouldProperlyParseMessage() {
        //given
        String rawMessage = "some_user - some_task some message body";

        //when
        SkypeMessage parsedMessage = parser.parseSkypeMessage(rawMessage);

        //then
        assertThat(parsedMessage.getUsername()).isNotEmpty().contains("some_user");
        assertThat(parsedMessage.getTaskName()).isNotEmpty().contains("some_task");
        assertThat(parsedMessage.getMessageBody()).isNotEmpty().contains("some message body");
    }

    @Test
    public void shouldThrowErrorForInvalidMessageFormat() {
        //given
        String badMessage = "invalid message";

        //when
        Throwable throwable = catchThrowable(() -> parser.parseSkypeMessage(badMessage));

        //then
        assertThat(throwable).isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Message '" + badMessage + "' does not match message pattern");
    }
}