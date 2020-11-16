package xyz.apollo30.skyblockremastered.listeners;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.tasks.ConstantTask;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class ItemEvents implements Listener {

    private final SkyblockRemastered plugin;

    public static HashMap<Player, ArmorStand> orbs = new HashMap<>();

    public ItemEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        Player plr = e.getPlayer();
        PlayerObject po = PlayerManager.playerObjects.get(plr);
        if (po == null) {
            plr.sendMessage(Utils.chat("&cError when loading the player's data, please try again later."));
        } else if (e.getItem() != null) {
            if (Helper.hasCustomName(e.getItem())) {
                if (Helper.getCustomName(e.getItem()).equals(Utils.chat("&6Mage Beam"))) {
                    Entity en = getNearestEntityInSight(plr, 20);
                    if (en != null) {
                        if (e.getAction() == Action.RIGHT_CLICK_AIR && en instanceof LivingEntity) {
                            LivingEntity entity = (LivingEntity) en;
                            shootBeam(plr.getLocation(), entity.getLocation());
                            int damage = (int) (0.2 * po.getBaseIntelligence());
                            Helper.damageMob(entity, damage, "normal");
                        }
                    }
                } else if ((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && Helper.getCustomName(e.getItem()).equals(Utils.chat("&aRadiant Power Orb"))) {

                    if (orbs.containsKey(plr)) {
                        ArmorStand orb = orbs.get(plr);
                        if (orb == null) return;
                        plr.sendMessage(Utils.chat("&aYour previous power orb has been removed!"));
                        if (orb.getPassenger() != null) orb.getPassenger().remove();
                        orb.remove();
                        orbs.remove(plr);
                    }

                    Location location = plr.getLocation();

                    // Armor Stand with the Skull
                    ArmorStand orb = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                    orb.setGravity(true);
                    orb.setVisible(false);
                    orb.setHelmet(GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2FiNGM0ZDZlZTY5YmMyNGJiYTJiOGZhZjY3YjlmNzA0YTA2YjAxYWE5M2YzZWZhNmFlZjdhOTY5NmM0ZmVlZiJ9fX0="));

                    // Armor Stand with the name;
                    ArmorStand name = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                    name.setGravity(true);
                    name.setCustomNameVisible(true);
                    name.setVisible(false);
                    name.setMarker(true);
                    name.setCustomName(Utils.chat("&aRadiant Power Orb"));

                    // Setting the name to the skull
                    orb.setPassenger(name);
                    plr.playSound(plr.getLocation(), Sound.WOOD_CLICK, 100F, 1F);
                    orbs.put(plr, orb);
                    ArrayList<Object> list = new ArrayList<>();
                    list.add(orb);
                    list.add(orb.getLocation().getY());
                    ConstantTask.orbs.put(orb, list);
                }
            }
        }
    }

    public static Entity getNearestEntityInSight(Player player, int range) {
        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
        entities = entities.stream().filter(x -> x.getType() != EntityType.ARMOR_STAND).collect(Collectors.toCollection(ArrayList::new));
        Set<Material> hs = new HashSet<>();
        hs.add(Material.AIR);
        hs.add(Material.WATER);
        hs.add(Material.STATIONARY_WATER);
        hs.add(Material.SIGN);
        hs.add(Material.LAVA);
        hs.add(Material.STATIONARY_LAVA);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight(hs, range);
        ArrayList<Location> sight = new ArrayList<>();
        for (Block block : sightBlock) sight.add(block.getLocation());
        for (Location location : sight) {
            for (Entity entity : entities) {
                if (Math.abs(entity.getLocation().getX() - location.getX()) < 1.3) {
                    if (Math.abs(entity.getLocation().getY() - location.getY()) < 1.5) {
                        if (Math.abs(entity.getLocation().getZ() - location.getZ()) < 1.3) {
                            return entity;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void shootBeam(Location loc1, Location loc2) {
        Location newLocVec = loc1.clone();

        newLocVec.setYaw((float) this.getSelectedYaw(loc1, loc2));
        newLocVec.setPitch((float) this.getSelectedPitch(loc1, loc2));

        for (Vector clonedLoc : this.traverse(loc1.toVector(), newLocVec.getDirection())) {
            PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.FIREWORKS_SPARK, true, (float) clonedLoc.getX(), (float) clonedLoc.getY(), (float) clonedLoc.getZ(), 0, 0, 0, 0, 1);

            for (Player online : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(particle);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player plr = e.getPlayer();

        /*
         * Double Jump Ability Below
         * Checks if they are wearing tarantula armor.
         */
        if (plr.getEquipment().getBoots() != null && plr.getEquipment().getBoots().hasItemMeta() && !plr.getEquipment().getBoots().getItemMeta().getDisplayName().isEmpty()) {
            if (plr.getEquipment().getBoots().getItemMeta().getDisplayName().contains(Utils.chat("&5Tarantula Boots"))) {
                if (!plr.getGameMode().equals(GameMode.CREATIVE)) {
                    e.getPlayer().setAllowFlight(true);
                    e.getPlayer().setFlying(false);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFlight(PlayerToggleFlightEvent e) {

        Player plr = e.getPlayer();
        PlayerObject po = PlayerManager.playerObjects.get(plr);

        /*
         * Double Jump Ability Below
         * Gives them velocity the direction they jump, and a cooldown of half a second.
         *
         * Also checks if they are wearing Tarantula Boots
         */

        if (plr.getEquipment().getBoots() != null && plr.getEquipment().getBoots().hasItemMeta() && !plr.getEquipment().getBoots().getItemMeta().getDisplayName().isEmpty()) {
            if (plr.getEquipment().getBoots() != null && plr.getEquipment().getBoots().getItemMeta().getDisplayName().contains(Utils.chat("&5Tarantula Boots"))) {
                if (!plr.getGameMode().equals(GameMode.CREATIVE) && !plr.getGameMode().equals(GameMode.SPECTATOR) || !plr.isFlying()) {
                    if (po.getIntelligence() - 40 < 0 || po.getIntelligence() <= 0) {
                        plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, .5F);
                        plr.sendMessage(Utils.chat("&cYou do not have enough mana"));
                        e.setCancelled(true);
                        return;
                    }
                    int usedMana = 40;
                    plr.sendMessage(Utils.chat("&aUsed &6Double Jump &b(" + usedMana + " Mana)"));
                    po.setIntelligence(po.getIntelligence() - usedMana);
                    e.setCancelled(true);
                    plr.setAllowFlight(false);
                    plr.setFlying(false);
                    for (int i = 0; i < 50; i++) {
                        plr.getWorld().playEffect(plr.getLocation().add(Math.random() * 5, Math.random() * 5, Math.random() * 5), Effect.SMOKE, 5, 1);
                    }
                    plr.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1).setY(plr.getLocation().getPitch() < -70 ? 1.25 : plr.getLocation().getPitch() < -50 ? 1.125 : 1));
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> plr.setAllowFlight(true), 20);
                }
            }
        }
    }

    private final HashMap<Player, Vector> grapple = new HashMap<>();
    private final HashMap<Player, Long> grappleCooldown = new HashMap<>();

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {

        Projectile projectile = e.getEntity();
        if (!(projectile.getShooter() instanceof Player)) return;
        Player plr = (Player) projectile.getShooter();
        ItemStack itemInHand = ((Player) e.getEntity().getShooter()).getItemInHand();

        /*
         * Runaan's Bow
         * Oh baby its tripple
         */
        if (itemInHand != null && itemInHand.hasItemMeta() && !itemInHand.getItemMeta().getDisplayName().isEmpty()) {
            if (itemInHand.getItemMeta().getDisplayName().contains(Utils.chat("&6Runaan's Bow"))) {
                if (plr == null || !plr.getItemInHand().getType().equals(Material.BOW)) return;
//                Arrow arrow1 = plr.getWorld().spawn(plr.getEyeLocation(), Arrow.class);
//                arrow1.setShooter(plr);
//
//                Arrow arrow2 = plr.getWorld().spawn(plr.getEyeLocation(), Arrow.class);
//                arrow2.setShooter(plr);
//
//                new BukkitRunnable() {
//                    @Override
//                    public void run() {
//
//                        if (arrow1.isDead() && arrow2.isDead()) this.cancel();
//
//                        // Arrow 1
//                        if (!arrow1.isDead()) {
//                            Location projLoc = projectile.getLocation();
//                            projLoc.setYaw(projLoc.getYaw() - 7);
//                            arrow1.setVelocity(projLoc.toVector());
//                        }
//
//                        // Arrow 2
//                        if (!arrow2.isDead()) {
//                            Location projLoc = projectile.getLocation();
//                            projLoc.setYaw(projLoc.getYaw() + 7);
//                            arrow1.setVelocity(projLoc.toVector());
//                        }
//
//                    }
//                }.runTaskTimer(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), 0L, 1L);
            }
        }

        /*
         * Grappling Hook Usage
         * This logs when the user throws the grappling hook and stores its original direction incase they move their screen
         */
        if (itemInHand != null && itemInHand.hasItemMeta() && !itemInHand.getItemMeta().getDisplayName().isEmpty()) {
            if (itemInHand.getItemMeta().getDisplayName().contains(Utils.chat("&aGrappling Hook"))) {
                if (!e.getEntityType().equals(EntityType.FISHING_HOOK)) return;
                if (!(e.getEntity().getShooter() instanceof Player)) return;
                grapple.put(plr, e.getEntity().getVelocity());
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e) {

        /*
         * Grappling Hook Usage
         * This checks when they reel their hook back in.
         * If so, launches them while detecting how far the threshold is.
         */
        ItemStack itemInHand = e.getPlayer().getItemInHand();
        if (itemInHand != null && itemInHand.hasItemMeta() && !itemInHand.getItemMeta().getDisplayName().isEmpty()) {
            if (itemInHand.getItemMeta().getDisplayName().contains(Utils.chat("&aGrappling Hook"))) {
                if (!e.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT)) return;
                Player plr = e.getPlayer();
                double distance = plr.getLocation().distance(e.getHook().getLocation());
                if (grapple.containsKey(plr)) {
                    try {
                        if (grappleCooldown.containsKey(plr) && grappleCooldown.get(plr) > new Date().getTime()) {
                            plr.sendMessage(Utils.chat("&cWoah there, Slow down!"));
                            return;
                        } else grappleCooldown.remove(plr);
                        Location plrLoc = plr.getLocation();
                        Location hookLoc = e.getHook().getLocation();

                        double dis = plrLoc.distance(hookLoc);

                        double X = (1.0 + 0.24 * dis) * (hookLoc.getX() - plrLoc.getX()) / dis;
                        double Y = (1.0 + 0.12 * dis) * (hookLoc.getY() - plrLoc.getY()) / dis - 0.5 * -0.08 * dis;
                        double Z = (1.0 + 0.24 * dis) * (hookLoc.getZ() - plrLoc.getZ()) / dis;

                        Vector v = plr.getVelocity();
                        v.setX(X);
                        v.setY(Y);
                        v.setZ(Z);
                        plr.setVelocity(v);

                        plr.setVelocity(grapple.get(plr).multiply(distance * .3).setY((distance * 0.1) > 1 ? 1 : plr.getLocation().getPitch() < -70 ? 1.25 : plr.getLocation().getPitch() < -50 ? 1.125 : 1));
                        grapple.remove(plr);
                        grappleCooldown.put(plr, new Date().getTime() + 2000);
                    } catch (Exception err) {
                        plr.sendMessage(Utils.chat("&cWoah there, that's a bit too far!"));
                    }
                }
            }
        }
    }

    private double getSelectedYaw(Location loc1, Location loc2) {
        double z = loc1.getZ() - loc2.getZ();
        double x = loc1.getX() - loc2.getX();

        double angleTangent = Math.toDegrees(Math.atan(z / x)) + 90;

        if ((int) x >= 0) {
            if (angleTangent < 180 && angleTangent > -180) {
                return angleTangent;
            }
        } else {
            if (angleTangent < 180 && angleTangent > -180) {
                return -179 + angleTangent;
            }
        }

        return 0;
    }

    private double getSelectedPitch(Location loc1, Location loc2) {
        double y = loc1.getY() - loc2.getY();

        double v0 = Math.pow(loc2.getX() - loc1.getX(), 2);
        double v1 = Math.pow(loc2.getZ() - loc1.getZ(), 2);
        double distanceTotal = v1 + v0;
        double linearDistance = Math.sqrt(distanceTotal);

        return Math.toDegrees(Math.atan(y / linearDistance));
    }

    private ArrayList<Vector> traverse(Vector origin, Vector direction) {
        ArrayList<Vector> positions = new ArrayList<>();
        for (double d = 0; d <= 30.0; d += .5) {
            positions.add(this.getPosition(origin, direction, d));
        }

        return positions;
    }

    private Vector getPosition(Vector origin, Vector direction, double dist) {
        return origin.clone().add(direction.clone().multiply(dist));
    }
}
