package xyz.apollo30.skyblockremastered.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.HashMap;

public class TradeEvents implements Listener {
    private final HashMap<String, String> cd = new HashMap<>();
    private final SkyblockRemastered plugin;

    public TradeEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteractToPlayer(PlayerInteractEntityEvent e) {
        Player plr = e.getPlayer();
        // if sneaking, return
        if (!plr.isSneaking()) {
            return;
        }

        Player c = (Player) e.getRightClicked();
        // if player is on cooldown, return
        if (cd.containsKey(plr.getName())) {
            plr.sendMessage(Utils.chat("&cYou already have an outgoing trade request!"));
            c.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
            return;
        }

        // otherwise, add to cooldown HashMap
        // send msgs
        if (cd.containsKey(c.getName()) && cd.containsValue(plr.getName())) {
            cd.remove(c.getName());
            Inventory menu1 = Bukkit.createInventory(null, 45, "You                  " + plr.getName());
            Utils.createGlass(menu1, "STAINED_GLASS_PANE", 15, 1, 5, 14, 23, 32, 41);
            Utils.createItemByte(menu1, 371, 0, 1, 37, "&6Coin Transaction", "", "&eClick to add coins to the trade menu", "&eYou can use prefixes like: K, M, B");
            Utils.createItemByte(menu1, 351, 8, 1, 40, "&cInvalid Trade", "", "&7Place something in the trade menu in order", "&7to accept the trade");
            Utils.createItemByte(menu1, 351, 8, 1, 42, "&7Other Person's Response", "", "&7You are currently", "&7trading with " + Helper.getRank(plr));

            Inventory menu2 = Bukkit.createInventory(null, 45, "You                  " + c.getName());
            Utils.createGlass(menu2, "STAINED_GLASS_PANE", 15, 1, 5, 14, 23, 32, 41);
            Utils.createItemByte(menu2, 371, 0, 1, 37, "&6Coin Transaction", "", "&eClick to add coins to the trade menu", "&eYou can use prefixes like: K, M, B");
            Utils.createItemByte(menu1, 351, 8, 1, 40, "&cInvalid Trade", "", "&7Place something in the trade menu in order", "&7to accept the trade");
            Utils.createItemByte(menu2, 351, 8, 1, 42, "&7Other Person's Response", "", "&7You are currently", "&7trading with " + Helper.getRank(c));

            c.openInventory(menu1);
            plr.openInventory(menu2);
        } else {
            plr.playSound(plr.getLocation(), Sound.VILLAGER_HAGGLE, 1000F, 1F);
            plr.sendMessage(Utils.chat("&aYou have sent a trade request to " + Helper.getRank(c)));
            c.playSound(plr.getLocation(), Sound.VILLAGER_HAGGLE, 1000F, 1F);
            c.sendMessage(Utils.chat(Helper.getRank(plr) + " &ahas sent you a trade request.\n&a&lSHIFT-CLICK &r&athem to accept"));
            cd.put(plr.getName(), c.getName());
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (cd.containsKey(plr.getName())) {
                    plr.sendMessage(Utils.chat("&cYour current trade request to " + cd.get(c.getName()) + " &chas expired!"));
                    plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
                    c.sendMessage(Utils.chat("&cYour trade request from " + cd.get(plr.getName()) + " &chas expired!"));
                    c.playSound(c.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
                    cd.remove(plr.getName());
                }
            }, 300);
        }
    }

}