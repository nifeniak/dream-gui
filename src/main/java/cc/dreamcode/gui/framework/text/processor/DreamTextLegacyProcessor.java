package cc.dreamcode.gui.framework.text.processor;

import cc.dreamcode.gui.framework.text.formatter.impl.LegacyTextFormatter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public final class DreamTextLegacyProcessor implements UnaryOperator<Component> {

    private final LegacyTextFormatter legacyTextFormatter;

    @Override
    public Component apply(Component component) {
        return component.replaceText(builder -> builder.match(Pattern.compile(".*"))
                .replacement((matchResult, builder1) -> this.legacyTextFormatter.parse(matchResult.group())));
    }

}