package com.lookfor.iwannatravel.bot;

import java.util.Arrays;

import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static com.google.common.base.CaseFormat.LOWER_CAMEL;

/**
 * Command enum
 */
public enum Command {
    GREETING("start"),
    HELP("help"),
    USER_COUNTRY("from");

    private final String cmd;
    private final String obj;
    private final String COMMAND_HANDLER = "CommandHandler";

    Command(String cmd) {
        this.cmd = cmd;
        this.obj = UPPER_UNDERSCORE.to(LOWER_CAMEL, name());
    }

    /**
     * Get the Command obj by its command name
     *
     * @param name to find
     * @return Command obj
     */
    public static Command getByName(String name) {
        return Arrays.stream(values())
                .filter(cmdVal -> cmdVal.cmd.equals(name))
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
