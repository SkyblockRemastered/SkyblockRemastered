package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;

public class PlayerDeath implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerDeath(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        Player plr = e.getEntity();

        Location loc = e.getEntity().getWorld().getSpawnLocation();
        loc.setPitch(0);
        loc.setYaw(-180);
        e.getEntity().teleport(loc);
        e.getEntity().setHealth(e.getEntity().getMaxHealth());
        e.setKeepInventory(true);
        e.setKeepLevel(true);
        e.setDroppedExp(0);
        PlayerObject po = plugin.playerManager.getPlayerData(e.getEntity());
        e.setDeathMessage("");
        po.resetHealth();

        double purse = po.getPurse();
        po.setPurse(po.getPurse() / 2);

        plr.sendMessage(Utils.chat("&cYou died and lost " + String.format("%,.0f", purse / 2) + " coins!"));
        plr.playSound(plr.getLocation(), Sound.ANVIL_LAND, 1F, 10F);
        plugin.getServer().getScheduler()
                .scheduleSyncDelayedTask(plugin, () -> {
                    plr.setVelocity(new Vector());
                }, 1L);

        Bukkit.getScheduler().runTask(plugin, () -> e.getEntity().setFireTicks(0));
    }

}
