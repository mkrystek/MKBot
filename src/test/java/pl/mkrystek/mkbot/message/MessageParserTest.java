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
        assertThat(parsedMessage.getUsername()).isNotEmpty().isEqualTo("some_user");
        assertThat(parsedMessage.getTaskName()).isNotEmpty().isEqualTo("some_task");
        assertThat(parsedMessage.getMessageBody()).isNotEmpty().isEqualTo("some message body");
    }

    @Test
    public void shouldProperlyParseMessageWithLongWhitespace() {
        //given
        String rawMessageWithLongWhitespace = "some.user -          some_task               body from message with long whitespace";

        //when
        SkypeMessage parsedMessage = parser.parseSkypeMessage(rawMessageWithLongWhitespace);

        //then
        assertThat(parsedMessage.getUsername()).isNotEmpty().isEqualTo("some.user");
        assertThat(parsedMessage.getTaskName()).isNotEmpty().isEqualTo("some_task");
        assertThat(parsedMessage.getMessageBody()).isNotEmpty().isEqualTo("body from message with long whitespace");
    }

    @Test
    public void shouldProperlyParseMessageWithEmptyBody() {
        //given
        String rawMessageWithEmptyBody = "some.user - some_task";

        //when
        SkypeMessage parsedMessage = parser.parseSkypeMessage(rawMessageWithEmptyBody);

        //then
        assertThat(parsedMessage.getUsername()).isNotEmpty().isEqualTo("some.user");
        assertThat(parsedMessage.getTaskName()).isNotEmpty().isEqualTo("some_task");
        assertThat(parsedMessage.getMessageBody()).isEmpty();
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