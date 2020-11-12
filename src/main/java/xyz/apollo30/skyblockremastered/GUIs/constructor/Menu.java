package xyz.apollo30.skyblockremastered.GUIs.constructor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class Menu implements InventoryHolder {

    protected Inventory inv;
    protected MenuUtility menuUtility;
    protected ItemStack GLASS = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
    public Menu(MenuUtility menuUtility) {
        this.menuUtility = menuUtility;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();

    public void open() {
        inv = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        menuUtility.getOwner().openInventory(inv);
    }

    public ItemStack makeItem(Material material, String displayName, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }


    @Override
    public Inventory getInventory() {
        return inv;
    }

}
