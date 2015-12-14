package pl.mkrystek.mkbot.pl.mkrystek.mkbot;

import static org.mockito.Mockito.inOrder;
import static java.awt.event.KeyEvent.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.awt.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardTest {

    @Mock
    private Robot robot;

    @InjectMocks
    private Keyboard keyboard;

    @Test
    public void shouldPressProperKeys() {
        //given
        String sampleMessage = "AbC";

        //when
        keyboard.type(sampleMessage);

        //then
        InOrder order = inOrder(robot);
        order.verify(robot).keyPress(VK_SHIFT);
        order.verify(robot).keyPress(VK_A);
        order.verify(robot).keyRelease(VK_A);
        order.verify(robot).keyRelease(VK_SHIFT);
        order.verify(robot).keyPress(VK_B);
        order.verify(robot).keyRelease(VK_B);
        order.verify(robot).keyPress(VK_SHIFT);
        order.verify(robot).keyPress(VK_C);
        order.verify(robot).keyRelease(VK_C);
        order.verify(robot).keyRelease(VK_SHIFT);
        verifyNoMoreInteractions(robot);
    }
}