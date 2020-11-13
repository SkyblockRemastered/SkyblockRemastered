package xyz.apollo30.skyblockremastered.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.apollo30.skyblockremastered.GUIs.constructor.MenuUtility;
import xyz.apollo30.skyblockremastered.GUIs.constructor.PaginatedMenu;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CreditsMenu extends PaginatedMenu {

    private int itemCount = 0;

    public CreditsMenu(MenuUtility menuUtility) {
        super(menuUtility);
    }

    @Override
    public String getMenuName() {
        return "Credits";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getInventory().getName().equals("Credits")) {
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
            }
        }

    }

    @Override
    public void setMenuItems() {

        HashMap<String, String> credits = new HashMap<>();
        credits.put("01b3ef7b-d39b-48ab-974b-a557b31a0739", "&6Founder and Main Developer");
        credits.put("4faf8bf9-c59f-47b6-895a-af141434e787", "&6Co-Developer and Contributor");
        credits.put("38184ab9-506a-42e6-9ecf-65d5bc006f86", "&eCore Contributor");
        credits.put("1396ef8a-443f-4483-b28b-3a3cf76f494f", "&eCore Contributor");
        credits.put("eb094132-4eec-4793-a38c-a37576d07c63", "&eCore Contributor");
        credits.put("abd3e676-f320-44f9-9021-043a2b31c387", "&eCore Contributor");

        ArrayList<String> uuids = new ArrayList<>(credits.keySet());

        addMenuBorder();

        if (!uuids.isEmpty()) {
            for (int i = 0; i < super.maxItemsPerPage; i++) {
                index = super.maxItemsPerPage * page + i;
                if (index >= uuids.size()) break;
                if (uuids.get(index) != null) {
                    OfflinePlayer plr = Bukkit.getOfflinePlayer(UUID.fromString(uuids.get(index)));
                    if (plr != null) {
                        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

                        SkullMeta meta = (SkullMeta) skull.getItemMeta();
                        meta.setOwner(plr.getName());
                        meta.setDisplayName(Utils.chat("&a" + plr.getName()));
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(Utils.chat(credits.get(uuids.get(index))));

                        meta.setLore(lore);
                        skull.setItemMeta(meta);
                        inv.addItem(skull);
                    }
                }
            }
        }

        itemCount = uuids.size();
    }
}
