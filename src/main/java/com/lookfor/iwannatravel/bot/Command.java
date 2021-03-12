package com.lookfor.iwannatravel.bot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Command enum
 */
public enum Command {
    GREETING(new HashSet<>(Arrays.asList("hello", "hi", "привет")));

    private final Set<String> set;
    private final String obj;
    private final String COMMAND_HANDLER = "CommandHandler";

    Command(Set<String> set) {
        this.set = set;
        this.obj = name().toLowerCase();
    }

    /**
     * Get the Command obj by its element of set
     *
     * @param name to find
     * @return Command obj
     */
    public static Command getByName(String name) {
        return Arrays.stream(values())
                .filter(cmdVal -> cmdVal.set.contains(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get handler Bean name to manage command
     *
     * @return handler's bean name
     */
    public String getHandlerBeanName() {
        return obj + COMMAND_HANDLER;
    }
}
