package pl.mkrystek.mkbot;

import static java.awt.event.KeyEvent.*;

import java.awt.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Keyboard {

    private static final Logger LOGGER = LoggerFactory.getLogger(Keyboard.class);

    private Robot robot;

    public Keyboard(Robot robot) {
        this.robot = robot;
    }

    public void type(CharSequence characters) {
        int length = characters.length();
        for (int i = 0; i < length; i++) {
            try {
                type(characters.charAt(i));
            } catch (RuntimeException e) {
                LOGGER.error("Problem typing character '" + characters.charAt(i) + "'", e);
            }
        }
    }

    private void type(char character) {
        int characterType = Character.getType(character);
        int asciiCode = (int) character;

        if (characterType == Character.DECIMAL_DIGIT_NUMBER) {
            doType(asciiCode);
        } else if (characterType == Character.LOWERCASE_LETTER) {
            doType(VK_A + (asciiCode - 0x61));
        } else if (characterType == Character.UPPERCASE_LETTER) {
            doType(VK_SHIFT, asciiCode);
        } else {
            switch (character) {
                case '`':
                    doType(VK_BACK_QUOTE);
                    break;
                case '-':
                    doType(VK_MINUS);
                    break;
                case '=':
                    doType(VK_EQUALS);
                    break;
                case '~':
                    doType(VK_SHIFT, VK_BACK_QUOTE);
                    break;
                case '!':
                    doType(VK_SHIFT, VK_1);
                    break;
                case '@':
                    doType(VK_SHIFT, VK_2);
                    break;
                case '#':
                    doType(VK_SHIFT, VK_3);
                    break;
                case '$':
                    doType(VK_SHIFT, VK_4);
                    break;
                case '%':
                    doType(VK_SHIFT, VK_5);
                    break;
                case '^':
                    doType(VK_SHIFT, VK_6);
                    break;
                case '&':
                    doType(VK_SHIFT, VK_7);
                    break;
                case '*':
                    doType(VK_SHIFT, VK_8);
                    break;
                case '(':
                    doType(VK_SHIFT, VK_9);
                    break;
                case ')':
                    doType(VK_SHIFT, VK_0);
                    break;
                case '_':
                    doType(VK_SHIFT, VK_MINUS);
                    break;
                case '+':
                    doType(VK_SHIFT, VK_EQUALS);
                    break;
                case '\t':
                    doType(VK_TAB);
                    break;
                case '\n':
                    doType(VK_ENTER);
                    break;
                case '[':
                    doType(VK_OPEN_BRACKET);
                    break;
                case ']':
                    doType(VK_CLOSE_BRACKET);
                    break;
                case '\\':
                    doType(VK_BACK_SLASH);
                    break;
                case '{':
                    doType(VK_SHIFT, VK_OPEN_BRACKET);
                    break;
                case '}':
                    doType(VK_SHIFT, VK_CLOSE_BRACKET);
                    break;
                case '|':
                    doType(VK_SHIFT, VK_BACK_SLASH);
                    break;
                case ';':
                    doType(VK_SEMICOLON);
                    break;
                case ':':
                    doType(VK_SHIFT, VK_SEMICOLON);
                    break;
                case '\'':
                    doType(VK_QUOTE);
                    break;
                case '"':
                    doType(VK_SHIFT, VK_QUOTE);
                    break;
                case ',':
                    doType(VK_COMMA);
                    break;
                case '<':
                    doType(VK_SHIFT, VK_COMMA);
                    break;
                case '.':
                    doType(VK_PERIOD);
                    break;
                case '>':
                    doType(VK_SHIFT, VK_PERIOD);
                    break;
                case '/':
                    doType(VK_SLASH);
                    break;
                case '?':
                    doType(VK_SHIFT, VK_SLASH);
                    break;
                case ' ':
                    doType(VK_SPACE);
                    break;
                default:
                    throw new IllegalArgumentException("Cannot type character " + character);
            }
        }
    }

    private void doType(int... keyCodes) {
        doType(keyCodes, 0, keyCodes.length);
    }

    private void doType(int[] keyCodes, int offset, int length) {
        if (length == 0) {
            return;
        }

        robot.keyPress(keyCodes[offset]);
        doType(keyCodes, offset + 1, length - 1);
        robot.keyRelease(keyCodes[offset]);
    }
}