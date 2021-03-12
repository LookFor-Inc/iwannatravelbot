package com.lookfor.iwannatravel.handlers;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler<T extends PartialBotApiMethod<?>> {
    /**
     * Execute a concrete command
     *
     * @param update received updates
     * @return bot api method
     * @throws Exception exp
     */
    T doParse(Update update) throws Exception;

    /**
     * Get message depending on whether it has been edited or not
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
