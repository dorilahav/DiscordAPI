package com.dorilahav.api.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.dorilahav.api.chat.Usages;

import lombok.Getter;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;

public class CommandHandler {

    @Getter
    private List<CommandWrapper>
            commands = new CopyOnWriteArrayList<>();


    public void registerCommand(Object object) {
        Class<?> clazz = object.getClass();

        for (Method method : Arrays.asList(clazz.getDeclaredMethods()).stream().filter(method -> method.isAnnotationPresent(Command.class)).collect(Collectors.toSet())) {

            Command command = method.getDeclaredAnnotation(Command.class);
            CommandExecutor executor = new CommandExecutor(method, object, command.processFlags());

            CommandWrapper wrapper = new CommandWrapper(command.label().toLowerCase(), Arrays.stream(command.aliases()).map(s -> s.toLowerCase()).collect(Collectors.toList()), executor, command.type());
            commands.add(wrapper);

            if (!command.usage().isEmpty()) {
                Usages.set(wrapper.getLabel(), command.usage());
                for (String alias : wrapper.getAliases())
                    Usages.set(alias, command.usage());
            }

        }
    }

    public int getCommandsAmount() {
        return commands.size();
    }

    public boolean isCommand(Message message, String prefix) {
        return isCommand(getLabel(message, prefix));
    }

    public boolean isCommand(String label) {

        if (label == null)
            return false;

        return commands.stream().anyMatch(wrapper -> wrapper.getLabel().equals(label) || wrapper.getAliases().contains(label.toLowerCase()));
    }

    public String getLabel(Message message, String prefix) {

        String content = message.getContentRaw();

        if (!content.toLowerCase().startsWith(prefix.toLowerCase()))
            return null;

        return content.split(" ")[0].toLowerCase().replaceFirst("\\" + prefix.toLowerCase(), "");
    }


    public boolean handle(Message message, String prefix) {

        String content = message.getContentRaw();
        String label = getLabel(message, prefix);

        if (!isCommand(label))
            return false;

        CommandWrapper cmd = commands.stream().filter(wrapper -> wrapper.getLabel().equals(label) || wrapper.getAliases().contains(label)).limit(1).findFirst().orElse(null);
        if (cmd == null)
            return false;

        boolean execute = false;

        switch (cmd.getCommandType()) {

            case PRIVATE:
                if (message.isFromType(ChannelType.PRIVATE))
                    execute = true;
                break;

            case PUBLIC:
                if (message.isFromType(ChannelType.TEXT))
                    execute = true;
                break;

            case BOTH:
                execute = true;
                break;

            default:
                break;

        }

        if (!execute)
            return false;

        content = content.replaceFirst("(?i)\\" + prefix + Pattern.quote(label), "");

        if (content.startsWith(" "))
            content = content.replaceFirst(" ", "");

        String[] args = content.split(" ");

        if (content.isEmpty() || args == null)
            args = new String[0];

        return cmd.getCommandExecutor().run(message, prefix, label, args);
    }

}
