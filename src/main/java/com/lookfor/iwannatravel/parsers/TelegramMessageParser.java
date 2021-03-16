package com.lookfor.iwannatravel.parsers;

import com.lookfor.iwannatravel.bot.CountryButtonsDisplay;
import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.config.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor
public class TelegramMessageParser extends Thread {
    private final TelegramBot telegramBot;
    private final Update update;
    private final RootCommandHandler<?> rootCommandHandler;
    private final ApplicationContext appContext;

    @Override
    public void run() {
        if (rootCommandHandler == null) {
            return;
        }

        Message message = update.hasMessage() ?
                update.getMessage() : update.hasEditedMessage() ?
                update.getEditedMessage() : update.hasCallbackQuery() ?
                update.getCallbackQuery().getMessage() : null;

        if (message == null) {
            return;
        }

        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("right") ||
                    update.getCallbackQuery().getData().equals("left")) {
                EditMessageText editButtonsReplyMarkup = new EditMessageText();
                editButtonsReplyMarkup.setChatId(String.valueOf(message.getChatId()));
                editButtonsReplyMarkup.setMessageId(message.getMessageId());
                InlineKeyboardMarkup markup = appContext.getBean(CountryButtonsDisplay.class).scrollTo(update.getCallbackQuery().getData());
                editButtonsReplyMarkup.setReplyMarkup(markup);
                editButtonsReplyMarkup.setText(message.getText());

                try {
                    telegramBot.execute(editButtonsReplyMarkup);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                PartialBotApiMethod<?> method = rootCommandHandler.doParse(update);

                if (method instanceof SendMessage) {
                    log.info(String.format(
                            "To @%s (%s): '%s'", message.getFrom().getUserName(), message.getChatId(), ((SendMessage) method).getText())
                    );
                    try {
                        telegramBot.execute((SendMessage) method);
                    } catch (TelegramApiException exp) {
                        log.error(exp.getMessage());
                    }
                }
            } catch (Exception exp) {
                log.error(exp.getMessage());
            }
        }
    }
}
