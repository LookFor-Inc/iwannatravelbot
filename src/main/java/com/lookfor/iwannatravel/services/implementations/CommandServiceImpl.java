package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.bot.Command;
import com.lookfor.iwannatravel.services.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CommandServiceImpl implements CommandService {
    @Override
    public Command findCommandInMessage(String message) {
        String textCmd = getCommandFromString(message);
        if (textCmd.equals("")) {
            return null;
        }
        Command cmd = Command.getByName(textCmd);
        log.info(
                String.format("Command '%s' (%s) was found in message '%s'",
                        textCmd, cmd, message)
        );
        return cmd;
    }

    private String getCommandFromString(String str) {
        if (str.charAt(0) == '/') {
            str = str.substring(1);
        }
        // Regex for input text
        Pattern pattern = Pattern.compile(
                "^[a-zA-Zа-яА-Я0-9Ёё]+",
                Pattern.UNICODE_CHARACTER_CLASS
        );
        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()) {
            return "";
        }
        return matcher.group(0).trim().toLowerCase();
    }
}
