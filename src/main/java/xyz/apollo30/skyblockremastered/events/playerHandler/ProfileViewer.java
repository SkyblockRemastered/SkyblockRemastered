package xyz.apollo30.skyblockremastered.events.playerHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class ProfileViewer implements Listener {

    private final SkyblockRemastered plugin;

    public ProfileViewer(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteractToPlayer(PlayerInteractEntityEvent e) {

        if (!(e.getRightClicked() instanceof Player)) return;

        Player plr = e.getPlayer();
        Player clicked = (Player) e.getRightClicked();

        if (!Bukkit.getOnlinePlayers().contains(clicked)) return;

        if (!plr.isSneaking()) {
            new xyz.apollo30.skyblockremastered.guis.ProfileViewer.ProfileViewer(SkyblockRemastered.getMenuUtility(plr), plr, clicked).open();
        }
    }
}
