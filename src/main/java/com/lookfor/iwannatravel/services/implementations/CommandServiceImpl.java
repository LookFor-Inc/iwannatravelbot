package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.bot.Command;
import com.lookfor.iwannatravel.exceptions.CommandNotFoundException;
import com.lookfor.iwannatravel.services.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.lookfor.iwannatravel.utils.TextMessageUtil.getCommandFromTextMessage;

@Slf4j
@Service
public class CommandServiceImpl implements CommandService {
    @Override
    public Command findCommandInMessage(String message) throws CommandNotFoundException {
        String textCmd = getCommandFromTextMessage(message);
        if (textCmd.isEmpty()) {
            return null;
        }
        Command cmd = Command.getByName(textCmd);
        log.info(
                String.format("Command '%s' (%s) was found in message '%s'",
                        textCmd, cmd, message)
        );
        if (cmd == null) {
            throw new CommandNotFoundException(textCmd);
        }
        return cmd;
    }
}
