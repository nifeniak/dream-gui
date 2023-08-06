package cc.dreamcode.gui.framework.text.formatter.impl;

import cc.dreamcode.gui.framework.text.formatter.DreamTextFormatter;
import com.google.common.base.Strings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LegacyTextFormatter implements DreamTextFormatter {

    private static final TextComponent RESET = Component.text()
            .decoration(TextDecoration.ITALIC, false)
            .build();

    private final static LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .build();

    @Override
    public Component parse(@NotNull String text) {
        if (Strings.isNullOrEmpty(text)) {
            return Component.empty();
        }

        return RESET.append(LEGACY_COMPONENT_SERIALIZER.deserialize(text));
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
