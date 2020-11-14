package xyz.apollo30.skyblockremastered.GUIs.constructor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.apollo30.skyblockremastered.GUIs.GUIHelper;

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

    public void addBorder() {
        GUIHelper.addItem(inv, GUIHelper.addLore(new ItemStack(Material.BARRIER), "&cClose"), 50);
        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 47, 48, 49, 51, 52, 53, 54);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

}
