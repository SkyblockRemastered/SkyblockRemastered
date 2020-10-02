package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class PlayerPickup implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerPickup(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent e) {

        e.setCancelled(true);

        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);
        ItemStack item = e.getItem().getItemStack();

        // Coins
        if (item.hasItemMeta() && item.getItemMeta().getDisplayName() != null) {
            if (item.getItemMeta().getDisplayName().equals(Utils.chat("&f"))) {
                po.setCoins_gained(po.getCoins_gained() + item.getAmount() * 1000);
                e.setCancelled(true);
                e.getItem().remove();
            } else if (item.getItemMeta().getDisplayName().equals(Utils.chat("&f&f"))) {
                po.setCoins_gained(po.getCoins_gained() + item.getAmount() * 100);
                e.setCancelled(true);
                e.getItem().remove();
            } else if (item.getItemMeta().getDisplayName().equals(Utils.chat("&f&f&f"))) {
                po.setCoins_gained(po.getCoins_gained() + item.getAmount() * 50);
                e.setCancelled(true);
                e.getItem().remove();
            } else if (item.getItemMeta().getDisplayName().equals(Utils.chat("&f&f&f&f"))) {
                po.setCoins_gained(po.getCoins_gained() + item.getAmount());
                e.setCancelled(true);
                e.getItem().remove();
            }
        }

        switch (po.getPickup_rarity()) {
            case "All":
                e.setCancelled(false);
                break;
            case "Uncommon":
                if (item.getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                break;
            case "Rare":
                if (item.getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                break;
            case "Epic":
                if (item.getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("RARE")) e.setCancelled(true);
                break;
            case "Legendary":
                if (item.getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("RARE")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("EPIC")) e.setCancelled(true);
                break;
            case "Special":
                if (item.getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("RARE")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("EPIC")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("LEGENDARY")) e.setCancelled(true);
                break;
            case "Very Special":
                if (item.getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("RARE")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("EPIC")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("LEGENDARY")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("SPECIAL")) e.setCancelled(true);
                break;
            case "Celestial":
                if (item.getItemMeta().getLore().contains("COMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("UNCOMMON")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("RARE")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("EPIC")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("LEGENDARY")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("SPECIAL")) e.setCancelled(true);
                else if (item.getItemMeta().getLore().contains("VERY SPECIAL"))
                    e.setCancelled(true);
                break;
            case "None":
                e.setCancelled(true);
                break;
        }

    }

}
