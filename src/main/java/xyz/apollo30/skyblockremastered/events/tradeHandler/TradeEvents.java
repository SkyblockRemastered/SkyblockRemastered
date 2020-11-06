package xyz.apollo30.skyblockremastered.events.tradeHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.GUIs.GUIHelper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TradeEvents implements Listener {

    private final HashMap<Player, Player> currentTrades = new HashMap<>();
    private final HashMap<String, String> cooldown = new HashMap<>();
    private final SkyblockRemastered plugin;

    public TradeEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

//    @EventHandler
//    public void onPlayerLeave(PlayerQuitEvent e) {
//        Player plr = e.getPlayer();
//        Player plr2 = currentTrades.get(plr);
//        if (plr2 == null) return;
//
//        plr.closeInventory();
//        plr2.closeInventory();
//
//        plr.sendMessage(Utils.chat("&cYou canceled the trade!"));
//        plr2.sendMessage(Utils.chat("&c" + plr.getName() + " canceled the trade!"));
//        currentTrades.remove(plr, plr2);
//        currentTrades.remove(plr2, plr);
//    }
//
//    @EventHandler
//    public void onTradeMenuClosed(InventoryCloseEvent e) {
//        Player plr = (Player) e.getPlayer();
//        Player plr2 = currentTrades.get(plr);
//        if (plr2 == null) return;
//
//        plr.getOpenInventory().close();
//        plr2.closeInventory();
//
//        plr.sendMessage(Utils.chat("&cYou canceled the trade!"));
//        plr2.sendMessage(Utils.chat("&c" + plr.getName() + " canceled the trade!"));
//        currentTrades.remove(plr, plr2);
//        currentTrades.remove(plr2, plr);
//    }
//
//    @EventHandler
//    public void onItemAddInTradeMenu(InventoryClickEvent e) {
//        Player plr = (Player) e.getWhoClicked();
//        Player plr2 = currentTrades.get(plr);
//        if (plr2 == null) return;
//
//        ItemStack clickedItem = e.getCurrentItem();
//
//        Inventory inv1 = (Inventory) plr.getOpenInventory();
//        Inventory inv2 = (Inventory) plr2.getOpenInventory();
//
//        Integer[] inv1Numbers = {1, 2, 3, 4, 10, 11, 12, 13, 19, 20, 21, 22, 28, 29, 30, 31, 37, 38, 39, 40};
//        Integer[] inv2Numbers = {6, 7, 8, 9, 15, 16, 17, 18, 19, 24, 25, 26, 27, 33, 34, 35, 36, 44, 43, 44, 45};
//
//        for (Integer i : inv1Numbers) {
//            if (inv1.getItem(i) == null) {
//                inv1.setItem(i, clickedItem);
//                plr.playSound(plr.getLocation(), Sound.VILLAGER_YES, 1000F, 1F);
//                break;
//            } else if (i == 40) {
//                plr.sendMessage(Utils.chat("&cThere is not enough space in the trade menu."));
//                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
//                break;
//            }
//        }
//
//        for (Integer j : inv2Numbers) {
//            if (inv2.getItem(j) == null) {
//                inv2.setItem(j, clickedItem);
//                plr.playSound(plr.getLocation(), Sound.VILLAGER_YES, 1000F, 1F);
//                break;
//            } else if (j == 45) {
//                plr.sendMessage(Utils.chat("&cThere is not enough space in the trade menu."));
//                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
//                break;
//            }
//        }
//    }

    @EventHandler
    public void onPlayerInteractToPlayer(PlayerInteractEntityEvent e) {
        Player plr = e.getPlayer();
        // if sneaking, return
        if (!plr.isSneaking()) {
            return;
        }

        Player c = (Player) e.getRightClicked();
        // if player is on cooldown, return
        if (cooldown.containsKey(plr.getName())) {
            plr.sendMessage(Utils.chat("&cYou already have an outgoing trade request!"));
            c.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
            return;
        }

        // otherwise, add to cooldown HashMap
        // send msgs
        if (cooldown.containsKey(c.getName()) && cooldown.containsValue(plr.getName())) {
            Inventory menu1 = Bukkit.createInventory(null, 45, "You                  " + plr.getName());
            GUIHelper.addGlass(menu1, "STAINED_GLASS_PANE", 15, 1, 5, 14, 23, 32, 41);
            GUIHelper.addItem(menu1, 371, 0, 1, 1, "&6100M Coins");
            GUIHelper.addItem(menu1, 371, 0, 1, 37, "&6Coin Transaction", "", "&eClick to add coins to the trade menu", "&eYou can use prefixes like: K, M, B");
            GUIHelper.addItem(menu1, 351, 8, 1, 40, "&cInvalid Trade", "", "&7Place something in the trade menu in order", "&7to accept the trade");
            GUIHelper.addItem(menu1, 351, 8, 1, 42, "&7Other Person's Response", "", "&7You are currently", "&7trading with " + Helper.getRank(plr, true));

            Inventory menu2 = Bukkit.createInventory(null, 45, "You                  " + c.getName());
            GUIHelper.addGlass(menu2, "STAINED_GLASS_PANE", 15, 1, 5, 14, 23, 32, 41);
            GUIHelper.addItem(menu1, 371, 0, 1, 6, "&6100M Coins");
            GUIHelper.addItem(menu2, 371, 0, 1, 37, "&6Coin Transaction", "", "&eClick to add coins to the trade menu", "&eYou can use prefixes like: K, M, B");
            GUIHelper.addItem(menu1, 351, 8, 1, 40, "&cInvalid Trade", "", "&7Place something in the trade menu in order", "&7to accept the trade");
            GUIHelper.addItem(menu2, 351, 8, 1, 42, "&7Other Person's Response", "", "&7You are currently", "&7trading with " + Helper.getRank(c, true));

            c.openInventory(menu1);
            plr.openInventory(menu2);
            currentTrades.put(plr, c);
            currentTrades.put(c, plr);
            cooldown.remove(c.getName());
        } else {
            plr.playSound(plr.getLocation(), Sound.VILLAGER_HAGGLE, 1000F, 1F);
            plr.sendMessage(Utils.chat("&aYou have sent a trade request to " + Helper.getRank(c, false)));
            c.playSound(plr.getLocation(), Sound.VILLAGER_HAGGLE, 1000F, 1F);
            c.sendMessage(Utils.chat(Helper.getRank(plr, false) + " &ahas sent you a trade request.\n&a&lSHIFT-CLICK &r&athem to accept"));
            cooldown.put(plr.getName(), c.getName());
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (cooldown.containsKey(plr.getName())) {
                    plr.sendMessage(Utils.chat("&cYour current trade request to " + Helper.getRank(c, false) + " &chas expired!"));
                    plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
                    c.sendMessage(Utils.chat("&cYour trade request from " + Helper.getRank(plr, false) + " &chas expired!"));
                    c.playSound(c.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
                    cooldown.remove(plr.getName());
                }
            }, 300);
        }
    }

}
