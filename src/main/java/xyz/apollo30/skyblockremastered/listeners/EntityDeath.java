package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.managers.PacketManager;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EntityDeath implements Listener {

    private final SkyblockRemastered plugin;

    public EntityDeath(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        if (EntityType.PLAYER != e.getEntityType()) plugin.mobManager.mobObjects.remove(e.getEntity());
        if (EntityType.PLAYER == e.getEntityType()) return;

        Player plr = e.getEntity().getKiller();
        if (plr == null) return;
        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);
        // Special Zealot
        if (e.getEntityType() == EntityType.ENDERMAN && e.getEntity().getPassenger().getCustomName().contains(Utils.chat("&cZealot"))) {
            double random = player_data.getZealot_kills() > 420 ? 0.00476190476 : 1;
            if (Math.random() > random) {
                player_data.addZealotKill();
            } else {
                int x = new Random().nextInt(10 - -10) + -10;
                int z = new Random().nextInt(10 - -10) + -10;

                plr.playSound(plr.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
                plr.sendMessage(Utils.chat("&aA special &5Zealot &ahas spawned nearby. &7(" + player_data.getZealot_kills() + ")"));

                Location loc;

                List<Location> list = new ArrayList<>(plugin.mobManager.zealotSpawnPoints);
                list.stream().filter(a -> e.getEntity().getKiller() != null && Math.abs(e.getEntity().getKiller().getLocation().getZ() + a.getZ()) <= 10 && Math.abs(e.getEntity().getKiller().getLocation().getX() + a.getX()) <= 10).collect(Collectors.toList());

                if (list.size() == 0) loc = e.getEntity().getLocation();
                else {
                    loc = list.get((int) Math.floor(Math.random() * list.size()));
                }

                Utils.broadCast(loc.getX() + ", " + loc.getY() + ", " + loc.getZ());

                Enderman enderman = (Enderman) Bukkit.getWorld("hub").spawnEntity(loc, EntityType.ENDERMAN);
                enderman.setCarriedMaterial(new MaterialData(Material.ENDER_PORTAL_FRAME));
                plugin.mobManager.createMob(enderman, "Special Zealot");

                player_data.resetZealotKills();
            }
        } else if (e.getEntityType() == EntityType.ENDERMAN && e.getEntity().getPassenger().getCustomName().contains(Utils.chat("Special Zealot"))) {
            plr.sendMessage(Utils.chat("&6&lRARE DROP!&r &5Summoning Eye&7!"));
            PacketManager.sendTitle(plr, 1, 15, 1, Utils.chat("&cSpecial Zealot!"), "");
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.LEATHER, 1, (byte) 69));
            plr.playSound(plr.getLocation(), Sound.SUCCESSFUL_HIT, 1F, .5F);
        }

        if (plugin.health_indicator.containsKey(e.getEntity())) {
            Entity armor_stand = plugin.health_indicator.get(e.getEntity());
            if (armor_stand != null) {
                plugin.health_indicator.remove(e.getEntity());
                armor_stand.remove();
            }
        } else if (e.getEntity().getPassenger() == null) e.getEntity().getPassenger().remove();
    }

}
