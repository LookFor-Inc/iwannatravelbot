package com.lookfor.iwannatravel.bot.handlers;

import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.services.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.lookfor.iwannatravel.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCountryCommandHandler implements RootCommandHandler<SendMessage> {
    private final CountryService countryService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        log.info(restOfTextMessage);
        log.info(String.valueOf(message.getText()));
        StringBuilder sb = new StringBuilder();
        List<Country> countries = countryService.fetchAllCountries();
        countries.forEach(c -> sb.append(c.getRu()).append("\n"));
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text(sb.toString())
                .build();
    }
}
