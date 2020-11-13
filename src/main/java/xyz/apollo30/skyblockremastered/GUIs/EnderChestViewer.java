package xyz.apollo30.skyblockremastered.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.apollo30.skyblockremastered.GUIs.constructor.Menu;
import xyz.apollo30.skyblockremastered.GUIs.constructor.MenuUtility;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.templates.PlayerTemplate;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class EnderChestViewer extends Menu {

    private final Player plr;

    public EnderChestViewer(MenuUtility menuUtility, Player plr) {
        super(menuUtility);
        this.plr = plr;
    }

    @Override
    public String getMenuName() {
        return plr.getName() + "'s Enderchest";
    }

    @Override
    public int getSlots() {
        PlayerTemplate po = PlayerManager.playerObjects.get(plr);
        Inventory enderChest = Helper.stringToInventory(po.getEnderChest());
        if (enderChest == null) return 54;
        else return enderChest.getSize();
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {

        PlayerTemplate po = PlayerManager.playerObjects.get(plr);
        Inventory enderChest = Helper.stringToInventory(po.getEnderChest());
        if (enderChest == null) {
            for (int i = 0; i < 54; i++) {
                ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
                ItemMeta meta = glass.getItemMeta();
                meta.setDisplayName(Utils.chat("&cCouldn't Load Enderchest Contents"));
                glass.setItemMeta(meta);
                inv.setItem(i, glass);
            }
        } else {
            for (int i = 0; i < enderChest.getSize(); i++) {
                ItemStack item = enderChest.getItem(i);
                if (item == null) break;
                else inv.setItem(i, item);
            }
        }

    }
}
