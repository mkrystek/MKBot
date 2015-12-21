package pl.mkrystek.mkbot.window;

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
    public void testTypingSmallLetters() {
        //given
        String messageWithSmallLetters = "abcdefghijklmnopqrstuvwxyz";

        //when
        keyboard.sendMessage(messageWithSmallLetters);

        //then
        for (int i = VK_A; i <= VK_Z; i++) {
            verifyCharacterTyped(i);
        }
        verifyCharacterTyped(VK_ENTER);
        verifyNoMoreInteractions(robot);
    }

    @Test
    public void testTypingBigLetters() {
        //given
        String messageWithBigLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        //when
        keyboard.sendMessage(messageWithBigLetters);

        //then
        for (int i = VK_A; i <= VK_Z; i++) {
            verifyCharacterTypedWithShift(i);
        }
        verifyCharacterTyped(VK_ENTER);
        verifyNoMoreInteractions(robot);
    }

    @Test
    public void testTypingNumbers() {
        //given
        String messageWithNumbers = "0123456789";

        //when
        keyboard.sendMessage(messageWithNumbers);

        //then
        for (int i = VK_0; i <= VK_9; i++) {
            verifyCharacterTyped(i);
        }
        verifyCharacterTyped(VK_ENTER);
        verifyNoMoreInteractions(robot);
    }

    @Test
    public void testTypingSpecialCharactersUnderNumbers() {
        //given
        String messageWithSpecialCharacters = ")!@#$%^&*(";

        //when
        keyboard.sendMessage(messageWithSpecialCharacters);

        //then
        for (int i = VK_0; i <= VK_9; i++) {
            verifyCharacterTypedWithShift(i);
        }
        verifyCharacterTyped(VK_ENTER);
        verifyNoMoreInteractions(robot);
    }

    private void verifyCharacterTypedWithShift(int keyCode) {
        verifyCharacterTyped(VK_SHIFT, keyCode);
    }

    private void verifyCharacterTyped(int... keyCodes) {
        for (int keyCode : keyCodes) {
            order.verify(robot).keyPress(keyCode);
        }

        for (int i = keyCodes.length - 1; i >= 0; i--) {
            order.verify(robot).keyRelease(keyCodes[i]);
        }
    }
}