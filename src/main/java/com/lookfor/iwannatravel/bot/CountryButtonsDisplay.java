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
    private final int RANGE_ROWS = 13;
    private int firstRowNumberFromList = 0;
    private final String LEFT_CMD = "left";
    private final String RIGHT_CMD = "right";
    private final List<List<InlineKeyboardButton>> fulRowList = new ArrayList<>();

    private void initKeyboard(String command) {
        fulRowList.clear();
        List<Country> countries = countryService.getAllSortedCountries();
        int i = 0;
        while (i < countries.size()) {
            ArrayList<InlineKeyboardButton> buttonsInRow = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(countries.get(i).getEn());
                inlineKeyboardButton.setCallbackData(command + " " + countries.get(i).getEn());
                buttonsInRow.add(inlineKeyboardButton);
                i++;
            }
            fulRowList.add(buttonsInRow);
        }
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

    public InlineKeyboardMarkup getInlineKeyBoardMarkup(String command) {
        initKeyboard(command);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        for (int i = 0; i < RANGE_ROWS; i++) {
            rowList.add(fulRowList.get(i));
        }
        inlineKeyboardMarkup.setKeyboard(rowList);

        rowList.add(getArrowButtons(LEFT_CMD, true));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup scrollTo(String turnSide) {
        int FULL_ROW_LIST_SIZE = fulRowList.size();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        if (turnSide.equals(RIGHT_CMD) && firstRowNumberFromList < FULL_ROW_LIST_SIZE - RANGE_ROWS) {
            firstRowNumberFromList += RANGE_ROWS;
        } else if (turnSide.equals(LEFT_CMD) && firstRowNumberFromList > 0) {
            firstRowNumberFromList -= RANGE_ROWS;
        }
        for (int i = firstRowNumberFromList; i < firstRowNumberFromList + RANGE_ROWS; i++) {
            rowList.add(fulRowList.get(i));
        }
        if (firstRowNumberFromList <= 0 || firstRowNumberFromList >= FULL_ROW_LIST_SIZE - RANGE_ROWS) {
            rowList.add(getArrowButtons(turnSide, true));
        } else {
            rowList.add(getArrowButtons(turnSide, false));
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

}
