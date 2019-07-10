package com.dorilahav.api.commands;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CommandWrapper {

    @Getter
    private final String
            label;

    @Getter
    private final List<String>
            aliases;

    @Getter
    private final CommandExecutor
            commandExecutor;

    @Getter
    private final CommandType
            commandType;

}
