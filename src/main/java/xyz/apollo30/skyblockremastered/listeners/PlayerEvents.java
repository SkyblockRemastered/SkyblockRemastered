package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.items.Miscs;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class PlayerEvents implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        e.setQuitMessage("");
        Player plr = e.getPlayer();
        PlayerManager.savePlayerData(plr);
        PlayerManager.playerObjects.remove(plr);
        SkyblockRemastered.dragonEvent.playerDamage.remove(plr);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {

        Player plr = e.getPlayer();
        e.setJoinMessage("");

        if (!plr.isOp()) {
            plr.setGameMode(GameMode.SURVIVAL);
        }

        ItemStack nether_star = Miscs.SKYBLOCK_MENU;
        plr.getInventory().remove(nether_star);

        GUIHelper.addItem(plr.getInventory(), nether_star, 9);

        // Create section for them
        PlayerManager.createPlayerData(plr);

        // If a player joins during a dragon fight
        SkyblockRemastered.dragonEvent.playerDamage.computeIfAbsent(plr, k -> (double) 0);

        // Load the Private Island
        World clone = Bukkit.getServer().createWorld(new WorldCreator("private_island_template"));

        // Now we copi pasta
        // File file = new File(Bukkit.getServer().getWorldContainer(), "playerislands/" + plr.getUniqueId().toString());
        // if (!file.exists())
        Utils.copyWorld(clone, "playerislands/" + plr.getUniqueId().toString());

        // Define Variables
        World island = Bukkit.getServer().getWorld("playerislands/" + plr.getUniqueId().toString());

        // Teleport them
        Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
        plr.teleport(loc);
    }

    @EventHandler
    public void playerShootArrow(ProjectileLaunchEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player)) return;
        Player plr = (Player) e.getEntity().getShooter();
        PlayerObject po = SkyblockRemastered.playerManager.getPlayerData(plr);
        if (po == null) return;

        int arrows = 0;

        if (po.getQuiverBag() == null) return;
        Inventory inv = Helper.stringToInventory(po.getQuiverBag());

        assert inv != null;
        for (ItemStack item : inv.getContents()) {
            if (item == null) break;
            if (item.getType() == Material.ARROW) arrows += item.getAmount();
            else if (item.getType() == Material.PRISMARINE_SHARD) arrows += item.getAmount();
            else if (item.getType() == Material.MAGMA_CREAM) arrows += item.getAmount();
        }

        if (arrows > 0) {
            ItemStack item = plr.getItemInHand();
            if (item.getType() != Material.BOW)
                return;
            String itemName = !item.hasItemMeta() || item.hasItemMeta() && item.getItemMeta().getDisplayName().isEmpty() ? "Bow" : item.getItemMeta().getDisplayName();

            if (itemName.contains("Magma Bow")) {
                for (ItemStack arrow : inv.getContents()) {
                    if (arrow != null) {
                        if (arrow.getType() == Material.MAGMA_CREAM) {
                            if (arrow.getAmount() - 1 == 0) inv.removeItem(arrow);
                            else arrow.setAmount(arrow.getAmount() - 1);
                            po.setQuiverBag(Helper.inventoryToString(inv));
                            return;
                        } else if (arrow.getType() == Material.ARROW) {
                            if (arrow.getAmount() - 1 == 0) inv.removeItem(arrow);
                            else arrow.setAmount(arrow.getAmount() - 1);
                            po.setQuiverBag(Helper.inventoryToString(inv));
                            return;
                        }
                    }
                }
            } else {
                for (ItemStack arrow : inv.getContents()) {
                    if (arrow != null) {
                        if (arrow.getType() == Material.ARROW) {
                            if (arrow.getAmount() - 1 == 0) inv.removeItem(arrow);
                            else arrow.setAmount(arrow.getAmount() - 1);
                            po.setQuiverBag(Helper.inventoryToString(inv));
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        Player plr = e.getPlayer();
        PlayerObject po = SkyblockRemastered.playerManager.getPlayerData(plr);
        if (po == null) return;

        // Island Border
        if (plr.getWorld().getName().startsWith("playerislands/")) {
            if (!Utils.isInZone(plr.getLocation(), new Location(plr.getWorld(), -80, 255, -80), new Location(plr.getWorld(), 80, 0, 80))) {
                plr.sendMessage(Utils.chat("&cYou've reached the world border.\n&aTo expand it, you can use your &2gems&a by heading to the Community Center!"));
                e.setCancelled(true);
                plr.teleport(plr.getWorld().getSpawnLocation());
            }
        }

        // Death Manager for the Void
        if (plr.getLocation().getY() <= -5) {
            Location loc = plr.getWorld().getSpawnLocation();
            plr.teleport(loc);
            Helper.deathHandler(plugin, plr, "void", null);
        }

        // Portal Mechanism.
        if (e.getTo().getBlock().getType().toString().toLowerCase().contains("portal")) {
            if (plr.getWorld().getName().equals("hub")) {
                if (Utils.isInZone(plr.getLocation(), new Location(plr.getWorld(), 670, 80, 206), new Location(plr.getWorld(), 680, 68, 214))) {
                    plr.sendMessage(Utils.chat("&7Sending you to your island."));
                    World island = Bukkit.getServer().createWorld(new WorldCreator("playerislands/" + plr.getUniqueId().toString()));
                    Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
                    plr.teleport(loc);
                    return;
                }
            } else if (plr.getWorld().getName().startsWith("playerislands/")) {
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

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN)
            event.setCancelled(true);
    }

}
