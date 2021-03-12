package com.lookfor.iwannatravel.parsers;

import com.lookfor.iwannatravel.interfaces.RootCommandHandler;
import com.lookfor.iwannatravel.config.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor
public class TelegramMessageParser extends Thread {
    private final TelegramBot telegramBot;
    private final Update update;
    private final RootCommandHandler<?> rootCommandHandler;

    @Override
    public void run() {
        if (rootCommandHandler == null) {
            return;
        }

        Message message = update.hasMessage() ?
                update.getMessage() : update.hasEditedMessage() ?
                update.getEditedMessage() : null;
        if (message == null) {
            return;
        }

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
