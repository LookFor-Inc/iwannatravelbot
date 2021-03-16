package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.IncorrectRequestException;
import com.lookfor.iwannatravel.exceptions.UserNotFoundException;
import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.lookfor.iwannatravel.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCountryCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserService userService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        StringBuilder sbResponse = new StringBuilder();

        try {
            Integer userId = message.getFrom().getId();
            if (restOfTextMessage.isEmpty()) {
                sbResponse.append(String.format("You are from *%s*🥳\n", userService.getUserDepartureCountryName(userId)));
            } else {
                userService.saveUserDepartureCountry(userId, restOfTextMessage);
                sbResponse.append("Your country was successfully saved!😎\n");
            }
        } catch (CountryNotFoundException | UserNotFoundException | IncorrectRequestException exp) {
            log.error(exp.getMessage());
            sbResponse.append(exp.getMessage());
        }

        sbResponse.append("\n✍️To *change* your country, simply use command */from + country name*\n");
        sbResponse.append("⭐️To *add* a country you want to travel🏝, use command */to + country name*\n");
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(sbResponse.toString())
                .build();
    }
}
