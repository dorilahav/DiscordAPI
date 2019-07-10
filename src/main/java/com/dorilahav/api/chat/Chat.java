package com.dorilahav.api.chat;

import net.dv8tion.jda.core.utils.Checks;

public class Chat {

    /**
     * Process a String to make it ready to be sent as a message.
     *
     * @param input the String to process.
     * @param args  the arguments to replace.
     * @return the processed String.
     */
    public static String processString(final String input, final String... args) {
        Checks.notEmpty(input, "input");
        Checks.notEmpty(args, "args");

        if (args == null || args.length == 0)
            return input;

        if (args.length % 2 == 1)
            throw new IllegalArgumentException("Amount of arguments has to be even.");

        String output = input;
        for (int i = 0; i < args.length; i += 2) {
            if (i + 1 < args.length) {
                String arg = args[i];

                if (!arg.startsWith("{"))
                    arg = "{" + arg;

                if (!arg.endsWith("}"))
                    arg += "}";

                output = output.replace(arg, args[i + 1]);
            }
        }
        return output;
    }

}
