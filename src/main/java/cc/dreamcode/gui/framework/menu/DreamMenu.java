package cc.dreamcode.gui.framework.menu;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DreamMenu implements InventoryHolder {

    @Getter
    private final Component title;
    @Getter
    private final int rows;
    @Getter
    private final boolean disableInteractions;
    @Getter
    private final DreamMenuFiller filler;
    private Inventory inventory;

    @Setter
    @Getter
    private Map<Integer, DreamItem> menuItems;

    public DreamMenu(@NotNull Component title, int rows, boolean disableInteractions) {
        this.title = title;
        this.rows = rows;
        this.disableInteractions = disableInteractions;
        this.filler = new DreamMenuFiller(this);
        this.inventory = Bukkit.createInventory(this, this.getRows() * 9, title);
        this.menuItems = new HashMap<>();
    }

    public DreamMenu clone() {
        DreamMenu dreamMenu = new DreamMenu(this.title, this.rows, this.disableInteractions);
        for (Map.Entry<Integer, DreamItem> entry : this.menuItems.entrySet()) {
            Integer key = entry.getKey();
            DreamItem value = entry.getValue();
            dreamMenu.getMenuItems().put(key, value);
        }
        return dreamMenu;
    }

    public void addItem(@NotNull DreamItem dreamItem) {
        int maxIndex = this.getRows() * 9;
        for (int i = 0; i < maxIndex; i++) {
            this.menuItems.putIfAbsent(i, dreamItem);
        }
    }

    public void setItem(int slot, @NotNull DreamItem dreamItem) {
        if (slot < 0) {
            return;
        }

        this.menuItems.put(slot, dreamItem);
    }

    public void setItem(int row, int column, @NotNull DreamItem dreamItem) {
        int slot = (column + (row - 1) * 9) - 1;
        if (slot < 0) {
            return;
        }
        this.menuItems.put(slot, dreamItem);
    }

    public void setItem(@NotNull List<Integer> slots, @NotNull DreamItem dreamItem) {
        for (Integer slot : slots) {
            setItem(slot, dreamItem);
        }
    }

    public void crowdInventory() {
        for (Map.Entry<Integer, DreamItem> entry : this.menuItems.entrySet()) {
            this.inventory.setItem(entry.getKey(), entry.getValue().getItemStack());
        }
    }

    int getLastFreeSlot() {
        int freeSlot = -1;
        for (int i = 0; i < this.getRows() * 9; i++) {
            if (this.menuItems.get(i) == null) {
                return i;
            }
        }

        return freeSlot;
    }

    public void update() {
        this.inventory.clear();
        crowdInventory();
        for (HumanEntity viewer : this.inventory.getViewers()) {
            ((Player) viewer).updateInventory();
        }
    }

    public void updateTitle(@NotNull Component title) {
        Inventory updated = Bukkit.createInventory(this, this.getRows() * 9, title);
        updated.setContents(this.inventory.getContents());
        this.inventory = updated;
    }

    public void open(@NotNull HumanEntity humanEntity) {
        crowdInventory();
        humanEntity.openInventory(this.inventory);
    }

    Optional<DreamItem> getItemBySlot(int slot) {
        return Optional.ofNullable(this.menuItems.get(slot));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
