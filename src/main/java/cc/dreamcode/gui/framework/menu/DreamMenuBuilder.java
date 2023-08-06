package cc.dreamcode.gui.framework.menu;

import cc.dreamcode.gui.framework.DreamGui;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;


public final class DreamMenuBuilder {
    private Component title;
    private int rows;
    private boolean disableInteractions = false;

    public DreamMenuBuilder title(@NotNull Component title) {
        this.title = title;
        return this;
    }

    public DreamMenuBuilder title(@NotNull String title) {
        this.title = DreamGui.textFormatter().parse(title);
        return this;
    }

    public DreamMenuBuilder rows(int rows) {
        this.rows = rows;
        return this;
    }

    public DreamMenuBuilder disableInteractions() {
        this.disableInteractions = true;
        return this;
    }

    public DreamMenu build() {
        return new DreamMenu(title, rows, disableInteractions);
    }
}