package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.IncorrectRequestException;
import com.lookfor.iwannatravel.exceptions.UserNotFoundException;
import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.services.CountryService;
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

import java.util.List;

import static com.lookfor.iwannatravel.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFavoriteCountryCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserService userService;
    private final ParseScheduler parseScheduler;
    private final CountryService countryService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        int userId = message.getFrom().getId();
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        StringBuilder sbResponse = new StringBuilder();

        try {
            if (restOfTextMessage.isEmpty()) {
                List<Country> countries = countryService.fetchAllCountries();
                // TODO: send countries to keyboard
                for (int i = 0; i < 10; i++) {
                    sbResponse.append(countries.get(i).getEn()).append("\n");
                }
            } else {
                userService.saveUserArrivalCountry(message.getFrom().getId(), restOfTextMessage);
                sbResponse.append("Country was added to your favorites!👌\n");
                parseScheduler.startParserWithChecks(userId, userService.getUserDepartureCountryName(userId), restOfTextMessage);
            }
        } catch (TelegramApiException | InterruptedException | ExecutionException| CountryNotFoundException | UserNotFoundException | IncorrectRequestException exp) {
            log.error(exp.getMessage());
            sbResponse.append(exp.getMessage());
        }

        sbResponse.append("\n👀*View* all your favorite countries using command */favorites*\n");
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(sbResponse.toString())
                .build();
    }
}
