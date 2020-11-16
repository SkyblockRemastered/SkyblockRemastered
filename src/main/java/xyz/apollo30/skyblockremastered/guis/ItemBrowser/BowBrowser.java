package xyz.apollo30.skyblockremastered.guis.ItemBrowser;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.guis.constructors.MenuUtility;
import xyz.apollo30.skyblockremastered.guis.constructors.PaginatedMenu;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.items.Bows;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.NMSUtil;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BowBrowser extends PaginatedMenu {

    private int itemCount = 0;

    public BowBrowser(MenuUtility menuUtility) {
        super(menuUtility);
    }

    @Override
    public String getMenuName() {
        return "Bow Browser";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        Player plr = (Player) e.getWhoClicked();
        if (e.getInventory().getName().contains("Browser")) {
            if (item.getType().equals(Material.BARRIER)) {
                e.getWhoClicked().closeInventory();
            } else if (item.getType().equals(Material.ARROW) && Helper.hasCustomName(item)) {
                if (Helper.hasCustomName(item)) {
                    if (Helper.getCustomName(item).equals(Utils.chat("&aPrevious"))) {
                        if (page == 0)
                            e.getWhoClicked().sendMessage(Utils.chat("&cYou are already on the first page."));
                        else {
                            page = page - 1;
                            super.open();
                        }
                    } else if (Helper.getCustomName(item).equals(Utils.chat("&aNext"))) {
                        if (!((index + 1) >= itemCount)) {
                            page = page + 1;
                            super.open();
                        } else e.getWhoClicked().sendMessage(Utils.chat("&cYou are on the last page."));
                    } else if (Helper.getCustomName(item).equals(Utils.chat("&aBack to Item Browser"))) {
                        new ItemBrowser(SkyblockRemastered.getMenuUtility(plr)).open();
                    }
                }
            } else {
                if (item.getType() == Material.STAINED_GLASS_PANE) return;
                if (Helper.hasCustomName(item) && Helper.getCustomName(item).equals(Utils.chat("&5Summoning Eye"))) {
                    for (int i = 0; i < 8; i++) {
                        plr.getInventory().addItem(NMSUtil.addString(e.getCurrentItem(), "UUID", UUID.randomUUID().toString()));
                    }
                } else
                    plr.getInventory().addItem(NMSUtil.addString(e.getCurrentItem(), "UUID", UUID.randomUUID().toString()));
                plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
            }
        }
    }

    @Override
    public void setMenuItems() {

        addMenuBorder();

        GUIHelper.addItem(inv, 262, 1, 46, "&aBack to Item Browser");

        ArrayList<ItemStack> items = new ArrayList<>();

        Bows bows = new Bows(JavaPlugin.getPlugin(SkyblockRemastered.class));

        Field[] fields = Bows.class.getFields();
        List<Field> fieldList = Arrays.stream(fields).filter(field -> Modifier.isPublic(field.getModifiers())).collect(Collectors.toList());
        try {
            for (Field field : fieldList) {
                ItemStack item = (ItemStack) field.get(bows);
                if (item != null) items.add(item);
            }
        } catch (Exception ignored) {

        }

        itemCount = items.size();

        if (!items.isEmpty()) {
            for (int i = 0; i < super.maxItemsPerPage; i++) {
                index = super.maxItemsPerPage * page + i;
                if (index >= items.size()) break;
                if (items.get(index) != null) {
                    ItemStack item = items.get(index);
                    if (item == null) break;
                    inv.addItem(item);
                }
            }
        }

    }
}
