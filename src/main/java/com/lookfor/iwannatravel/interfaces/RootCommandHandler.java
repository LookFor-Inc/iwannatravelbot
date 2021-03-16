package com.lookfor.iwannatravel.interfaces;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.ExecutionException;

/**
 * Parent (root) handler interface
 *
 * @param <T> type of message to send
 */
public interface RootCommandHandler<T extends PartialBotApiMethod<?>> {
    /**
     * Execute a concrete command
     *
     * @param update received updates
     * @return bot api method
     */
    T doParse(Update update) throws ExecutionException, InterruptedException, TelegramApiException;

    /**
     * Get entire user's message
     *
     * @param update received updates
     * @return received message
     */
    default Message getReceivedMessage(Update update) {
        return update.hasMessage() ?
                update.getMessage() : update.hasEditedMessage() ?
                update.getEditedMessage() : null;
    }
}
