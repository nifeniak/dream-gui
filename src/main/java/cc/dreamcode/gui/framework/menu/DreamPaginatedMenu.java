package cc.dreamcode.gui.framework.menu;

import cc.dreamcode.gui.framework.DreamGui;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DreamPaginatedMenu {

    @Getter
    private final DreamMenu template;
    @Getter
    private final Component nextPageDoesNotExist;
    @Getter
    private final Component previousPageDoesNotExist;

    private final Map<Integer, DreamMenu> menusByPages;
    private final Map<DreamMenu, Integer> pagesByMenus;
    private final Map<UUID, Integer> pageViewers;

    private DreamMenu currentPage;

    public DreamPaginatedMenu(@NotNull DreamMenu template, Component nextPageDoesNotExist, Component previousPageDoesNotExist) {
        this.template = template;
        this.nextPageDoesNotExist = nextPageDoesNotExist;
        this.previousPageDoesNotExist = previousPageDoesNotExist;
        this.menusByPages = new HashMap<>();
        this.pagesByMenus = new HashMap<>();
        this.pageViewers = new HashMap<>();
    }

    public DreamMenu addPage(@NotNull DreamMenu dreamMenu, int page) {
        this.menusByPages.put(page, dreamMenu);
        this.pagesByMenus.put(dreamMenu, page);

        return dreamMenu;
    }

    public void nextPage(int slot, @NotNull ItemStack itemStack) {
        this.template.setItem(slot, DreamItemBuilder.of(itemStack).buildAsDream(event -> {

            event.setCancelled(true);

            HumanEntity humanEntity = event.getWhoClicked();
            int nextPage = this.pageViewers.getOrDefault(humanEntity.getUniqueId(), 0) + 1;

            if (this.menusByPages.size() <= nextPage) {
                humanEntity.sendMessage(this.nextPageDoesNotExist);
                return;
            }

            this.open(humanEntity, nextPage);

        }));
    }

    public void previousPage(int slot, @NotNull ItemStack itemStack) {
        this.template.setItem(slot, DreamItemBuilder.of(itemStack).buildAsDream(event -> {
            event.setCancelled(true);

            HumanEntity humanEntity = event.getWhoClicked();
            int previousPage = this.pageViewers.getOrDefault(humanEntity.getUniqueId(), 0) - 1;

            if (0 > previousPage) {
                humanEntity.sendMessage(this.previousPageDoesNotExist);
                return;
            }

            this.open(humanEntity, previousPage);

        }));
    }

    @Nullable
    private DreamMenu getLeastFilled() {

        for (int i = 0; i < this.menusByPages.size(); i++) {
            DreamMenu dreamMenu = this.menusByPages.get(i);
            if (dreamMenu != null) {
                return dreamMenu;
            }
        }

        return null;
    }

    public void refreshPagesTitle() {

        for (DreamMenu dreamMenu : this.menusByPages.values()) {

            TextComponent title = (TextComponent) dreamMenu.getTitle();
            String titleString = title.content();
            titleString = titleString
                    .replace("{PAGE}", String.valueOf(this.pagesByMenus.get(dreamMenu) + 1))
                    .replace("{MAX_PAGE}", String.valueOf(this.menusByPages.size()));

            dreamMenu.updateTitle(DreamGui.textFormatter().parse(titleString));

        }

    }

    public void addItem(@NotNull DreamItem dreamItem) {
        if (this.menusByPages.isEmpty()) {
            addPage(this.template.clone(), 0);
        }

        DreamMenu dreamMenu;
        if (this.currentPage == null) {
            dreamMenu = getLeastFilled();
            this.currentPage = dreamMenu;
        } else {
            dreamMenu = this.currentPage;
        }

        if (dreamMenu.getLastFreeSlot() == -1) {
            DreamMenu nextPage = this.template.clone();
            dreamMenu = nextPage;
            this.currentPage = dreamMenu;
            addPage(nextPage, this.menusByPages.size());
        }

        dreamMenu.setItem(dreamMenu.getLastFreeSlot(), dreamItem);
    }

    public void open(@NotNull HumanEntity humanEntity, int page) {
        DreamMenu menu = this.menusByPages.get(page);

        if (menu == null) {
            open(humanEntity);
            return;
        }

        refreshPagesTitle();
        this.pageViewers.put(humanEntity.getUniqueId(), page);
        menu.open(humanEntity);
    }

    public void open(@NotNull HumanEntity humanEntity) {
        DreamMenu firstPage = this.menusByPages.get(0);

        this.pageViewers.put(humanEntity.getUniqueId(), 0);

        refreshPagesTitle();
        firstPage.open(humanEntity);
    }

}
