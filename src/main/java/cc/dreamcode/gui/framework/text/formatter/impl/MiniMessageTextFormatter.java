package cc.dreamcode.gui.framework.text.formatter.impl;

import cc.dreamcode.gui.framework.text.formatter.DreamTextFormatter;
import cc.dreamcode.gui.framework.text.processor.DreamTextLegacyProcessor;
import com.google.common.base.Strings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MiniMessageTextFormatter implements DreamTextFormatter {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .postProcessor(new DreamTextLegacyProcessor(new LegacyTextFormatter()))
            .build();

    @Override
    public Component parse(@NotNull String text) {

        if (Strings.isNullOrEmpty(text)) {
            return Component.empty();
        }

        return MINI_MESSAGE.deserialize(text);
    }

    @Override
    public List<Component> parse(@NotNull List<String> text) {
        List<Component> list = new ArrayList<>();
        for (String it : text) {
            list.add(parse(it));
        }
        return list;
    }
}
