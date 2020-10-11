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
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class MiscEvents implements Listener {

    private final SkyblockRemastered plugin;

    public MiscEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        e.setMotd(Utils.chat("&6SkyblockRemastered &7- &8[&71.8&8]\n&b&k|&r &9Beta Release Coming Soon &b&k|&r"));
        e.setMaxPlayers(100);
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

//    @EventHandler
//    public void onPlayerItemHeld(PlayerItemHeldEvent e) {
//
//        Player plr = e.getPlayer();
//        PlayerObject po = plugin.playerManager.playerObjects.get(plr);
//
//        for (ItemStack item : plr.getInventory().getContents()) {
//            if (item == null) return;
////            addStats(item);
//            addRarity(item);
//        }
//    }
//
//    private void addRarity(ItemStack item) {
//
//        List<Integer> itemIds = new ArrayList<>(Arrays.asList(256, 257, 258, 259, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 283, 284, 285, 286, 290, 291, 292, 293, 294, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317));
//        if (itemIds.contains(item.getTypeId())) {
//            if (!item.hasItemMeta()) {
//                List<Integer> common = new ArrayList<>(Arrays.asList())
//            }
//            switch ()
//        }
//
//
//        if (!item.hasItemMeta()) {
//            Utils.addLore(item, "", "&7Damage: &c+20", "&7Strength: &c+30", "", "&b&lCELESTIAL");
//            ItemMeta meta = item.getItemMeta();
//            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_POTION_EFFECTS);
//        }
//
//
//    }

//
//    private void addStats(ItemStack item) {
//        Utils.addLore(item, "", "");
//    }

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
                    GuiUtils.skyblockMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers(), plugin);
                    e.setCancelled(true);
                }
            }
        } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK || action.toString().equals("SHIFT_RIGHT")) {
            personalVault(plr, e.getClickedBlock());

            if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
                if (e.getItem() != null && e.getItem().hasItemMeta() && item.getItemMeta().getDisplayName() != null) {

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
                            GuiUtils.skyblockMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers(), plugin);
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
