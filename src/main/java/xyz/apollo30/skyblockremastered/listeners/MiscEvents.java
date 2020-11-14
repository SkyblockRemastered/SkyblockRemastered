package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import xyz.apollo30.skyblockremastered.GUIs.SkyblockMenu;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class MiscEvents implements Listener {

    private final SkyblockRemastered plugin;

    public MiscEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        e.setMotd(Utils.chat("                         &eSkyblock Remastered &f[1.8]\n       &fDragon Simulator &e| play.apollo30.xyz | &aOnline"));
        if (e.getMaxPlayers() != 100) e.setMaxPlayers(100);
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onUnloadChunk(ChunkUnloadEvent e) {
        for (Entity entity : e.getChunk().getEntities()) {
            if (entity.getPassenger() != null) entity.remove();
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(e.toWeatherState());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player plr = e.getPlayer();

        Action action = e.getAction();
        ItemStack item = e.getItem();

        if (action == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL) e.setCancelled(true);
        else if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK || action.toString().equals("SHIFT_LEFT")) {
            personalVault(plr, e.getClickedBlock());

            if (e.getItem() != null && e.getItem().hasItemMeta() && item.getItemMeta().getDisplayName() != null) {
                if (item.getItemMeta().getDisplayName().equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
                    SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), plugin);
                    e.setCancelled(true);
                }
            }
        } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK || action.toString().equals("SHIFT_RIGHT")) {
            personalVault(plr, e.getClickedBlock());

            if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
                if (e.getItem() != null && e.getItem().hasItemMeta() && item.getItemMeta().getDisplayName() != null) {

                    if (item.getItemMeta().getDisplayName().contains("Helmet")) {
                        if (e.getPlayer().getInventory().getHelmet() == null) {
                            e.getPlayer().getInventory().setHelmet(new ItemStack(e.getItem()));
                            e.getPlayer().getInventory().setItemInHand(null);
                            e.setCancelled(true);
                            return;
                        }
                    }

                    if (item.getItemMeta().getDisplayName().equals(Utils.chat("&5Zealot Spawn Egg"))) {
                        Enderman enderman = (Enderman) e.getClickedBlock().getLocation().getWorld().spawnEntity(e.getClickedBlock().getLocation().add(0, 1, 0), EntityType.ENDERMAN);
                        plugin.mobManager.createMob(enderman, "Zealot");
                    } else if (item.getItemMeta().getDisplayName().equals(Utils.chat("&5Special Zealot Spawn Egg"))) {
                        Enderman enderman = (Enderman) e.getClickedBlock().getLocation().getWorld().spawnEntity(e.getClickedBlock().getLocation().add(0, 1, 0), EntityType.ENDERMAN);
                        enderman.setCarriedMaterial(new MaterialData(Material.ENDER_PORTAL_FRAME));
                        plugin.mobManager.createMob(enderman, "Special Zealot");
                    } else if (e.getItem().getItemMeta().getDisplayName().contains("Summoning Eye")) {
                        plugin.dragonEvent.placeSummoningEye(e);
                    } else if (e.getItem().getItemMeta().getDisplayName().contains("Remnant of the Eye")) {
                        plugin.dragonEvent.retrieveSummoningEye(e);
                    } else if (e.getItem() != null && e.getItem().hasItemMeta()) {
                        if (item.getItemMeta().getDisplayName().equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
                            SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), plugin);
                            e.setCancelled(true);
                        } else if (item.getItemMeta().getDisplayName().contains(Utils.chat("Aspect of The End"))) {
                            plugin.weaponAbilities.aspect_of_the_end(plr);
                            e.setCancelled(true);
                        } else if (item.getItemMeta().getDisplayName().equals(Utils.chat("Maddox Batphone"))) {
                            plugin.miscAbilities.maddox_batphone(plr);
                            e.setCancelled(true);
                        } else if (item.getItemMeta().getDisplayName().contains(Utils.chat("&dAspect of the Jerry"))) {
                            plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1000F, 1F);
                        }
                    }
                }
            }
        }
    }

    private void personalVault(Player plr, Block clickedBlock) {
        if (clickedBlock == null || clickedBlock.getType() == Material.AIR) return;
        Location loc = clickedBlock.getLocation();

        if (Utils.isInZone(loc, new Location(loc.getWorld(), 28, 75, -52), new Location(loc.getWorld(), 34, 67, -49))) {
            plr.openInventory(plr.getEnderChest());
            plr.playSound(plr.getLocation(), Sound.CHEST_OPEN, 1F, 0.3F);
        }
    }

}
