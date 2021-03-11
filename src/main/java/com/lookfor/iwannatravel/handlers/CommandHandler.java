package com.lookfor.iwannatravel.handlers;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler<T extends PartialBotApiMethod<?>> {
    T doParse(Update update) throws Exception;
}
