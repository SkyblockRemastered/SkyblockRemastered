package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.guis.SkyblockMenu;
import xyz.apollo30.skyblockremastered.guis.constructors.Menu;
import xyz.apollo30.skyblockremastered.constants.Constants;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.util.ArrayList;
import java.util.List;

public class InventoryEvents implements Listener {

    private final SkyblockRemastered plugin;

    public InventoryEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent e) {

        if (e.getInventory().getHolder() instanceof Menu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            Menu menu = (Menu) e.getInventory().getHolder();
            menu.handleMenu(e);
        }

        if (e.getCurrentItem() == null) return;

        /*
         * Checks if the player shifts click a skull
         * Then checks if the item name has the word "Helmet"
         * Then it puts it on for them.
         */
        if (e.getInventory().getName().equalsIgnoreCase("container.crafting") && e.getClick().isShiftClick() && e.getCurrentItem().hasItemMeta() && !e.getCurrentItem().getItemMeta().getDisplayName().isEmpty() && e.getCurrentItem().getItemMeta().getDisplayName().contains("Helmet")) {
            if (e.getWhoClicked().getInventory().getHelmet() == null) {
                e.getWhoClicked().getInventory().setHelmet(new ItemStack(e.getCurrentItem()));
                e.setCurrentItem(null);
            }
        }

        Player plr = (Player) e.getView().getPlayer();
        String title = e.getInventory().getTitle();
        String item = e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() ? e.getCurrentItem().getItemMeta().getDisplayName() : null;
        String clickType = e.getClick().toString();

        PlayerObject po = SkyblockRemastered.playerManager.getPlayerData(plr);

        if (title == null || item == null)
            return;

        // Trade Menu
        if ("Trades".equals(title)) {
            e.setCancelled(true);
            if (item.equals(Utils.chat("&fCoal &8x2"))) {
                ItemStack need = new ItemStack(Material.LOG, 1, (short) 0);
                ItemStack give = new ItemStack(Material.COAL);
                if (Helper.consumeItem(plr, 1, need)) {
                    Utils.successTrade(plr, give);
                } else Utils.failTrade(plr);
            } else if (item.equals(Utils.chat("&fGrass &8x4"))) {
                ItemStack need = new ItemStack(Material.DIRT);
                ItemStack give = new ItemStack(Material.GRASS, 4);
                if (Helper.consumeItem(plr, 4, need)) {
                    Utils.successTrade(plr, give);
                } else Utils.failTrade(plr);
            }
        }
        // trade menu
        if (title.startsWith("You   ")) {
            e.setCancelled(true);

        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        // Fetching the player's data
        Player plr = (Player) e.getPlayer();
        PlayerObject po = PlayerManager.playerObjects.get(plr);

        // If the player is in their island or not.
        if (!plr.getWorld().getName().equals("playerislands/" + plr.getUniqueId().toString())) {
            // Do a little checcing
            List<String> containers = new ArrayList<>();
            containers.add("container.dropper");
            containers.add("container.chest");
            containers.add("container.dispenser");

            if (!po.isBlockBreak() && containers.contains(e.getInventory().getName())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDrag(InventoryDragEvent e) {
        Player plr = (Player) e.getView().getPlayer();
        String title = e.getInventory().getTitle();
        if (e.getCursor() == null) return;
        String item = e.getCursor().getItemMeta().getDisplayName();

        if (title == null || item == null)
            return;

        if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
            new SkyblockMenu(SkyblockRemastered.getMenuUtility(plr), plr).open();
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&8Quiver Arrow"))) {
            new SkyblockMenu(SkyblockRemastered.getMenuUtility(plr), plr).open();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        Player plr = e.getPlayer();
        String item = e.getItemDrop().getItemStack().getItemMeta().getDisplayName();
        if (item == null)
            return;
        if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
            new SkyblockMenu(SkyblockRemastered.getMenuUtility(plr), plr).open();
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&8Quiver Arrow"))) {
            new SkyblockMenu(SkyblockRemastered.getMenuUtility(plr), plr).open();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent e) {
        Player plr = e.getPlayer();
        PlayerObject po = PlayerManager.playerObjects.get(plr);
        ItemStack item = e.getItem().getItemStack();

        if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().startsWith("Coin")) {
            int coin = Integer.parseInt(item.getItemMeta().getDisplayName().split(" ")[item.getItemMeta().getDisplayName().split(" ").length - 1]);
            po.setCoins_gained(po.getCoins_gained() + item.getAmount() * coin);
            e.setCancelled(true);
            e.getItem().remove();
            plr.getInventory().remove(item);
        } // i eat

        assert item != null;
        checkPickupRarity(e, item.getItemMeta().getLore(), po.getSelectedRarity());
    }

    private void checkPickupRarity(PlayerPickupItemEvent e, List<String> lores, Constants.Rarities selected_rarity) {

        if (lores == null || lores.size() <= 0) return;

        String item_rarity;

        item_rarity = Helper.getRarity(lores);

        switch (selected_rarity) {

            // item_rarity is the item rarity
            // selected_rarity is the players rarity selection

            case CELESTIAL:
                if (item_rarity.equals("common") ||
                        item_rarity.equals("uncommon") ||
                        item_rarity.equals("rare") ||
                        item_rarity.equals("epic") ||
                        item_rarity.equals("legendary")) e.setCancelled(true);
                break;
            case LEGENDARY:
                if (item_rarity.equals("common") ||
                        item_rarity.equals("uncommon") ||
                        item_rarity.equals("rare") ||
                        item_rarity.equals("epic")) e.setCancelled(true);
                break;
            case EPIC:
                if (item_rarity.equals("common") ||
                        item_rarity.equals("uncommon") ||
                        item_rarity.equals("rare")) e.setCancelled(true);

                break;
            case RARE:
                if (item_rarity.equals("common") || item_rarity.equals("uncommon"))
                    e.setCancelled(true);
                break;
            case UNCOMMON:
                if (item_rarity.equals("common")) e.setCancelled(true);
                break;
            case COMMON:
                e.setCancelled(false);
                break;
            case NONE:
                e.setCancelled(true);
                break;
        }

    }

}
