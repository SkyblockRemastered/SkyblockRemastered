package xyz.apollo30.skyblockremastered.GUIs;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.apollo30.skyblockremastered.GUIs.constructor.MenuUtility;
import xyz.apollo30.skyblockremastered.GUIs.constructor.PaginatedMenu;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.items.*;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBrowserMenu extends PaginatedMenu {

    private int itemCount = 0;

    public ItemBrowserMenu(MenuUtility menuUtility) {
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

        if (e.getInventory().getName().equals("Item Browser")) {
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                e.getWhoClicked().closeInventory();
            } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                if (e.getCurrentItem().hasItemMeta() && !e.getCurrentItem().getItemMeta().getDisplayName().isEmpty()) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat("&aPrevious"))) {
                        if (page == 0)
                            e.getWhoClicked().sendMessage(Utils.chat("&cYou are already on the first page."));
                        else {
                            page = page - 1;
                            super.open();
                        }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat("&aNext"))) {
                        if (!((index + 1) >= itemCount)) {
                            page = page + 1;
                            super.open();
                        } else e.getWhoClicked().sendMessage(Utils.chat("&cYou are on the last page."));
                    }
                }
            } else {
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) return;
                Player plr = (Player) e.getWhoClicked();
                plr.getInventory().addItem(e.getCurrentItem());
                plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                e.setCancelled(true);
            }
        }
    }

    @Override
    public void setMenuItems() {

        addMenuBorder();

        ArrayList<ItemStack> items = new ArrayList<>();

        Accessories accessories = new Accessories(JavaPlugin.getPlugin(SkyblockRemastered.class));
        Armor armor = new Armor(JavaPlugin.getPlugin(SkyblockRemastered.class));
        Bows bows = new Bows(JavaPlugin.getPlugin(SkyblockRemastered.class));
        Drops drops = new Drops(JavaPlugin.getPlugin(SkyblockRemastered.class));
        Fragments fragments = new Fragments(JavaPlugin.getPlugin(SkyblockRemastered.class));
        Miscs miscs = new Miscs(JavaPlugin.getPlugin(SkyblockRemastered.class));
        Pets pets = new Pets(JavaPlugin.getPlugin(SkyblockRemastered.class));
        Stones stones = new Stones(JavaPlugin.getPlugin(SkyblockRemastered.class));
        Tools tools = new Tools(JavaPlugin.getPlugin(SkyblockRemastered.class));
        Weapons weapons = new Weapons(JavaPlugin.getPlugin(SkyblockRemastered.class));

        Class[] classes = {Accessories.class, Armor.class, Bows.class, Drops.class, Fragments.class, Miscs.class, Pets.class, Stones.class, Tools.class, Weapons.class};

        try {
            for (Class uniqueClass : classes) {
                Field[] fields = uniqueClass.getFields();
                List<Field> fieldList = Arrays.stream(fields).filter(field -> Modifier.isPublic(field.getModifiers())).collect(Collectors.toList());
                for (Field field : fieldList) {
                    ItemStack item = (ItemStack) field.get(
                            uniqueClass.equals(Accessories.class) ? accessories :
                                    uniqueClass.equals(Armor.class) ? armor :
                                            uniqueClass.equals(Bows.class) ? bows :
                                                    uniqueClass.equals(Drops.class) ? drops :
                                                            uniqueClass.equals(Fragments.class) ? fragments :
                                                                    uniqueClass.equals(Miscs.class) ? miscs :
                                                                            uniqueClass.equals(Pets.class) ? pets :
                                                                                    uniqueClass.equals(Stones.class) ? stones :
                                                                                            uniqueClass.equals(Tools.class) ? tools : weapons);
                    if (item != null) items.add(item);
                }
            }
        } catch (Exception ignored) {
            return;
        }

        itemCount = items.size();

        if (!items.isEmpty()) {
            for (int i = 0; i < super.maxItemsPerPage; i++) {
                index = super.maxItemsPerPage * page + i;
                if (index >= items.size()) break;
                if (items.get(index) != null) {
                    ItemStack item = items.get(index);
                    inv.addItem(item);
                }
            }
        }
    }
}
