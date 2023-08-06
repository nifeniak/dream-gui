package cc.dreamcode.gui.framework.text;

import cc.dreamcode.gui.framework.text.formatter.DreamTextFormatter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class DreamTextBuilder {

    private final List<String> text = new ArrayList<>();
    private final Map<String, String> placeholders = new HashMap<>();
    private DreamTextFormatter textFormatter;

    public DreamTextBuilder textFormatter(@NotNull DreamTextFormatter textFormatter) {
        this.textFormatter = textFormatter;
        return this;
    }

    public DreamTextBuilder text(@NotNull String message) {
        this.text.add(message);
        return this;
    }

    public DreamTextBuilder text(@NotNull List<String> message) {
        this.text.addAll(message);
        return this;
    }

    public DreamTextBuilder text(@NotNull String... message) {
        this.text.addAll(List.of(message));
        return this;
    }

    public DreamTextBuilder placeholder(@NotNull String from, @NotNull String to) {
        this.placeholders.put(from, to);
        return this;
    }

    public List<String> build() {
        if (!this.placeholders.isEmpty()) {
            List<String> replacedMessages = new ArrayList<>();

            for (String message : this.text) {

                String messageToReplace = message;
                for (Map.Entry<String, String> entry : this.placeholders.entrySet()) {
                    messageToReplace = messageToReplace.replace(entry.getKey(), entry.getValue());
                }

                replacedMessages.add(messageToReplace);

            }

            return replacedMessages;
        }

        return this.text;
    }

    public void send(@NotNull CommandSender commandSender) {
        send(Collections.singletonList(commandSender));
    }

    public void send(@NotNull List<CommandSender> receivers) {
        List<String> messages = build();
        if (receivers.isEmpty() || messages.isEmpty()) {
            return;
        }

        for (CommandSender commandSender : receivers) {

            for (String message : messages) {
                commandSender.sendMessage(this.textFormatter.parse(message));
            }

        }
    }
}
