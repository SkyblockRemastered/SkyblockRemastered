package xyz.apollo30.skyblockremastered.guis.ItemBrowser;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.guis.constructors.Menu;
import xyz.apollo30.skyblockremastered.guis.constructors.MenuUtility;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class ItemBrowser extends Menu {

    public ItemBrowser(MenuUtility menuUtility) {
        super(menuUtility);
    }

    @Override
    public String getMenuName() {
        return "Item Browser";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player plr = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getInventory().getName().equals("Item Browser")) {
            if (Helper.hasCustomName(item)) {
                if (Helper.getCustomName(item).equals(Utils.chat("&aAccessories"))) {
                    new AccessoryBrowser(SkyblockRemastered.getMenuUtility(plr)).open();
                } else if (Helper.getCustomName(item).equals(Utils.chat("&aWeapons"))) {
                    new WeaponsBrowser(SkyblockRemastered.getMenuUtility(plr)).open();
                } else if (Helper.getCustomName(item).equals(Utils.chat("&aArmor"))) {
                    new ArmorBrowser(SkyblockRemastered.getMenuUtility(plr)).open();
                } else if (Helper.getCustomName(item).equals(Utils.chat("&aFragments"))) {
                    new FragmentsBrowser(SkyblockRemastered.getMenuUtility(plr)).open();
                } else if (Helper.getCustomName(item).equals(Utils.chat("&aMisc"))) {
                    new MiscBrowser(SkyblockRemastered.getMenuUtility(plr)).open();
                } else if (Helper.getCustomName(item).equals(Utils.chat("&aBows"))) {
                    new BowBrowser(SkyblockRemastered.getMenuUtility(plr)).open();
                }
            }
        }
    }

    @Override
    public void setMenuItems() {

        addBorder();

        inv.addItem(GUIHelper.addLore(new ItemStack(Material.DIAMOND_SWORD), "&aWeapons"));
        inv.addItem(GUIHelper.addLore(new ItemStack(Material.BOW), "&aBows"));
        inv.addItem(GUIHelper.addLore(new ItemStack(Material.LEATHER_CHESTPLATE), "&aArmor"));
        inv.addItem(GUIHelper.addLore(GUIHelper.addSkull(Utils.urlToBase64("961a918c0c49ba8d053e522cb91abc74689367b4d8aa06bfc1ba9154730985ff")), "&aAccessories"));
        inv.addItem(GUIHelper.addLore(GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmY4OWIxNTBiZTljNGM1MjQ5ZjM1NWY2OGVhMGM0MzkxMzAwYTliZTFmMjYwZDc1MGZjMzVhMTgxN2FkNzk2ZSJ9fX0="), "&aFragments"));
        inv.addItem(GUIHelper.addLore(GUIHelper.addSkull(Utils.urlToBase64("")), "&aMisc"));


    }
}
