package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class PlayerItemHeld implements Listener {

    private final SkyblockRemastered plugin;
    public PlayerItemHeld(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e) {

        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        ItemStack item = plr.getItemInHand();
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            for (String lore : item.getItemMeta().getLore()) {
                if (lore.startsWith(Utils.chat("&7Strength:"))) po.setStrength(po.getStrength() + parseLore(lore));
                if (lore.startsWith(Utils.chat("&7Crit Chance:"))) po.setCritChance(po.getCritChance() + parseLore(lore));
                if (lore.startsWith(Utils.chat("&7Crit Damage:"))) po.setCritDamage(po.getCritDamage() + parseLore(lore));
                if (lore.startsWith(Utils.chat("&7Bonus Attack Speed")) || lore.startsWith(Utils.chat("&7Attack Speed"))) po.setAtkSpeed(po.getAtkSpeed() + parseLore(lore));
                if (lore.startsWith(Utils.chat("&7Intelligence"))) po.setIntelligence(po.getIntelligence() + parseLore(lore));
                if (lore.startsWith(Utils.chat("&7Speed"))) po.setSpeed(po.getSpeed() + parseLore(lore));
                if (lore.startsWith(Utils.chat("&7Health"))) po.setHealth(po.getHealth() + parseLore(lore));
                if (lore.startsWith(Utils.chat("&7Defense"))) po.setDefense(po.getDefense() + parseLore(lore));
                if (lore.startsWith(Utils.chat("&7True Defense"))) po.setSpeed(po.getSpeed() + parseLore(lore));
                if (lore.startsWith(Utils.chat("&7Speed"))) po.setSpeed(po.getSpeed() + parseLore(lore));


            }
        }
    }

    private int parseLore(String lore) {
        try {
            return Integer.parseInt(lore.split(" ")[lore.split(" ").length - 1].replace(Utils.chat("&c+"), "").replace(Utils.chat("&a+"), "").replace("%", ""));
        } catch (Exception ignored) { }
        return 0;
    }

}
