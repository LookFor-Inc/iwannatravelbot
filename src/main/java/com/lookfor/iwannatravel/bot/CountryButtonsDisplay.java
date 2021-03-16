package com.lookfor.iwannatravel.bot;

import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.services.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CountryButtonsDisplay {
    private final CountryService countryService;
    private final int RANGE = 26;
    private int firstCountryFromList = 0;
    private final String LEFT_CMD = "left";
    private final String RIGHT_CMD = "right";

    private List<List<InlineKeyboardButton>> initKeyboard(int firstCountryNumber, int lastCountryNumber) {
        List<Country> countries = countryService.getAllSortedCountries();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        int i = firstCountryNumber;
        while (i < lastCountryNumber) {
            ArrayList<InlineKeyboardButton> buttonsInRow = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(countries.get(i).getEn());
                inlineKeyboardButton.setCallbackData("from " + countries.get(i).getEn());
                buttonsInRow.add(inlineKeyboardButton);
                i++;
            }
            rowList.add(buttonsInRow);
        }
        return rowList;
    }

    private List<InlineKeyboardButton> getArrowButtons(String turnSide, boolean isLastList) {
        ArrayList<InlineKeyboardButton> arrowButtons = new ArrayList<>();

        InlineKeyboardButton leftIKB = new InlineKeyboardButton();
        leftIKB.setText("⬅");
        leftIKB.setCallbackData(LEFT_CMD);

        InlineKeyboardButton rightIKB = new InlineKeyboardButton();
        rightIKB.setText("➡");
        rightIKB.setCallbackData(RIGHT_CMD);

        if (isLastList) {
            if (turnSide.equals(LEFT_CMD)) {
                arrowButtons.add(rightIKB);
                return arrowButtons;
            } else if (turnSide.equals(RIGHT_CMD)) {
                arrowButtons.add(leftIKB);
                return arrowButtons;
            }
        }
        arrowButtons.add(leftIKB);
        arrowButtons.add(rightIKB);

        return arrowButtons;
    }

    public InlineKeyboardMarkup getInlineKeyBoardMarkup() {
        List<List<InlineKeyboardButton>> rowList = initKeyboard(0, RANGE);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);

        rowList.add(getArrowButtons(LEFT_CMD, true));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup scrollTo(String turnSide) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        int COUNTRIES_SIZE = 234;
        if (turnSide.equals(RIGHT_CMD) && firstCountryFromList < COUNTRIES_SIZE - RANGE) {
            firstCountryFromList += RANGE;
        } else if (turnSide.equals(LEFT_CMD) && firstCountryFromList > 0) {
            firstCountryFromList -= RANGE;
        }
        List<List<InlineKeyboardButton>> rowList = initKeyboard(firstCountryFromList, firstCountryFromList + RANGE);
        if (firstCountryFromList <= 0 || firstCountryFromList >= COUNTRIES_SIZE - RANGE) {
            rowList.add(getArrowButtons(turnSide, true));
        } else {
            rowList.add(getArrowButtons(turnSide, false));
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

}
