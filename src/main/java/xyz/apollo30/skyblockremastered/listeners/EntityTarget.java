package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class EntityTarget implements Listener {

    public final SkyblockRemastered plugin;

    public EntityTarget(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {

        if (e.getTarget() instanceof Player) {
            Player plr = (Player) e.getTarget();
            if (plr.getWorld().getName().startsWith("islands/") && !plr.getWorld().getName().equals("islands/" + plr.getUniqueId().toString())) {
                e.setCancelled(true);
            }
        }

    }

}
