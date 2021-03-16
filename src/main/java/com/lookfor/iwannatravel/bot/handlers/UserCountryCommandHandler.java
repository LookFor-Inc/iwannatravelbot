package com.lookfor.iwannatravel.bot.handlers;

// import com.lookfor.iwannatravel.bot.CountryButtonsDisplay;
import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.IncorrectRequestException;
import com.lookfor.iwannatravel.exceptions.UserNotFoundException;
import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.services.CountryService;
import com.lookfor.iwannatravel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.lookfor.iwannatravel.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCountryCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserService userService;
    private final CountryService countryService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        String textMessage = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : message.getText();
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(textMessage);
        StringBuilder sbResponse = new StringBuilder();

        SendMessage sendMessage = new SendMessage();
        try {
            Integer userId = message.getFrom().getId();
            if (restOfTextMessage.isEmpty()) {
                sendMessage.setReplyMarkup(getInlineKeyBoardMarkup());
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

        sendMessage.setChatId((String.valueOf(message.getChatId())));
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setText(sbResponse.toString());
        return sendMessage;
    }

    public InlineKeyboardMarkup getInlineKeyBoardMarkup() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<Country> countries = countryService.fetchAllCountries();

        for (int i=0; i < countries.size(); i+=2) {
            log.info(countries.get(i).getRu());
            ArrayList<InlineKeyboardButton> rowBtns = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
            inlineKeyboardButton1.setText(countries.get(i).getRu());
            inlineKeyboardButton1.setCallbackData("from " + countries.get(i).getEn().toLowerCase());
            rowBtns.add(inlineKeyboardButton1);

            InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
            inlineKeyboardButton2.setText(countries.get(i + 1).getRu());
            inlineKeyboardButton2.setCallbackData("from " + countries.get(i +1).getEn().toLowerCase());
            rowBtns.add(inlineKeyboardButton2);

            rowList.add(rowBtns);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
