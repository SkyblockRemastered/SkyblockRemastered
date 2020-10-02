package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class UnloadChunk implements Listener {

    private final SkyblockRemastered plugin;

    public UnloadChunk(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onUnloadChunk(ChunkUnloadEvent e) {
        for (Entity entity : e.getChunk().getEntities()) {
            if (entity.getPassenger() != null) entity.remove();
        }
    }

}
