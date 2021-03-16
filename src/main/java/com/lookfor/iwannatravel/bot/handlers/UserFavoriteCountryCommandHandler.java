package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.IncorrectRequestException;
import com.lookfor.iwannatravel.exceptions.UserNotFoundException;
import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.services.ParseScheduler;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.ExecutionException;

import static com.lookfor.iwannatravel.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFavoriteCountryCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserService userService;
    private final ParseScheduler parseScheduler;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        int userId = message.getFrom().getId();
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        String responseMessage;
        try {
            userService.saveUserArrivalCountry(userId, restOfTextMessage);
            responseMessage =
                    """
                            Country was added to your favorites!👌
                            Use command /favorites to view a list of all countries you want to travel🗺
                            """;
            parseScheduler.startParserWithChecks(userId, userService.getUserDepartureCountryName(userId), restOfTextMessage);
        } catch (TelegramApiException | InterruptedException | ExecutionException| CountryNotFoundException | UserNotFoundException | IncorrectRequestException exp) {
            log.error(exp.getMessage());
            responseMessage = exp.getMessage();
        }

        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text(responseMessage)
                .build();
    }
}
