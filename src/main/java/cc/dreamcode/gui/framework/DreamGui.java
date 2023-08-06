package cc.dreamcode.gui.framework;

import cc.dreamcode.gui.framework.menu.DreamItemBuilder;
import cc.dreamcode.gui.framework.menu.DreamMenuBuilder;
import cc.dreamcode.gui.framework.menu.DreamMenuListener;
import cc.dreamcode.gui.framework.menu.DreamPaginatedMenuBuilder;
import cc.dreamcode.gui.framework.text.DreamTextBuilder;
import cc.dreamcode.gui.framework.text.formatter.DreamTextFormatter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class DreamGui {

    private static DreamTextFormatter DREAM_TEXT_FORMATTER;

    public static void register(@NotNull Plugin plugin, @NotNull DreamTextFormatter dreamTextFormatter) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new DreamMenuListener(), plugin);

        DREAM_TEXT_FORMATTER = dreamTextFormatter;
    }

    public static DreamMenuBuilder classic() {
        return new DreamMenuBuilder();
    }

    public static DreamPaginatedMenuBuilder paginated() {
        return new DreamPaginatedMenuBuilder();
    }

    public static DreamItemBuilder itemBuilder() {
        return new DreamItemBuilder().textFormatter(DREAM_TEXT_FORMATTER);
    }

    public static DreamTextBuilder textBuilder() {
        return new DreamTextBuilder().textFormatter(DREAM_TEXT_FORMATTER);
    }

    public static DreamTextFormatter textFormatter() {
        return DREAM_TEXT_FORMATTER;
    }


}
