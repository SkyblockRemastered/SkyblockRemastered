package xyz.apollo30.skyblockremastered.guis.constructors;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.apollo30.skyblockremastered.utils.Utils;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;
    protected int maxItemsPerPage = 28;
    protected int index = 0;

    public PaginatedMenu(MenuUtility menuUtility) {
        super(menuUtility);
    }

    public void addMenuBorder() {
        ItemStack LEFT_BUTTON = new ItemStack(Material.ARROW, 1);
        ItemMeta LEFT_BUTTON_META = LEFT_BUTTON.getItemMeta();
        LEFT_BUTTON_META.setDisplayName(Utils.chat("&aPrevious"));
        LEFT_BUTTON.setItemMeta(LEFT_BUTTON_META);
        inv.setItem(48, LEFT_BUTTON);

        ItemStack CLOSE_BUTTON = new ItemStack(Material.BARRIER, 1);
        ItemMeta CLOSE_BUTTON_META = LEFT_BUTTON.getItemMeta();
        CLOSE_BUTTON_META.setDisplayName(Utils.chat("&cClose"));
        CLOSE_BUTTON.setItemMeta(CLOSE_BUTTON_META);

        inv.setItem(49, CLOSE_BUTTON);

        ItemStack RIGHT_BUTTON = new ItemStack(Material.ARROW, 1);
        ItemMeta RIGHT_BUTTON_META = LEFT_BUTTON.getItemMeta();
        RIGHT_BUTTON_META.setDisplayName(Utils.chat("&aNext"));
        RIGHT_BUTTON.setItemMeta(RIGHT_BUTTON_META);
        inv.setItem(50, RIGHT_BUTTON);

        for (int i = 0; i < 10; i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, super.GLASS);
            }
        }

        inv.setItem(17, super.GLASS);
        inv.setItem(18, super.GLASS);
        inv.setItem(26, super.GLASS);
        inv.setItem(27, super.GLASS);
        inv.setItem(35, super.GLASS);
        inv.setItem(36, super.GLASS);

        for (int i = 44; i < 54; i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, super.GLASS);
            }
        }
    }
}
