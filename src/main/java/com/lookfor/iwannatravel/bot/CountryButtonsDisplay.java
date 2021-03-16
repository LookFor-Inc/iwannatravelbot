package com.lookfor.iwannatravel.bot;

import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.services.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CountryButtonsDisplay {
    private final CountryService countryService;

    public SendMessage sendInlineKeyBoardMessage(long chatId) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<Country> countries = countryService.fetchAllCountries();

        for (int i=0; i < countries.size(); i+=2) {
            int finalI = i;
            log.info(countries.get(finalI).getRu());
            ArrayList<InlineKeyboardButton> rowBtns = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
            inlineKeyboardButton1.setText(countries.get(finalI).getRu());
            inlineKeyboardButton1.setCallbackData("from Russia");
            rowBtns.add(inlineKeyboardButton1);

            InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
            inlineKeyboardButton2.setText(countries.get(finalI + 1).getRu());
            inlineKeyboardButton2.setCallbackData("from Russia");
            rowBtns.add(inlineKeyboardButton2);

            rowList.add(rowBtns);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Список стран");
        message.setReplyMarkup(inlineKeyboardMarkup);
        return message;
    }
}
