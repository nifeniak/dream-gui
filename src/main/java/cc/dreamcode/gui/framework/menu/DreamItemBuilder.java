package cc.dreamcode.gui.framework.menu;

import cc.dreamcode.gui.framework.text.formatter.DreamTextFormatter;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class DreamItemBuilder {

    private final ItemStack itemStack;
    @Getter
    private final ItemMeta itemMeta;
    private DreamTextFormatter textFormatter;

    public DreamItemBuilder() {
        this.itemStack = null;
        this.itemMeta = null;
    }

    private DreamItemBuilder(@NotNull Material material, @NotNull int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    private DreamItemBuilder(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public static DreamItemBuilder of(@NotNull Material material) {
        return new DreamItemBuilder(material, 1);
    }

    public static DreamItemBuilder of(@NotNull Material material, int amount) {
        return new DreamItemBuilder(material, amount);
    }

    public static DreamItemBuilder of(@NotNull ItemStack item) {
        return new DreamItemBuilder(item);
    }

    public DreamItemBuilder textFormatter(@NotNull DreamTextFormatter textFormatter) {
        this.textFormatter = textFormatter;
        return this;
    }

    public void refreshMeta() {
        this.itemStack.setItemMeta(itemMeta);
    }

    public DreamItemBuilder name(@NotNull String name) {
        this.itemMeta.displayName(this.textFormatter.parse(name));
        this.refreshMeta();

        return this;
    }

    public DreamItemBuilder lore(@NotNull List<String> lore) {
        this.itemMeta.lore(this.textFormatter.parse(lore));
        this.refreshMeta();

        return this;
    }

    public DreamItemBuilder lore(@NotNull String... lore) {
        return lore(Arrays.asList(lore));
    }

    public DreamItemBuilder appendLore(@NotNull List<String> lore) {
        ItemMeta itemMeta = this.itemMeta;
        if (!itemMeta.hasLore()) {
            itemMeta.lore(this.textFormatter.parse(lore));
        } else {
            List<Component> newLore = itemMeta.lore();
            newLore.addAll(this.textFormatter.parse(lore));
            itemMeta.lore(newLore);
        }

        refreshMeta();
        return this;
    }

    public DreamItemBuilder appendLore(@NotNull String lore) {
        return this.appendLore(Collections.singletonList(lore));
    }

    public DreamItemBuilder appendLore(@NotNull String... lore) {
        return this.appendLore(Arrays.asList(lore));
    }

    public DreamItemBuilder enchantment(@NotNull Enchantment enchant, int level) {
        this.itemMeta.addEnchant(enchant, level, true);
        this.refreshMeta();

        return this;
    }

    public DreamItemBuilder flag(@NotNull ItemFlag flag) {
        this.itemMeta.addItemFlags(flag);
        this.refreshMeta();

        return this;
    }

    public DreamItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public DreamItemBuilder glow() {
        return this.glow(true);
    }

    public DreamItemBuilder glow(boolean glow) {
        if (glow) {
            this.itemMeta.addEnchant(Enchantment.LURE, 1, false);
            this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {

            for (Enchantment enchantment : this.itemMeta.getEnchants().keySet()) {
                this.itemMeta.removeEnchant(enchantment);
            }

        }
        refreshMeta();
        return this;
    }

    public ItemStack buildAsItemStack() {
        return this.itemStack;
    }

    public DreamItem buildAsDream() {
        return new DreamItem(this.itemStack);
    }

    public DreamItem buildAsDream(@NotNull Consumer<InventoryClickEvent> eventConsumer) {
        return new DreamItem(this.itemStack, eventConsumer);

    }
}