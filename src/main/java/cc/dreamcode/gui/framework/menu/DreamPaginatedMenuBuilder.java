package cc.dreamcode.gui.framework.menu;

import cc.dreamcode.gui.framework.DreamGui;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class DreamPaginatedMenuBuilder {
    private DreamMenu template;
    private Component nextPageDoesNotExist;
    private Component previousPageDoesNotExist;

    public DreamPaginatedMenuBuilder template(@NotNull DreamMenu template) {
        this.template = template;
        return this;
    }

    public DreamPaginatedMenuBuilder nextPageDoesNotExistMessage(@NotNull Component nextPageDoesNotExist) {
        this.nextPageDoesNotExist = nextPageDoesNotExist;
        return this;
    }

    public DreamPaginatedMenuBuilder previousPageDoesNotExistMessage(@NotNull Component previousPageDoesNotExist) {
        this.previousPageDoesNotExist = previousPageDoesNotExist;
        return this;
    }

    public DreamPaginatedMenuBuilder nextPageDoesNotExistMessage(@NotNull String nextPageDoesNotExist) {
        this.nextPageDoesNotExist = DreamGui.textFormatter().parse(nextPageDoesNotExist);
        return this;
    }

    public DreamPaginatedMenuBuilder previousPageDoesNotExistMessage(@NotNull String previousPageDoesNotExist) {
        this.previousPageDoesNotExist = DreamGui.textFormatter().parse(previousPageDoesNotExist);
        return this;
    }

    public DreamPaginatedMenu build() {
        return new DreamPaginatedMenu(template, nextPageDoesNotExist, previousPageDoesNotExist);
    }
}