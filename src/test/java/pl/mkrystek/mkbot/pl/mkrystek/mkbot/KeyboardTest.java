package pl.mkrystek.mkbot.pl.mkrystek.mkbot;

import static org.mockito.Mockito.inOrder;
import static java.awt.event.KeyEvent.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.awt.*;

import org.junit.Before;
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

    InOrder order;

    @Before
    public void setUp() {
        order = inOrder(robot);
    }

    @Test
    public void shouldPressProperKeys() {
        //given
        String sampleMessage = "AbC";

        //when
        keyboard.type(sampleMessage);

        //then
        verifySequencePressed(VK_SHIFT, VK_A);
        verifySequencePressed(VK_B);
        verifySequencePressed(VK_SHIFT, VK_C);
        verifyNoMoreInteractions(robot);
    }

    private void verifySequencePressed(int... sequence) {
        verifySequence(sequence);
    }

    private void verifySequence(int... keyCodes) {
        verifySequence(keyCodes, 0, keyCodes.length);
    }

    private void verifySequence(int[] keyCodes, int offset, int length) {
        if (length == 0) {
            return;
        }

        order.verify(robot).keyPress(keyCodes[offset]);
        verifySequence(keyCodes, offset + 1, length - 1);
        order.verify(robot).keyRelease(keyCodes[offset]);
    }
}