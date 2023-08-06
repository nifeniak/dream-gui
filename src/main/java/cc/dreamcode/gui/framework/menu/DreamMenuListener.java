package cc.dreamcode.gui.framework.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.function.Consumer;

public class DreamMenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder instanceof DreamMenu dreamMenu) {

            if (dreamMenu.isDisableInteractions()) {
                event.setCancelled(true);
            }

            int slot = event.getRawSlot();
            dreamMenu.getItemBySlot(slot).ifPresentOrElse(dreamItem -> {

                Consumer<InventoryClickEvent> eventConsumer = dreamItem.getEventConsumer();
                if (eventConsumer == null) {
                    return;
                }

                eventConsumer.accept(event);

            }, () -> event.setCancelled(true));

        }
    }
}
