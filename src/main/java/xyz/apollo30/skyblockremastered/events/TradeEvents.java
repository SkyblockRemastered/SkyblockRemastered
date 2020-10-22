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
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
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
            GuiUtils.playerTradeMenu(plr, c);
        } else {
            plr.playSound(plr.getLocation(), Sound.VILLAGER_HAGGLE, 1000F, 1F);
            plr.sendMessage(Utils.chat("&aYou have sent a trade request to " + Helper.getRank(c)));
            c.playSound(plr.getLocation(), Sound.VILLAGER_HAGGLE, 1000F, 1F);
            c.sendMessage(Utils.chat(Helper.getRank(plr) + " &ahas sent you a trade request.\n&a&lSHIFT-CLICK &r&athem to accept"));
            cd.put(plr.getName(), c.getName());
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (cd.containsKey(plr.getName())) {
                    plr.sendMessage(Utils.chat("&cYour current trade request to " + Helper.getRank(c) + " &chas expired!"));
                    plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
                    c.sendMessage(Utils.chat("&cYour trade request from " + Helper.getRank(plr) + " &chas expired!"));
                    c.playSound(c.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
                    cd.remove(plr.getName());
                }
            }, 300);
        }


    }
}