package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class ProtectionEvents implements Listener {

    private final SkyblockRemastered plugin;

    public ProtectionEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        // Fetching the player's data
        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        // Check if the player is in the hub or not.
        if (plr.getWorld().getName().equals("hub")) {
            // Checking if their settings is allowed.
            if (!po.isBlockBreak()) {
                // Wheat Detection
                if (Utils.isInZone(plr.getLocation(), new Location(plr.getWorld(), 3, 255, -194), new Location(plr.getWorld(), 81, 0, -116))) {
                    if (e.getBlock().getType() != Material.CROPS) {
                        e.setCancelled(true);
                    }

                    // Oak Wood Detection
                } else if (Utils.isInZone(plr.getLocation(), new Location(plr.getWorld(), -236, 255, -82), new Location(plr.getWorld(), -90, 0, 34))) {
                    if (e.getBlock().getType() != Material.LOG && e.getBlock().getTypeId() != 0) {
                        e.setCancelled(true);
                    }

                } else e.setCancelled(true);
            }
            // If the player is in their island
        } else if (!plr.getWorld().getName().replace("playerislands/", "").equals(plr.getUniqueId().toString())) {
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);
        // Check if the player is in the hub or not.
        if (plr.getWorld().getName().equals("hub")) {
            // Checking if their settings is allowed.
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
            }
        } else if (!plr.getWorld().getName().replace("playerislands/", "").equals(plr.getUniqueId().toString())) {
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        // Fetching the player's data
        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);
        ItemStack item = plr.getItemInHand();

        // Island Border
        if (plr.getWorld().getName().startsWith("playerislands/")) {

            if (!Utils.isInZone(e.getBlock().getLocation(), new Location(plr.getWorld(), -80, 255, -80), new Location(plr.getWorld(), 80, 0, 80))) {
                plr.sendMessage(Utils.chat("&cYou've reached the world border.\n&aTo expand it, you can use your &2gems&a by heading to the Community Center!"));
                e.setCancelled(true);
                return;
            }
        }

        // unplaceable items
        if (item != null && item.getItemMeta().getEnchants().size() > 0) {
            e.setCancelled(true);
        } else {
            assert item != null;
            if (item.getType().equals(Material.SKULL) || item.getType().equals(Material.SKULL_ITEM)) {
                e.setCancelled(true);
            }
        }

        // Check if the player is in the hub or not.
        if (plr.getWorld().getName().equals("hub")) {
            // Checking if their settings is allowed.
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
            }
        } else if (!plr.getWorld().getName().replace("playerislands/", "").equals(plr.getUniqueId().toString())) {
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockFade(BlockFormEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void endermanPickup(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof Enderman) e.setCancelled(true);
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent e) {
        if (e.getEntity() instanceof Zombie) e.setCancelled(true);
        else if (e.getEntity() instanceof Skeleton) e.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getEntity().getWorld().getName().equals("hub")) {
            e.setCancelled(true);
        } else if (e.getEntity().getWorld().getName().startsWith("playerislands/")) {
            if (e.getEntityType() != EntityType.PRIMED_TNT) e.setCancelled(true);
        } else e.setCancelled(true);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player) {
            Player plr = (Player) e.getTarget();
            if (plr.getWorld().getName().startsWith("playerislands/") && !plr.getWorld().getName().equals("playerislands/" + plr.getUniqueId().toString())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent e) {
        if (e.getEntity() instanceof Enderman) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        // Cancels out all item damage.
        e.setDamage(0);
        e.setCancelled(true);
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
        // Fetching the player's data
        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        // Check if the player is in the hub or not.
        if (plr.getWorld().getName().equals("hub")) {
            // Checking if their settings is allowed.
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
                return;
            }
        }

        // If the player is in their island
        if (!plr.getWorld().getName().replace("playerislands/", "").equals(plr.getUniqueId().toString())) {
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent e) {
        // Fetching the player's data
        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        // Check if the player is in the hub or not.
        if (plr.getWorld().getName().equals("hub")) {
            // Checking if their settings is allowed.
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
                return;
            }
        }

        // If the player is in their island
        if (!plr.getWorld().getName().replace("playerislands/", "").equals(plr.getUniqueId().toString())) {
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PortalEvent(PlayerPortalEvent e) {
        e.setCancelled(true);
    }
}
