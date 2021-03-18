package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.bot.CountryButtonsDisplay;
import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.IncorrectRequestException;
import com.lookfor.iwannatravel.exceptions.UserNotFoundException;
import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.services.ParseScheduler;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
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
    private final CountryButtonsDisplay countryButtonsDisplay;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        int userId = Math.toIntExact(update.hasCallbackQuery() ? message.getChat().getId() : message.getFrom().getId());
        String textMessage = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : message.getText();
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(textMessage);
        StringBuilder sbResponse = new StringBuilder();

        SendMessage sendMessage = new SendMessage();
        try {
            if (restOfTextMessage.isEmpty()) {
                sendMessage.setReplyMarkup(countryButtonsDisplay.getInlineKeyBoardMarkup("to"));
            } else {
                userService.saveUserArrivalCountry(userId, restOfTextMessage);
                sbResponse.append("Country was added to your favorites!👌\n");
                parseScheduler.startParserWithChecks(userId, userService.getUserDepartureCountryName(userId), restOfTextMessage);
            }
        } catch (TelegramApiException | InterruptedException | ExecutionException| CountryNotFoundException | UserNotFoundException | IncorrectRequestException exp) {
            log.error(exp.getMessage());
            sbResponse.append(exp.getMessage());
        }

        sbResponse.append("\n👀*View* all your favorite countries using command */favorites*\n");

        sendMessage.setChatId((String.valueOf(message.getChatId())));
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setText(sbResponse.toString());
        return sendMessage;
    }
}
