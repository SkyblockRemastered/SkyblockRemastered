package xyz.apollo30.skyblockremastered.events.dragonHandler;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderDragon;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.events.dragonHandler.lootTable.*;
import xyz.apollo30.skyblockremastered.items.Miscs;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.NMSUtil;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.*;

public class DragonEvent implements Listener {

    private final SkyblockRemastered plugin;


    public DragonEvent(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    enum CommonDragons {
        STRONG,
        YOUNG,
        UNSTABLE,
        WISE,
        PROTECTOR,
        OLD,
        // SHITASS
    }

    private final HashMap<Location, UUID> placedEyes = new HashMap<>();
    private final HashMap<Location, Long> cooldown = new HashMap<>();
    private final HashMap<Player, Integer> placedPlayerEyes = new HashMap<>();

    private final HashMap<Location, Byte> blockDatabase = new HashMap<>();

    private final HashMap<Location, Material> stickRegeneration = new HashMap<>();
    private final HashMap<Location, Material> dragonEggRegeneration = new HashMap<>();
    private final HashMap<Location, Material> glassRegeneration = new HashMap<>();
    public final HashMap<Player, Double> playerDamage = new HashMap<>();
    public static List<Location> endCrystals = new ArrayList<>();

    public void placeSummoningEye(PlayerInteractEvent e) {

        e.setCancelled(true);
        Block blocc = e.getClickedBlock();
        if (blocc != null && Utils.isInZone(blocc.getLocation(), new Location(blocc.getWorld(), -2, 5, -7), new Location(blocc.getWorld(), 4, 15, -1))) {
            if (blocc.getType() == Material.ENDER_PORTAL_FRAME && blocc.getData() != 6) {
                if (SkyblockRemastered.so.isDragonFight()) {
                    e.getPlayer().sendMessage(Utils.chat("&cThe Altar Table is temporarily disabled!"));
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1F, .5F);
                } else {
                    blocc.setData((byte) 6);
                    SkyblockRemastered.so.setPlacedSummoningEye(SkyblockRemastered.so.getPlacedSummoningEye() + 1);

                    e.getPlayer().getInventory().remove(e.getItem());
                    e.getPlayer().getInventory().addItem(NMSUtil.addString(Miscs.REMNANT_OF_THE_EYE, "UUID", UUID.randomUUID().toString()));


                    if (SkyblockRemastered.so.getPlacedSummoningEye() >= 8) {
                        dragonSummoningSequence(e);
                        placedPlayerEyes.put(e.getPlayer(), placedPlayerEyes.get(e.getPlayer()) == null ? 0 : placedPlayerEyes.get(e.getPlayer()) + 1);
                        SkyblockRemastered.so.setDragonFight(true);
                        placedEyes.put(blocc.getLocation(), e.getPlayer().getUniqueId());
                        cooldown.put(blocc.getLocation(), new Date().getTime() + 15000);
                        for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                            plr.sendMessage(Utils.chat("&5❂ " + Helper.getRank(e.getPlayer(), false) + " &dplaced a Summoning Eye! Brace Yourselves! &7(&a" + plugin.so.getPlacedSummoningEye() + "&7/&a8&7)"));
                            plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1.5F);
                        }
                    } else {
                        placedEyes.put(blocc.getLocation(), e.getPlayer().getUniqueId());
                        cooldown.put(blocc.getLocation(), new Date().getTime() + 15000);
                        placedPlayerEyes.put(e.getPlayer(), placedPlayerEyes.get(e.getPlayer()) == null ? 0 : placedPlayerEyes.get(e.getPlayer()) + 1);
                        for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                            plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1.5F);
                            plr.sendMessage(Utils.chat("&5❂ " + Helper.getRank(e.getPlayer(), false) + " &dplaced a Summoning Eye! &7(&e" + SkyblockRemastered.so.getPlacedSummoningEye() + "&7/&a8&7)"));
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
                if (SkyblockRemastered.so.isDragonFight()) {
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
                            e.getPlayer().sendMessage(Utils.chat("&cYou must wait &e" + Utils.doubleFormat((double) kek) + "s&c to do that!"));
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1F, .5F);
                            return;
                        }
                    }

                    blocc.setData((byte) 0);
                    SkyblockRemastered.so.setPlacedSummoningEye(SkyblockRemastered.so.getPlacedSummoningEye() - 1);

                    placedPlayerEyes.put(e.getPlayer(), placedPlayerEyes.get(e.getPlayer()) == null ? 0 : placedPlayerEyes.get(e.getPlayer()) - 1);

                    for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                        plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, .5F);
                        plr.sendMessage(Utils.chat("&5❂ " + Helper.getRank(e.getPlayer(), false) + " &drecovered a Summoning Eye.. &7(&e" + SkyblockRemastered.so.getPlacedSummoningEye() + "&7/&a8&7)"));
                        cooldown.remove(blocc.getLocation());
                        placedEyes.remove(blocc.getLocation());
                    }

                    e.getPlayer().getInventory().remove(e.getItem());
                    e.getPlayer().getInventory().addItem(NMSUtil.addString(Miscs.SUMMONING_EYE, "UUID", UUID.randomUUID().toString()));
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

            animateGlass(e);
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
                            if (Math.random() > .9) {
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
                String dragonName = "&c&l" + WordUtils.capitalizeFully(type) + (type.equalsIgnoreCase("shitass") ? "" : " Dragon");
                for (Player plr : e.getPlayer().getWorld().getPlayers()) {
                    plr.playSound(plr.getLocation(), Sound.ENDERDRAGON_GROWL, 1000F, 1F);
                    plr.sendMessage(Utils.chat("&5❂ &d&lA &c&l" + dragonName + " &d&lhas spawned!"));
                    playerDamage.put(plr, (double) 0);
                }

                if (dragonName.equalsIgnoreCase(Utils.chat("&c&lshitass"))) {
                    JavaPlugin.getProvidingPlugin(SkyblockRemastered.class).getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), () -> {
                        Utils.broadCast("&c[BOSS] shitass&7: &c&lYou are challenge me? The ultimate &ka &r&c&l&nSHITASS!? &ka");
                    }, 20);
                    JavaPlugin.getProvidingPlugin(SkyblockRemastered.class).getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), () -> {
                        Utils.broadCast("&c[BOSS] shitass&7: &c&lDIE MORTAL!");
                    }, 40);
                }

                CustomEnderDragon eDragon = CustomEnderDragon.spawn(new Location(e.getPlayer().getWorld(), 1, 63, -4), dragonName);
                LivingEntity enderDragon = (EnderDragon) eDragon.getBukkitEntity();
                enderDragon.setCustomName(Utils.chat(dragonName));

                ((CraftEnderDragon) enderDragon).getHandle().getNavigation().a(49, 33, 2);

                plugin.mobManager.createMob(enderDragon, type.toLowerCase());
                plugin.so.setDragonName(type.toUpperCase());
                CustomEnderDragon.endCrystals = spawnCrystals(e);

                enderDragon.setNoDamageTicks(0);
                enderDragon.setMaximumNoDamageTicks(0);
            }, 150);
        }, 60);
    }

    private int spawnCrystals(PlayerInteractEvent e) {
        List<Location> crystalSpawns = new ArrayList<>();
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), -10.5, 44.5, 48.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), -39.5, 69.5, 35.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), -43.5, 44.5, -27.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), 36.5, 51.5, 38.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), 29.5, 36.5, 25.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), -25.5, 35.5, 22.5));
        crystalSpawns.add(new Location(e.getPlayer().getWorld(), 1.5, 58.5, 42));

        int crystalsLeft = 0;

        while (crystalsLeft < 6) {
            for (Location loc : crystalSpawns) {
                loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
                endCrystals.add(loc);
                crystalsLeft += 1;
            }
        }

        return 5;
    }

    public void regenerateDragonStick(EntityDeathEvent e) {
        regenerateGlass(e);
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
                            plr.sendMessage(Utils.chat("&5❂ &dA Dragon Egg has spawned!"));
                            plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1000, 1.25F);
                        }

                        playerDamage.clear();
                        placedEyes.clear();
                        placedPlayerEyes.clear();
                        blockDatabase.clear();
                        dragonEggRegeneration.clear();
                        plugin.so.setRiggedDragon(null);
                    } catch (Exception ignore) {
                    }
                }

                blockDatabase.remove(blocc1.getLocation());
                stickRegeneration.remove(blocc1.getLocation());
            }, 20 * i);
        }
    }

    private void regenerateGlass(EntityDeathEvent e) {
        for (int y = 14; y < 30; y++) {
            int finalY = y;
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                for (Location loc : glassRegeneration.keySet()) {
                    if (finalY == (int) loc.getY()) {
                        Block block = e.getEntity().getWorld().getBlockAt(loc);

                        block.setType(glassRegeneration.get(loc));
                        block.setData(blockDatabase.get(loc));
                    }
                }
            }, 20 * finalY);
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
        for (Player plr : e.getPlayer().getWorld().getPlayers()) {
            plr.playSound(plr.getLocation(), Sound.ENDERMAN_STARE, 1000F, 1.2F);
        }
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
                        armorStand.setHelmet(GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZlOGU3ZjJkYjhlYWE4OGEwNDFjODlkNGMzNTNkMDY2Y2M0ZWRlZjc3ZWRjZjVlMDhiYjVkM2JhYWQifX19"));
                        armorStand.setSmall(true);

                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, armorStand::remove, 80);
                        riseEyes(armorStand);
                    }
                }
            }
        }
    }

    private void animateGlass(PlayerInteractEvent e) {
        // 10, 14, 6
        // -8, 30, -14
        for (int y = 14; y < 30; y++) {
            int finalY = y;
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                for (int x = -8; x < 10; x++) {
                    for (int z = -14; z < 6; z++) {
                        Block block = e.getPlayer().getWorld().getBlockAt(x, finalY, z);
                        if (block.getType() == Material.STAINED_GLASS_PANE && block.getData() == 10) {
                            glassRegeneration.put(block.getLocation(), block.getType());
                            blockDatabase.put(block.getLocation(), block.getData());
                            block.setType(Material.AIR);
                        }
                    }
                }
            }, y * 3);
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

    private void getLoot(Player plr, int position) {

        int placedEyes = plugin.dragonEvent.placedPlayerEyes.getOrDefault(plr, 0);
        int weight = 0;

        // default to 3 frags if weight is >= 22 but there isn't enough weight to fulfill other prizes. Subtract weight once loot is chosen, then divide the remaining weight by 22. Give player that amount of frags.

        /* Stat Weight */
        // 1 eye = 100w, caps at 4 eyes.
        // 1st in dmg = 300w
        // 2nd in dmg = 250w
        // 2nd in dmg = 200w
        // 2nd in dmg = 250w
        // 4th to 7th = 125w
        // 8th to 15th = 100w
        // 16th or below = 75w

        /* Drops (Calculated in order. Once one drop is chosen, return and give frags for extras */
        // AOTD = 450w, shouldn't drop from superior because nobody wants that
        // Dragon Scale (Spiked) = 450w, shouldn't drop from superior because nobody wants that (wont subtract, you can still get frags)
        // Dragon Claw (Fabled) = 450w, shouldn't drop from superior because nobody wants that (wont subtract, you can still get frags)
        // Dragon Horn (Renowned) = 450w, only from sup (wont subtract, you can still get frags)
        // Pet = 450w 1/300 chance, then once that is passed, 1/5 for legendary & 4/5 for epic
        // Scroll = 250w, only from unstable (wont subtract, you can still get frags)
        // Chest = 400w
        // Helm = 325w
        // Legs = 350w
        // Boots = 300w

        // Weight for the eye placement
        if (placedEyes >= 4) weight += 400;
        else weight += placedEyes * 100;

        // Position
        if (plugin.dragonEvent.playerDamage.get(plr) != 0) {
            if (position == 1) weight += 300;
            else if (position == 2) weight += 250;
            else if (position == 3) weight += 200;
            else if (position >= 4 && position <= 7) weight += 125;
            else if (position >= 8 && position <= 15) weight += 100;
            else if (position >= 16) weight += 75;
        }

        getItem(weight, plr, plugin.so.getDragonName(), placedEyes);
    }

    private void getItem(int weight, Player plr, String dragon, int eyesPlaced) {
        switch (dragon) {
            case "SUPERIOR":
                new SuperiorDragon(plr, weight).getItem();
                break;
            case "UNSTABLE":
                new UnstableDragon(plr, weight, eyesPlaced).getItem();
                break;
            case "YOUNG":
                new YoungDragon(plr, weight, eyesPlaced).getItem();
                break;
            case "OLD":
                new OldDragon(plr, weight, eyesPlaced).getItem();
                break;
            case "PROTECTOR":
                new ProtectorDragon(plr, weight, eyesPlaced).getItem();
                break;
            case "STRONG":
                new StrongDragon(plr, weight, eyesPlaced).getItem();
                break;
            case "WISE":
                new WiseDragon(plr, weight, eyesPlaced).getItem();
                break;
            case "CELESTIAL":
                new CelestialDragon(plr, weight).getItem();
                break;
        }
    }

    private static List<Map.Entry<String, Double>> sortByValues(HashMap<String, Double> map) {
        List<Map.Entry<String, Double>> list = new ArrayList<>(map.entrySet());
        list.sort((e1, e2) -> -e1.getValue().compareTo(e2.getValue()));
        return list;
    }



    private String chooseDragon() {
        if (SkyblockRemastered.so.getRiggedDragon() != null) return SkyblockRemastered.so.getRiggedDragon();
        if (Math.random() > .99) return "CELESTIAL";
        else if (Math.random() > .96) return "SUPERIOR";
        else return CommonDragons.values()[(int) (Math.random() * CommonDragons.values().length)].toString();
    }

    private String centerText(String text) {
        int maxWidth = 80,
                spaces = (int) Math.round((maxWidth - 1.4 * ChatColor.stripColor(text).length()) / 2);
        return StringUtils.repeat(" ", spaces) + text;
    }

    private void bounceBlock(Block b) {
        FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
        b.setType(Material.AIR);
        float x = (float) -0.3 + (float) (Math.random() * ((0.3 - -0.3) + 1));
        float y = (float) 0.5;
        float z = (float) -0.3 + (float) (Math.random() * ((0.3 - -0.3) + 1));
        fb.setVelocity(new Vector(x, y, z));
    }

    @EventHandler
    public void onDragonTarget(EntityTargetEvent e) {
        if (e.getEntityType() == EntityType.ENDER_DRAGON) {
            if (e.getTarget().getType() == EntityType.PLAYER && !e.isCancelled()) CustomEnderDragon.targetingPlayer = true;
            else if (e.getTarget().getType() == EntityType.PLAYER && e.isCancelled()) CustomEnderDragon.targetingPlayer = false;
        }
    }

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
                itemName.equalsIgnoreCase("item.tile.endportalframe") ||
                itemName.equalsIgnoreCase("item.tile.sand")) e.setCancelled(true);
    }

    @EventHandler
    public void onEntityCreatePortal(EntityCreatePortalEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        // Dragon
        if (e.getEntity().getType() == EntityType.ENDER_DRAGON && SkyblockRemastered.so.isDragonFight()) {

            if (e.getEntity().getKiller() != null) SkyblockRemastered.so.setLastDragonHit(e.getEntity().getKiller());

            SkyblockRemastered.so.setPlacedSummoningEye(0);
            SkyblockRemastered.so.setEnderDragon(null);
            SkyblockRemastered.dragonEvent.regenerateDragonStick(e);

            e.getEntity().teleport(new Location(e.getEntity().getWorld(), 1, 50, -4));

            for (Location endCrystal : endCrystals) {
                for (Entity entity : endCrystal.getChunk().getEntities()) {
                    if (entity.getType() == EntityType.ENDER_CRYSTAL) entity.remove();
                }
            }

            endCrystals.clear();

            String numberOne = null;
            String numberTwo = null;
            String numberThree = null;

            HashMap<String, Double> playerDamage2 = new HashMap<>();
            for (Player player : SkyblockRemastered.dragonEvent.playerDamage.keySet()) {
                playerDamage2.put(Helper.getRank(player, true), SkyblockRemastered.dragonEvent.playerDamage.get(player));
            }

            int position = 0;

            for (Player plr : Bukkit.getOnlinePlayers()) {

                for (Map.Entry<String, Double> entry : sortByValues(playerDamage2)) {
                    if (numberOne == null && entry.getKey() != null)
                        numberOne = entry.getKey() + " &7- &e" + Utils.doubleFormat(entry.getValue());
                    else if (numberTwo == null && entry.getKey() != null)
                        numberTwo = entry.getKey() + " &7- &6" + Utils.doubleFormat(entry.getValue());
                    else if (numberThree == null && entry.getKey() != null)
                        numberThree = entry.getKey() + " &7- &c" + Utils.doubleFormat(entry.getValue());
                }

                if (numberOne == null) numberOne = "&7&kApollon was here&r - &e0";
                if (numberTwo == null) numberTwo = "&7&kApollon was here&r - &60";
                if (numberThree == null) numberThree = "&7&kApollon was here&r - &c0";

                for (int i = 0; i < sortByValues(playerDamage2).size(); i++) {
                    if (sortByValues(playerDamage2).get(i).getValue().equals(playerDamage.get(plr))) position = i + 1;
                }

                plr.sendMessage(Utils.chat(centerText("&a&l&m------------------------------------------------")));
                plr.sendMessage(Utils.chat(centerText("                       &6&l" + SkyblockRemastered.so.getDragonName().toUpperCase() + " DRAGON DOWN!                    ")));
                plr.sendMessage(Utils.chat(centerText("                                                                ")));
                plr.sendMessage(Utils.chat(centerText("              " + Helper.getRank(SkyblockRemastered.so.getLastDragonHit(), true) + " &7dealt the final blow.         ")));
                plr.sendMessage(Utils.chat(centerText("                                                                ")));
                plr.sendMessage(Utils.chat(centerText("            &e&l1st Damager &7- " + numberOne + "           ")));
                plr.sendMessage(Utils.chat(centerText("        &6&l2nd Damager &7- " + numberTwo + "        ")));
                plr.sendMessage(Utils.chat(centerText("              &c&l3rd Damager &7- " + numberThree + "           ")));
                plr.sendMessage(Utils.chat(centerText("                                                                ")));
                plr.sendMessage(Utils.chat(centerText("                   &eYour Damage: &a" + Utils.doubleFormat(SkyblockRemastered.dragonEvent.playerDamage.get(plr)) + " &7(Position #" + position + ")                         ")));
                plr.sendMessage(Utils.chat(centerText("                                                                ")));
                plr.sendMessage(Utils.chat(centerText("&a&l&m------------------------------------------------")));
            }

            for (Player playa : playerDamage.keySet()) {
                getLoot(playa, position);
            }

            SkyblockRemastered.so.setDragonName(null);
        }
    }
}
