package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;

public class PlayerMove implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerMove(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.getPlayerData(plr);

        // Island Border
        if (plr.getWorld().getName().startsWith("islands/")) {
            if (!Utils.isInZone(plr.getLocation(), new Location(plr.getWorld(), -80, 255, -80), new Location(plr.getWorld(), 80, 0, 80))){
                plr.sendMessage(Utils.chat("&cYou've reached the world border.\n&aTo expand it, you can use your &2gems&a by heading to the Community Center!"));
                e.setCancelled(true);
                plr.teleport(plr.getWorld().getSpawnLocation());
            }
        }

        // Death Manager for the Void
        if (plr.getLocation().getY() <= -5) {
            Location loc = plr.getWorld().getSpawnLocation();
            plr.teleport(loc);
            plr.setHealth(plr.getMaxHealth());
            po.setHealth(po.getMaxHealth());
            po.resetHealth();

            plugin.getServer().getScheduler()
                    .scheduleSyncDelayedTask(plugin, () -> {
                        plr.setVelocity(new Vector());
                    }, 1L);

            if (!plr.getWorld().getName().startsWith("islands/")) {
                plr.playSound(plr.getLocation(), Sound.ANVIL_LAND, 1F, 10F);
                double purse = po.getPurse();
                po.setPurse(po.getPurse() / 2);
                plr.sendMessage(Utils.chat("&cYou died and lost " + String.format("%,.0f", purse / 2) + " coins!"));
            } else plr.sendMessage(Utils.chat("&cYou fell into the void"));
        }

        // Portal Mechanism.
        if (e.getTo().getBlock().getType().toString().toLowerCase().contains("portal")) {
            if (plr.getWorld().getName().equals("hub")) {
                if (Utils.isInZone(plr.getLocation(), new Location(plr.getWorld(), -0, 80, -67), new Location(plr.getWorld(), 6, 68, -60))) {
                    plr.sendMessage(Utils.chat("&7Sending you to your island."));
                    World island = Bukkit.getServer().createWorld(new WorldCreator("islands/" + plr.getUniqueId().toString()));
                    Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
                    plr.teleport(loc);
                    return;
                }
            } else if (plr.getWorld().getName().startsWith("islands/")) {
                plr.sendMessage(Utils.chat("&7Sending you to the hub."));
                World island = Bukkit.getServer().createWorld(new WorldCreator("hub"));
                Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
                plr.teleport(loc);
            }
        }

        // Health / Speed / Saturation Editor
        plr.setSaturation(20);
        plr.setWalkSpeed(po.getSpeed() > 500 ? 1.0f : po.getSpeed() <= 0 ? 0.0f : (float) po.getSpeed() / 500);
        plr.setFlySpeed(po.getSpeed() / 2 > 500 ? 1.0f : po.getSpeed() / 2 <= 0 ? 0.0f : (float) po.getSpeed() / 2 / 500);
    }

}
