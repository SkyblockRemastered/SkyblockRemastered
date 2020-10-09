package xyz.apollo30.skyblockremastered.events;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.customMobs.CustomEntityEnderDragon;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.*;

enum CommonDragons {
    STRONG,
    YOUNG,
    UNSTABLE,
    WISE,
    PROTECTOR,
    OLD
}

public class Dragon implements Listener {

    private final SkyblockRemastered plugin;

    public Dragon(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final HashMap<Location, UUID> placedEyes = new HashMap<>();
    private final HashMap<Location, Long> cooldown = new HashMap<>();
    private final HashMap<Player, ItemStack> playerEyes = new HashMap<>();
    private final HashMap<Player, Integer> placedPlayerEyes = new HashMap<>();

    private final HashMap<Location, Byte> blockDatabase = new HashMap<>();

    private final HashMap<Location, Material> stickRegeneration = new HashMap<>();
    private final HashMap<Location, Material> dragonEggRegeneration = new HashMap<>();
    public final HashMap<Player, Double> playerDamage = new HashMap<>();
    private final List<Entity> endCrystals = new ArrayList<>();

    public void placeSummoningEye(PlayerInteractEvent e) {
        e.setCancelled(true);
        Block blocc = e.getClickedBlock();
        if (blocc != null && Utils.isInZone(blocc.getLocation(), new Location(blocc.getWorld(), -2, 5, -7), new Location(blocc.getWorld(), 4, 15, -1))) {
            if (blocc.getType() == Material.ENDER_PORTAL_FRAME && blocc.getData() != 6) {
                if (plugin.so.isDragonFight()) {
                    e.getPlayer().sendMessage(Utils.chat("&cThe Altar Table is temporarily disabled!"));
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1F, .5F);
                } else {
                    blocc.setData((byte) 6);
                    plugin.so.setPlacedSummoningEye(plugin.so.getPlacedSummoningEye() + 1);

                    ItemStack sleepingEye = Utils.addLore(
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
                            "&5&lEPIC");

                    e.getPlayer().getInventory().remove(e.getItem());
                    playerEyes.put(e.getPlayer(), sleepingEye);
                    e.getPlayer().getInventory().addItem(sleepingEye);


                    if (plugin.so.getPlacedSummoningEye() >= 8) {
                        dragonSummoningSequence(e);
                        placedPlayerEyes.put(e.getPlayer(), placedPlayerEyes.get(e.getPlayer()) == null ? 0 : placedPlayerEyes.get(e.getPlayer()) + 1);
                        plugin.so.setDragonFight(true);
                        placedEyes.put(blocc.getLocation(), e.getPlayer().getUniqueId());
                        cooldown.put(blocc.getLocation(), new Date().getTime() + 15000);
                        for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                            plr.sendMessage(Utils.chat(Helper.getRank(e.getPlayer()) + " &dplaced a Summoning Eye! Brace Yourselves! &7(&e" + plugin.so.getPlacedSummoningEye() + "&7/&a8&7)"));
                            plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1.5F);
                        }

                        for (Player player : playerEyes.keySet()) {
                            player.getInventory().remove(playerEyes.get(player));
                            player.getInventory().addItem(Utils.addLore(
                                    Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODlkMDc0YWQ5Yjk5NzE4NzllYjMyNWJkZGZmMzY3NWY3MjI0ODU2YmQ2ZDU2OWZjOGQ0ODNjMTMzZDczMDA1ZCJ9fX0"),
                                    "&5Remnant of the Eye",
                                    "&7Keep you alive when you are on",
                                    "&7death's door, granting a short",
                                    "&7period of invincibility.",
                                    "&7Consumed on use",
                                    " ",
                                    "&cNote: This item only works while you're",
                                    "&cin the end island.",
                                    "",
                                    "&5&lEPIC"));
                        }
                    } else {
                        placedEyes.put(blocc.getLocation(), e.getPlayer().getUniqueId());
                        cooldown.put(blocc.getLocation(), new Date().getTime() + 15000);
                        placedPlayerEyes.put(e.getPlayer(), placedPlayerEyes.get(e.getPlayer()) == null ? 0 : placedPlayerEyes.get(e.getPlayer()) + 1);
                        for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                            plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1.5F);
                            plr.sendMessage(Utils.chat(Helper.getRank(e.getPlayer()) + " &dplaced a Summoning Eye! &7(&e" + plugin.so.getPlacedSummoningEye() + "&7/&a8&7)"));
                        }
                    }
                }
            }
        }
    }

    public void retrieveSummoningEye(PlayerInteractEvent e) {
        e.setCancelled(true);
        Block blocc = e.getClickedBlock(); // Apply for beta tester, check my discord status.
        if (Utils.isInZone(blocc.getLocation(), new Location(blocc.getWorld(), -2, 5, -7), new Location(blocc.getWorld(), 4, 15, -1))) {
            if (blocc.getType() == Material.ENDER_PORTAL_FRAME && blocc.getData() == 6) {
                if (plugin.so.isDragonFight()) {
                    e.getPlayer().sendMessage(Utils.chat("&cThe Altar Table is temporarily disabled!"));
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1F, .5F);
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

                    placedPlayerEyes.put(e.getPlayer(), placedPlayerEyes.get(e.getPlayer()) == null ? 0 : placedPlayerEyes.get(e.getPlayer()) - 1);

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
        animateAltar(e);

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {

            for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                plr.playSound(plr.getLocation(), Sound.ENDERDRAGON_DEATH, 1000F, 1F);
            }

            for (int i = 8; i < 46; i++) {
                int finalI = i;
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    Block blocc1 = e.getPlayer().getWorld().getBlockAt(1, finalI, -4);
                    Block blocc = e.getPlayer().getWorld().getBlockAt(1, finalI + 1, -4);
                    blocc.setType(Material.SEA_LANTERN);
                    blocc1.setType(Material.STAINED_GLASS);
                    blocc1.setData((byte) 10);

                    stickRegeneration.put(blocc1.getLocation(), blocc1.getType());
                    blockDatabase.put(blocc1.getLocation(), blocc1.getData());

                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        blocc1.setType(Material.AIR);
                    }, 4);
                }, i * 3);
            }

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                for (int i = -5; i < 9; i++) {
                    for (int j = 46; j < 78; j++) {
                        for (int k = -11; k < 4; k++) {
                            if (Math.random() > .8) {
                                Block explodedBlock = e.getPlayer().getWorld().getBlockAt(i, j, k);

                                dragonEggRegeneration.put(explodedBlock.getLocation(), explodedBlock.getType());
                                blockDatabase.put(explodedBlock.getLocation(), explodedBlock.getData());

                                bounceBlock(explodedBlock);
                            } else {
                                Block explodedBlock = e.getPlayer().getWorld().getBlockAt(i, j, k);

                                dragonEggRegeneration.put(explodedBlock.getLocation(), explodedBlock.getType());
                                blockDatabase.put(explodedBlock.getLocation(), explodedBlock.getData());

                                explodedBlock.setType(Material.AIR);
                            }
                        }
                    }
                }

                for (int i = 0; i < 15; i++) {
                    e.getPlayer().getWorld().createExplosion(new Location(e.getPlayer().getWorld(), 1, 54 + i, -4), 3F);
                }

                String type = chooseDragon();
                String dragonName = "&c&l" + WordUtils.capitalizeFully(type) + " Dragon";
                for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                    plr.playSound(plr.getLocation(), Sound.ENDERDRAGON_GROWL, 1000F, 1F);
                    plr.sendMessage(Utils.chat("&d&lA &c&l" + dragonName + " &d&lhas spawned!"));
                    playerDamage.put(plr, (double) 0);
                }

                LivingEntity enderDragon = CustomEntityEnderDragon.spawn(new Location(e.getPlayer().getWorld(), 1, 63, -4), dragonName);
                enderDragon.setNoDamageTicks(0);
                enderDragon.setMaximumNoDamageTicks(0);
                enderDragon.setCustomName(Utils.chat(dragonName));
                plugin.mobManager.createMob(enderDragon, type.toLowerCase());
                plugin.so.setDragonName(type.toUpperCase());
                spawnCrystals(e);
            }, 150);
        }, 60);
    }

    private void spawnCrystals(PlayerInteractEvent e) {
        List<Location> crystalSpawns = new ArrayList<>();
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), -10.5, 44.5, 48.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), -39.5, 69.5, 35.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), -43.5, 44.5, -27.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), 36.5, 51.5, 38.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), 29.5, 36.5, 25.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), -25.5, 35.5, 22.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), 1.5, 58.5, 42));

        for (Location loc : crystalSpawns) {
            if (Math.random() > .5) {
                Entity endCrystal = loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
                endCrystals.add(endCrystal);
            }
        }
    }

    public void regenerateDragonStick(EntityDeathEvent e) {
        for (int i = 8; i < 46; i++) {
            int finalI = i;
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Block blocc1 = e.getEntity().getWorld().getBlockAt(1, finalI, -4);

                Material blocc = stickRegeneration.get(blocc1.getLocation());
                if (blocc == null) return;
                Byte byteID = blockDatabase.get(blocc1.getLocation());

                blocc1.setType(blocc);
                blocc1.setData(byteID);

                if (finalI >= 45) {
                    try {
                        regenerateDragonEgg(e);

                        plugin.so.setDragonFight(false);

                        for (Player plr : e.getEntity().getWorld().getPlayers()) {
                            plr.sendMessage(Utils.chat("&dA Dragon Egg has spawned!"));
                            plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1000, 1.25F);
                        }

                        for (Player plr : playerDamage.keySet()) {
                            playerDamage.remove(plr);
                            placedPlayerEyes.remove(plr);
                        }

                        for (Location loc : dragonEggRegeneration.keySet()) {
                            blockDatabase.remove(loc);
                            dragonEggRegeneration.remove(loc);
                        }
                    } catch (Exception ignore) {
                    }
                }

                blockDatabase.remove(blocc1.getLocation());
                stickRegeneration.remove(blocc1.getLocation());
            }, 20 * i);
        }
    }

    private void regenerateDragonEgg(EntityDeathEvent e) {
        for (Location loc : dragonEggRegeneration.keySet()) {
            Block blocc = e.getEntity().getWorld().getBlockAt(loc);

            blocc.setType(dragonEggRegeneration.get(loc));
            blocc.setData(blockDatabase.get(loc));
        }
    }

    private void animateAltar(PlayerInteractEvent e) {
        for (int i = -2; i < 5; i++) {
            for (int j = 8; j < 13; j++) {
                for (int k = -6; k < -1; k++) {
                    Block blocc = e.getPlayer().getWorld().getBlockAt(i, j, k);
                    if (blocc.getType() == Material.ENDER_PORTAL_FRAME && blocc.getData() == 6) {
                        blocc.setData((byte) 0);

                        ArmorStand armorStand = blocc.getWorld().spawn(blocc.getLocation().add(.5, -.125, .5), ArmorStand.class);
                        armorStand.setVisible(false);
                        armorStand.setGravity(false);
                        armorStand.setRemoveWhenFarAway(true);
                        armorStand.setHelmet(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ=="));
                        armorStand.setSmall(true);

                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, armorStand::remove, 80);
                        riseEyes(armorStand);
                    }
                }
            }
        }
    }

    private void riseEyes(ArmorStand armorStand) {
        for (int i = 0; i < 25; i++) {
            int finalI = i;

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                double X1 = armorStand.getLocation().getX();
                double Y2 = armorStand.getLocation().getY();
                double Z3 = armorStand.getLocation().getZ();

                armorStand.teleport(new Location(armorStand.getWorld(), X1, Y2 + (finalI / (double) 100), Z3));
            }, 2 * i);
        }
    }

    private void getLoot(Player plr) {

        double pD = plugin.dragonEvent.playerDamage.get(plr);
        int placedEyes = plugin.dragonEvent.placedPlayerEyes.get(plr);

        int weight = 0;

        // With the reforge update, they changed that Helmet and boots require same weight to drop, which is 1300 weight
        // For leggings, it requires 1600 weight to drop, and 1800 for chestplate.
        // An AOTD / Dragon pet / Dragon Claw / Draogn Horn requires 2000 weight to drop.
        // A dragon scale requires 1300 weight to drop on a Young Dragon.
        // A Travel scroll to dragon's nest requires 1100 weight to drop on an Unstable dragon.

        // Next is how the loot is actually rolled and determined.
        // The sword can be "given" at 450 weight, the chestplate is 400 weight,
        // the leggings are 350 weight, the helmet is 350 weight and the boots are 300 weight.
        // Which is why you have a chance to get the boots even if you place no eyes but come in 1st place.

        weight += 100 * placedEyes;

        // Add weight for the position they got.
        // Note: I have no idea on how to do this.

        switch (plugin.so.getDragonName()) {
            case "SUPERIOR":
                getItem(pD, weight, plr, plugin.so.getDragonName(), .8, .7, .7, .6, .6);
                break;
            case "UNSTABLE":
                getItem(pD, weight, plr, plugin.so.getDragonName(), .5, .75, .7, .65, .6);
                break;
            case "YOUNG":
            case "WISE":
            case "OLD":
            case "PROTECTOR":
                getItem(pD, weight, plr, plugin.so.getDragonName(), .9, .65, .6, .55, .50);
                break;
            case "STRONG":
                getItem(pD, weight, plr, plugin.so.getDragonName(), .7, .65, .6, .55, .50);
                break;
            case "HOLY":
                getItem(pD, weight, plr, plugin.so.getDragonName(), .8, .7, .7, .50, .50);
                break;
            case "CELESTIAL":
                getItem(pD, weight, plr, plugin.so.getDragonName(), .99, .7, .7, .6, .6);
                break;
        }

    }

    private void getItem(double playerDamage, int weight, Player plr, String dragon, double aotd, double chestplate, double leggings, double helmet, double boots) {
        Inventory inv = plr.getInventory();
        if (weight >= 450 && Math.random() > aotd) {
            inv.addItem(Utils.addLore(new ItemStack(Material.DIAMOND_SWORD), "&6Aspect of the Dragons", "&7Damage: &c+225", "&7Strength: &c+100", "", "&6Item Ability: Dragon Rage &a&lRIGHT CLICK", "&7All Monsters in front of you", "&7take &a&l1,050 &r&7damage. Hit", "&7monsters take large knockback.", "&8Mana Cost: &b100", "&8Cooldown: &a5s"));
            broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &6Aspect of the Dragons");
        } else if (weight >= 400 && Math.random() > chestplate) {
            broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &6" + WordUtils.capitalizeFully(dragon) + " Dragon Chestplate");
            switch (dragon) {
                case "SUPERIOR":

                case "UNSTABLE":

                case "YOUNG":

                case "OLD":

                case "PROTECTOR":

                case "STRONG":

                case "HOLY":

                case "WISE":

                case "CELESTIAL":

            }
        } else if (weight >= 350 && Math.random() > leggings) {
            broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &6" + WordUtils.capitalizeFully(dragon) + " Dragon Leggings");
            switch (dragon) {
                case "SUPERIOR":

                case "UNSTABLE":

                case "YOUNG":

                case "OLD":

                case "PROTECTOR":

                case "STRONG":

                case "HOLY":

                case "WISE":

                case "CELESTIAL":

            }
        } else if (weight >= 350 && Math.random() > helmet) {
            broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &6" + WordUtils.capitalizeFully(dragon) + " Dragon Helmet");
            switch (dragon) {
                case "SUPERIOR":

                case "UNSTABLE":

                case "YOUNG":

                case "OLD":

                case "PROTECTOR":

                case "STRONG":

                case "HOLY":

                case "WISE":

                case "CELESTIAL":

            }
        } else if (weight >= 300 && Math.random() > boots) {
            broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &6" + WordUtils.capitalizeFully(dragon) + " Dragon Boots");
            switch (dragon) {
                case "SUPERIOR":

                case "UNSTABLE":

                case "YOUNG":

                case "OLD":

                case "PROTECTOR":

                case "STRONG":

                case "HOLY":

                case "WISE":

                case "CELESTIAL":

            }
        } else {
            int totalFrags = 0;
            if (playerDamage < 100000) {
                totalFrags = 3;
                broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &63 " + WordUtils.capitalizeFully(dragon) + " Fragments");
            } else if (playerDamage >= 1000000) {
                totalFrags = (int) Math.floor(playerDamage / 1000000D);
                broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &6" + totalFrags + " " + WordUtils.capitalizeFully(dragon) + " Fragments");
            }

            switch (dragon) {
                case "SUPERIOR": //
                    ItemStack fragment = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmY4OWIxNTBiZTljNGM1MjQ5ZjM1NWY2OGVhMGM0MzkxMzAwYTliZTFmMjYwZDc1MGZjMzVhMTgxN2FkNzk2ZSJ9fX0="), "&5Superior Dragon Fragment", "&eRight-click to view recipes!", "", "&5&lEPIC");
                    fragment.setAmount(totalFrags);
                    inv.addItem(fragment);
                    break;
                case "UNSTABLE": //
                    ItemStack fragment1 = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTgyMjhjMjM0YzM5MDNjNTEyYTVhMGFhNDUyNjBlN2I1NjdlMGUyMGVlZmM3ZDU2MWNjZWM5N2IyOTU4NzFhZiJ9fX0="), "&5Unstable Dragon Fragment", "&eRight-click to view recipes!", "", "&5&lEPIC");
                    fragment1.setAmount(totalFrags);
                    inv.addItem(fragment1);
                    break;
                case "YOUNG": //
                    ItemStack fragment2 = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI1YmQ2YjY0ZThiZDZjNThmNWNkMWU3OWE1NTAyZDQ0NDhiYWZjMDA2ZDJmZTA1NjhmNmEwZDZiODZkNDQ5ZSJ9fX0="), "&5Young Dragon Fragment", "&eRight-click to view recipes!", "", "&5&lEPIC");
                    fragment2.setAmount(totalFrags);
                    inv.addItem(fragment2);
                    break;
                case "OLD": //
                    ItemStack fragment3 = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2FhMDlhZDE3N2ZiY2NjNTNmYTMxNmNjMDRiZGQyYzkzNjZiYWVkODg5ZGY3NmM1YTI5ZGVmZWE4MTcwZGVmNSJ9fX0="), "&5Old Dragon Fragment", "&eRight-click to view recipes!", "", "&5&lEPIC");
                    fragment3.setAmount(totalFrags);
                    inv.addItem(fragment3);
                    break;
                case "PROTECTOR":
                    ItemStack fragment4 = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDhkZTMzOWFmNjNhMjI5YzkyMzhkMDI3ZTQ3ZjUzZWViNTYxNDFhNDE5ZjUxYjM1YzMxZWExNDk0YjQzNWRkMyJ9fX0="), "&5Protector Dragon Fragment", "&eRight-click to view recipes!", "", "&5&lEPIC");
                    fragment4.setAmount(totalFrags);
                    inv.addItem(fragment4);
                    break;
                case "STRONG": //
                    ItemStack fragment5 = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmVlMzJmYmQ0YzdiMDNiODY5MDc4YWExZjQ5M2EzOTBlNmUxM2I0NjFkNjEzNzA3ZWFmYjMyNmRiY2QyYjRiNSJ9fX0="), "&5Strong Dragon Fragment", "&eRight-click to view recipes!", "", "&5&lEPIC");
                    fragment5.setAmount(totalFrags);
                    inv.addItem(fragment5);
                    break;
                case "HOLY": //
                    ItemStack fragment6 = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU5NGVkNDY3YjZiM2ZjODlkZTE3OTZlYzJiMjM3NGI5MjAwNmUzZTMwMTY2YzMxMDc0ODdjNmE3YTMzNTg0NSJ9fX0="), "&6Holy Dragon Fragment", "&eRight-click to view recipes!", "", "&6&lLEGENDARY");
                    fragment6.setAmount(totalFrags);
                    inv.addItem(fragment6);
                    break;
                case "WISE": //
                    ItemStack fragment7 = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQ3NjIwYjJlNDkzNDk2M2JiMTI1MDgzMTBkMDU0OTRjMDY3ZGMzM2UwMDhjZWNmMmNkN2I0NTQ5NjU0ZmFiMyJ9fX0="), "&5Wise Dragon Fragment", "&eRight-click to view recipes!", "", "&5&lEPIC");
                    fragment7.setAmount(totalFrags);
                    inv.addItem(fragment7);
                    break;
                case "CELESTIAL":
                    ItemStack fragment8 = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIzOWMwZTUzZTc5ZTdlYmQ0ZGI2YzZkMDk2YzlkOWExNjBjZmYyNzgyMmMwNzdmYjhmNWQ0NTk2OWNjNDk3MiJ9fX0="), "&6Celestial Dragon Fragment", "&eRight-click to view recipes!", "", "&6&lLEGENDARY");
                    fragment8.setAmount(totalFrags);
                    inv.addItem(fragment8);
                    break;
            }
         }
    }

    private void broadcastWorld(Player plr, String msg) {
        for (Player player : plr.getWorld().getPlayers()) {
            player.sendMessage(Utils.chat(msg));
        }
    }

    private String chooseDragon() {
        if (Math.random() > .99) return "HOLY";
        else if (Math.random() > .96) return "SUPERIOR";
        else return CommonDragons.values()[(int) (Math.random() * CommonDragons.values().length)].toString();
    }

    private void randomizeDragonPath(LivingEntity enderDragon) {
        Location loc = enderDragon.getLocation();

        double X = loc.getX();
        double Y = loc.getY();
        double Z = loc.getZ();

        enderDragon.teleport(loc.add(X + negativeOrPositive(Math.random() * 20), Y + negativeOrPositive(Math.random() * 20), Z + negativeOrPositive(Math.random() * 20)));
    }

    private double negativeOrPositive(double num) {
        return Math.random() > .5 ? num - (num * 2) : num;
    }

    private void bounceBlock(Block b) {
        FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
        b.setType(Material.AIR);
        float x = (float) -0.3 + (float) (Math.random() * ((0.3 - -0.3) + 1));
        float y = (float) 0.5;
        float z = (float) -0.3 + (float) (Math.random() * ((0.3 - -0.3) + 1));
        fb.setVelocity(new Vector(x, y, z));
    }

//    @EventHandler
//    public void onDragonTarget(EntityTargetEvent e) {
//        if (e.getEntityType() == EntityType.ENDER_DRAGON) {
//            e.setCancelled(true);
//        }
//    }

    @EventHandler
    public void onBlockChange(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof FallingBlock) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e) {

        Item item = e.getEntity();
        String itemName = item.getName().toLowerCase();

        if (itemName.equalsIgnoreCase("item.tile.blockcoal") ||
                itemName.equalsIgnoreCase("item.tile.sealantern") ||
                itemName.equalsIgnoreCase("item.tile.stainedglass.purple") ||
                itemName.equalsIgnoreCase("item.tile.thinstainedglass.purple") ||
                itemName.equalsIgnoreCase("item.tile.cloth.purple") ||
                itemName.equalsIgnoreCase("item.tile.obsidian") ||
                itemName.equalsIgnoreCase("item.tile.endportalframe")) e.setCancelled(true);
    }

    @EventHandler
    public void onEntityCreatePortal(EntityCreatePortalEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        // Dragon
        if (e.getEntity().getType() == EntityType.ENDER_DRAGON && plugin.so.isDragonFight()) {

            if (e.getEntity().getKiller() != null) plugin.so.setLastDragonHit(e.getEntity().getKiller());

            plugin.so.setPlacedSummoningEye(0);
            plugin.so.setEnderDragon(null);
            plugin.dragonEvent.regenerateDragonStick(e);

            e.getEntity().teleport(new Location(e.getEntity().getWorld(), 1, 50, -4));

            for (Entity endCrystal : endCrystals) {
                endCrystal.remove();
            }

            for (Player plr : Bukkit.getOnlinePlayers()) {
                PlayerObject poa = plugin.playerManager.playerObjects.get(plr);
                poa.setDamageToDragon(0);

                String numberOne = null;
                String numberTwo = null;
                String numberThree = null;

                HashMap<String, Double> playerDamage2 = new HashMap<>();
                for (Player player : plugin.dragonEvent.playerDamage.keySet()) {
                    playerDamage2.put(Helper.getRank(player), plugin.dragonEvent.playerDamage.get(player));
                }

                TreeMap<String, Double> sorted = new TreeMap<>(playerDamage2);

                for (Map.Entry<String, Double> entry : sorted.descendingMap().entrySet()) {
                    if (numberOne == null && entry.getKey() != null)
                        numberOne = entry.getKey() + " &7- &e" + Utils.coinFormat(entry.getValue());
                    else if (numberTwo == null && entry.getKey() != null)
                        numberTwo = entry.getKey() + " &7- &6" + Utils.coinFormat(entry.getValue());
                    else if (numberThree == null && entry.getKey() != null)
                        numberThree = entry.getKey() + " &7- &c" + Utils.coinFormat(entry.getValue());
                }

                if (numberOne == null) numberOne = "&7[N/A] Not Applicable - &e0";
                if (numberTwo == null) numberTwo = "&7[N/A] Not Applicable - &60";
                if (numberThree == null) numberThree = "&7[N/A] Not Applicable - &c0";

                int position = 0;
                for (int i = 0; i < sorted.keySet().size(); i++) {
                    if (sorted.descendingMap().keySet().toArray()[i] != null) position = i + 1;
                }

                plr.sendMessage(Utils.chat(centerDefaultText("&a&l&m------------------------------------------------")));
                plr.sendMessage(Utils.chat(centerDefaultText("                       &6&l" + plugin.so.getDragonName().toUpperCase() + " DRAGON DOWN!                    ")));
                plr.sendMessage(Utils.chat(centerDefaultText("                                                                ")));
                plr.sendMessage(Utils.chat(centerDefaultText("              " + Helper.getRank(plugin.so.getLastDragonHit()) + " &7dealt the final blow.         ")));
                plr.sendMessage(Utils.chat(centerDefaultText("                                                                ")));
                plr.sendMessage(Utils.chat(centerDefaultText("            &e&l1st Damager &7- " + numberOne + "           ")));
                plr.sendMessage(Utils.chat(centerDefaultText("        &6&l2nd Damager &7- " + numberTwo + "        ")));
                plr.sendMessage(Utils.chat(centerDefaultText("              &c&l3rd Damager &7- " + numberThree + "           ")));
                plr.sendMessage(Utils.chat(centerDefaultText("                                                                ")));
                plr.sendMessage(Utils.chat(centerDefaultText("              &eYour Damage: &a" + Utils.coinFormat(plugin.dragonEvent.playerDamage.get(plr)) + " &7(Position #" + position + ")                         ")));
                plr.sendMessage(Utils.chat(centerDefaultText("                                                                ")));
                plr.sendMessage(Utils.chat(centerDefaultText("&a&l&m------------------------------------------------")));
            }

            for (Player playa : playerDamage.keySet()) {
                getLoot(playa);
            }

            plugin.so.setDragonName(null);
        }
    }

    private static String centerText(String text) {
        int spaces = (int) Math.round((80 - 1.4 * text.length()) / 2);
        return repeat(spaces) + text + repeat(spaces);
    }

    public static String centerDefaultText(String text) {
        return centerText(text);
    }

    private static String repeat(int amount) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            ret.append(" ");
        }
        return ret.toString();
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        e.setCancelled(true);
    }
}
