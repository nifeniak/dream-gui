package cc.dreamcode.gui.framework.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class DreamItem {

    private ItemStack itemStack;
    private Consumer<InventoryClickEvent> eventConsumer;

    public DreamItem(@NotNull ItemStack itemStack) {
        this(itemStack, null);
    }

}
