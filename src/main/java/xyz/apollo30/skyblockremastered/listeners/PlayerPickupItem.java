package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;

public class PlayerPickupItem implements Listener {

    private final SkyblockRemastered plugin;
    public PlayerPickupItem(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {

        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        switch (po.getPickup_rarity()) {
            case "All":
                e.setCancelled(false);
                break;
            case "Uncommon":
                if (e.getItem().getItemStack().getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                break;
            case "Rare":
                if (e.getItem().getItemStack().getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                break;
            case "Epic":
                if (e.getItem().getItemStack().getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("RARE")) e.setCancelled(true);
                break;
            case "Legendary":
                if (e.getItem().getItemStack().getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("RARE")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("EPIC")) e.setCancelled(true);
                break;
            case "Special":
                if (e.getItem().getItemStack().getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("RARE")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("EPIC")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("LEGENDARY")) e.setCancelled(true);
                break;
            case "Very Special":
                if (e.getItem().getItemStack().getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("RARE")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("EPIC")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("LEGENDARY")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("SPECIAL")) e.setCancelled(true);
                break;
            case "Celestial":
                if (e.getItem().getItemStack().getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("RARE")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("EPIC")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("LEGENDARY")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("SPECIAL")) e.setCancelled(true);
                else if (e.getItem().getItemStack().getItemMeta().getLore().contains("VERY SPECIAL"))
                    e.setCancelled(true);
                break;
            case "None":
                e.setCancelled(true);
                break;
        }

    }

}
