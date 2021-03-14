package com.lookfor.iwannatravel.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class with basic methods for processing text messages
 */
public class TextMessageUtil {
    /**
     * Removing slash at the beginning of a text message
     *
     * @param textMessage source
     * @return textMessage without slash
     */
    private static String removeSlash(String textMessage) {
        return textMessage.charAt(0) == '/' ?
                textMessage.substring(1)
                : textMessage;
    }

    /**
     * Fetch command string name from source text message
     *
     * @param textMessage source
     * @return command string name
     */
    public static String getCommandFromTextMessage(String textMessage) {
        Pattern pattern = Pattern.compile(
                "^[a-zA-Zа-яА-Я]+",
                Pattern.UNICODE_CHARACTER_CLASS
        );
        Matcher matcher = pattern.matcher(removeSlash(textMessage));
        return !matcher.find() ?
                ""
                : matcher.group(0).trim().toLowerCase();
    }

    /**
     * Fetch rest of the source text message (without command)
     *
     * @param textMessage source
     * @return rest of the text message
     */
    public static String getRestOfTextMessageWithoutCommand(String textMessage) {
        String currentCommandName = getCommandFromTextMessage(textMessage);
        textMessage = removeSlash(textMessage);
        if (currentCommandName.isEmpty() ||
                currentCommandName.equals(textMessage.toLowerCase())) {
            return "";
        }
        return textMessage.substring(currentCommandName.length() + 1);
    }
}
