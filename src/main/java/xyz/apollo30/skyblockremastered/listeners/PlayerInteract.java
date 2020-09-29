package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.abilities.Miscs;
import xyz.apollo30.skyblockremastered.abilities.Weapons;
import xyz.apollo30.skyblockremastered.managers.InventoryManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.ResponsesUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.*;

public class PlayerInteract implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerInteract(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player plr = e.getPlayer();

        if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL)
            e.setCancelled(true);

        if (e.getItem() == null || e.getAction() == null)
            return;

        String action = e.getAction().toString();
        String item = e.getItem().getItemMeta().getDisplayName();

        if (item == null || action == null)
            return;

        if (action.contains("LEFT_CLICK")) {
            if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)")))
                InventoryManager.skyblockMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers(), plugin);
        } else if (action.contains("RIGHT_CLICK")) {
            if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)")))
                InventoryManager.skyblockMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers(), plugin);
            else if (item.contains(Utils.chat("&9Aspect of The End")))
                plugin.weaponAbilities.aspect_of_the_end(plr);
            else if (item.equals(Utils.chat("&aMaddox Batphone")))
                plugin.miscAbilities.maddox_batphone(plr);

        }
    }
}
