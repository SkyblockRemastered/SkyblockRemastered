package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.templates.PlayerTemplate;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;
import java.util.HashMap;

public class ItemAbilityEvents implements Listener {

    private final SkyblockRemastered plugin;

    public ItemAbilityEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

//    @EventHandler
//    public void onPlayerWalk(PlayerMoveEvent e) {
//
//        Player plr = e.getPlayer();
//        PlayerObject po = plugin.playerManager.playerObjects.get(plr);
//
//
//    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player plr = e.getPlayer();

        /**
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
        PlayerTemplate po = PlayerManager.playerObjects.get(plr);

        /**
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

        /**
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

        /**
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

        /**
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
}
