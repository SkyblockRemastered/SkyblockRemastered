package xyz.apollo30.skyblockremastered.events;

import net.minecraft.server.v1_8_R3.EntityComplexPart;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.Navigation;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Dragon implements Listener {

    private final SkyblockRemastered plugin;

    public Dragon(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final HashMap<Location, UUID> placedEyes = new HashMap<>();
    private final HashMap<Location, Long> cooldown = new HashMap<>();
    private final HashMap<Location, Block> stickRegeneration = new HashMap<>();
    private final HashMap<Location, Block> regeneration = new HashMap<>();

    public void placeSummoningEye(PlayerInteractEvent e) {
        e.setCancelled(true);
        Block blocc = e.getClickedBlock();
        if (Utils.isInZone(blocc.getLocation(), new Location(blocc.getWorld(), -674, 5, -279), new Location(blocc.getWorld(), -668, 15, -273))) {
            if (blocc.getType() == Material.ENDER_PORTAL_FRAME && blocc.getData() != 6) {
                if (plugin.so.isDragonFight()) {
                    e.getPlayer().sendMessage(Utils.chat("&cYou cannot place your Summoning Eye now.. A fight is in session!"));
                } else {
                    blocc.setData((byte) 6);
                    plugin.so.setPlacedSummoningEye(plugin.so.getPlacedSummoningEye() + 1);
                    for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                        plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1.5F);
                        if (plugin.so.getPlacedSummoningEye() >= 8) {
                            plugin.so.setDragonFight(true);
                            dragonSummoningSequence(e);
                            placedEyes.put(blocc.getLocation(), e.getPlayer().getUniqueId());
                            cooldown.put(blocc.getLocation(), new Date().getTime() + 15000);
                            plr.sendMessage(Utils.chat(Helper.getRank(e.getPlayer()) + " &dplaced a Summoning Eye! Brace Yourselves! &7(&e" + plugin.so.getPlacedSummoningEye() + "&7/&a8&7)"));
                        } else {
                            placedEyes.put(blocc.getLocation(), e.getPlayer().getUniqueId());
                            cooldown.put(blocc.getLocation(), new Date().getTime() + 15000);
                            plr.sendMessage(Utils.chat(Helper.getRank(e.getPlayer()) + " &dplaced a Summoning Eye! &7(&e" + plugin.so.getPlacedSummoningEye() + "&7/&a8&7)"));
                        }
                    }
                    e.getPlayer().getInventory().remove(e.getItem());
                    e.getPlayer().getInventory().addItem(Utils.addLore(
                            Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBkMWZiMTdhMmQ1YzZlYzJjMmUyYzNjYTFmOTUxMTA5YmM2OTA2MzMyOTdjNmY5ZDkzODNhNDQ5ZjYwMzg5NiJ9fX0"),
                            "&5Sleeping Eye",
                            "&7Keep this item is in your",
                            "&7inventory to recover your placed",
                            "&7Summoning Eye when you leave or",
                            "&7when you click the Ender Altar.",
                            "&7This item becomes imbued with",
                            "&7the magic of the Dragon when it",
                            "&7spawns, turning it into a",
                            "&7Remnant of the Eye.",
                            "",
                            "&5&lEPIC"));
                }
            }
        }
    }

    public void retrieveSummoningEye(PlayerInteractEvent e) {
        e.setCancelled(true);
        Block blocc = e.getClickedBlock(); // Apply for beta tester, check my discord status.
        if (Utils.isInZone(blocc.getLocation(), new Location(blocc.getWorld(), -674, 5, -279), new Location(blocc.getWorld(), -668, 15, -273))) {
            if (blocc.getType() == Material.ENDER_PORTAL_FRAME && blocc.getData() == 6) {
                if (plugin.so.isDragonFight()) {
                    e.getPlayer().sendMessage(Utils.chat("&cYou cannot retrieve your Summoning Eye now.. A fight is in session!"));
                } else {
                    // Cooldown Checking
                    if (placedEyes.get(blocc.getLocation()) != e.getPlayer().getUniqueId()) {
                        e.getPlayer().sendMessage(Utils.chat("&cYou cannot pick up an eye that isn't yours."));
                        return;
                    } else {
                        if (new Date().getTime() < cooldown.get(blocc.getLocation())) {
                            long kek = (cooldown.get(blocc.getLocation()) - new Date().getTime()) / 1000;
                            e.getPlayer().sendMessage(Utils.chat("&cYou must wait &e" + Utils.coinFormat((double) kek) + "s&c to do that!"));
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1F, .5F);
                            return;
                        }
                    }

                    blocc.setData((byte) 0);
                    plugin.so.setPlacedSummoningEye(plugin.so.getPlacedSummoningEye() - 1);

                    for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                        plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, .5F);
                        plr.sendMessage(Utils.chat(Helper.getRank(e.getPlayer()) + " &drecovered a Summoning Eye.. &7(&e" + plugin.so.getPlacedSummoningEye() + "&7/&a8&7)"));
                        cooldown.remove(blocc.getLocation());
                        placedEyes.remove(blocc.getLocation());
                    }
                    e.getPlayer().getInventory().remove(e.getItem());
                    e.getPlayer().getInventory().addItem(Utils.addLore(
                            Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ=="),
                            "&5Summoning Eye",
                            "&7Use this at the &5Ender Altar",
                            "&7in the &5Dragon's Nest&7 to",
                            "&7summon Ender Dragons!",
                            "",
                            "&5&lEPIC"));
                }
            }
        }
    }

    private void dragonSummoningSequence(PlayerInteractEvent e) {
        for (Player plr : e.getPlayer().getWorld().getPlayers()) {
            plr.playSound(plr.getLocation(), Sound.ENDERDRAGON_DEATH, 1000F, 1F);
        }

        resetAltar(e);

        for (int i = 8; i < 46; i++) {
            int finalI = i;
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Block blocc1 = e.getPlayer().getWorld().getBlockAt(-671, finalI, -276);
                Block blocc = e.getPlayer().getWorld().getBlockAt(-671, finalI + 1, -276);
                blocc.setType(Material.SEA_LANTERN);
                blocc1.setType(Material.STAINED_GLASS);
                blocc1.setData((byte) 10);
                //stickRegeneration.put(blocc.getLocation(), blocc1);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    blocc1.setType(Material.AIR);
                }, 4);
            }, i * 3);
        }
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            for (int i = -677; i < -664; i++) {
                for (int j = 46; j < 78; j++) {
                    for (int k = -282; k < -268; k++) {
                        if (Math.random() > .6) {
                            Block explodedBlock = e.getPlayer().getWorld().getBlockAt(i, j, k);
                            bounceBlock(explodedBlock);
                        } else {
                            Block explodedBlock = e.getPlayer().getWorld().getBlockAt(i, j, k);
                            explodedBlock.setType(Material.AIR);
                        }

                    }
                }
            }

            for (int i = 0; i < 15; i++) {
                e.getPlayer().getWorld().createExplosion(new Location(e.getPlayer().getWorld(), -670, 54 + i, -276), 3F);
            }

            String type = chooseDragon();
            String dragonName = "&c&l" + WordUtils.capitalizeFully(type) + " Dragon";
            for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                plr.playSound(plr.getLocation(), Sound.ENDERDRAGON_GROWL, 1000F, 1F);
                plr.sendMessage(Utils.chat("&d&lA &c&l" + dragonName.toUpperCase() + " &d&lhas spawned!"));
            }
            EnderDragon enderDragon = (EnderDragon) e.getPlayer().getWorld().spawnEntity(new Location(e.getPlayer().getWorld(), -671, 63, -275), EntityType.ENDER_DRAGON);
            enderDragon.setCustomName(Utils.chat("&c" + dragonName));
            plugin.mobManager.createMob(enderDragon, dragonName);
        }, 150);
    }

    private void resetAltar(PlayerInteractEvent e) {
        for (int i = -674; i < -279; i++) {
            for (int j = 8; j < 15; j++) {
                for (int k = -668; k < -273; k++) {
                    Block blocc = e.getPlayer().getWorld().getBlockAt(i, j, k);
                    if (blocc.getData() == 6) blocc.setData((byte) 0);
                }
            }
        }
    }

    enum CommonDragons {
        STRONG,
        YOUNG,
        UNSTABLE,
        WISE,
        PROTECTOR,
        OLD
    }

    private String chooseDragon() {
        if (Math.random() < .95)
            return CommonDragons.values()[(int) (Math.random() * CommonDragons.values().length)].toString();
        else if (Math.random() > .99) return "SUPERIOR";
        else return "HOLY";

    }

//    private void randomizeDragonPath(EnderDragon eD) {
//        EntityCreature ec = ((CraftCreature) eD).getHandle();
//        Navigation nav = ec.
//
//        Location loc = eD.getLocation();
//        double X = loc.getX();
//        double Y = loc.getY();
//        double Z = loc.getZ();
//
//        nav.a(X, Y, Z);
//    }

    private void bounceBlock(Block b) {
        FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
        b.setType(Material.AIR);
        float x = (float) -0.3 + (float) (Math.random() * ((0.3 - -0.3) + 1));
        float y = (float) 0.5;
        float z = (float) -0.3 + (float) (Math.random() * ((0.3 - -0.3) + 1));
        fb.setVelocity(new Vector(x, y, z));
    }

    @EventHandler
    public void onBlockChange(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof FallingBlock) {
            e.setCancelled(true);
        }
    }

//    @EventHandler
//    public void onItemSpawn(ItemSpawnEvent e) {
//
//    }

    @EventHandler
    public void onEntityCreatePortal(EntityCreatePortalEvent e) {
        e.setCancelled(true);
    }
}
