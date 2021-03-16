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

import java.util.List;

import static com.lookfor.iwannatravel.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemoveFavoriteCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserService userService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        StringBuilder sbResponse = new StringBuilder();

        try {
            Integer userId = message.getFrom().getId();
            if (restOfTextMessage.isEmpty()) {
                sbResponse.append("🔖Select country you want to remove from your favorites\n");
            } else {
                List<String> arrCountries = userService.fetchUserArrivalCountries(userId);
                if (arrCountries.isEmpty()) {
                    sbResponse.append("😔No favorite countries added\n\n");
                } else {
                    userService.removeUserArrivalCountry(userId, restOfTextMessage);
                    sbResponse.append("Country was removed!✅\n");
                }
            }
        } catch (CountryNotFoundException | UserNotFoundException | IncorrectRequestException exp) {
            log.error(exp.getMessage());
            sbResponse.append(exp.getMessage());
        }

        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(sbResponse.toString())
                .build();
    }
}
