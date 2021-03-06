package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.events.dragonHandler.CustomEnderDragon;
import xyz.apollo30.skyblockremastered.events.dragonHandler.DragonEvent;
import xyz.apollo30.skyblockremastered.items.Miscs;
import xyz.apollo30.skyblockremastered.managers.MobManager;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DamageEvents implements Listener {

    private final SkyblockRemastered plugin;

    public DamageEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        /*
         * Detecting if a player threw another projectile to another player
         * If so check if theres pvp or not.
         */

        ArrayList<EntityType> projectiles = new ArrayList<>();
        projectiles.add(EntityType.ARROW);
        projectiles.add(EntityType.SNOWBALL);
        projectiles.add(EntityType.ENDER_PEARL);
        projectiles.add(EntityType.EGG);
        projectiles.add(EntityType.FISHING_HOOK);
        projectiles.add(EntityType.SPLASH_POTION);
        if (projectiles.contains(e.getDamager().getType()) && e.getEntityType() == EntityType.PLAYER) {
            Projectile proj = (Projectile) e.getDamager();
            if (proj.getShooter() instanceof Player) {
                if (!SkyblockRemastered.so.isPvp()) e.setCancelled(true);
            }
        }

        /*
         * Reworking on the enderman physics.
         * Disabling their teleportation when shot by an arrow, potion or projectile.
         */
        if (e.getDamager().getType() == EntityType.ARROW || e.getDamager().getType() == EntityType.SNOWBALL || e.getDamager().getType() == EntityType.SNOWMAN || e.getDamager().getType() == EntityType.SPLASH_POTION || e.getDamager().getType() == EntityType.FIREBALL) {
            if (e.getEntityType() == EntityType.ENDERMAN) {
                e.setCancelled(true);
                e.getEntity().teleport(e.getDamager().getLocation());
            }
        }

        /*
         * Ender Crystal Handler
         * Checks if an arrow shot by a player or a player hitting it
         * If so, we give them the crystal.
         */
        if (e.getDamager().getType() == EntityType.PLAYER && e.getEntityType() == EntityType.ENDER_CRYSTAL) {
            for (Player plr : e.getEntity().getWorld().getPlayers()) {
                plr.sendMessage(Utils.chat("&5❂ &d" + Helper.getRank((Player) e.getDamager(), false) + "&d broke an &5End Crystal&d!"));
                plr.playSound(plr.getLocation(), Sound.EXPLODE, 1000F, 1F);
            }
            ((Player) e.getDamager()).getInventory().addItem(Miscs.CRYSTAL_FRAGMENT);
            e.getEntity().remove();
            DragonEvent.endCrystals.remove(e.getEntity().getLocation());
            CustomEnderDragon.endCrystals -= 1;
            return;
        } else if (e.getDamager().getType() == EntityType.ARROW) {
            if (e.getEntityType() == EntityType.ENDER_CRYSTAL) {
                Arrow arrow = (Arrow) e.getDamager();
                for (Player plr : e.getEntity().getWorld().getPlayers()) {
                    plr.sendMessage(Utils.chat("&5❂ &d" + Helper.getRank((Player) arrow.getShooter(), false) + "&d broke an &5End Crystal&d!"));
                    plr.playSound(plr.getLocation(), Sound.EXPLODE, 1000F, 1F);
                }
                ((Player) ((Arrow) e.getDamager()).getShooter()).getInventory().addItem(Miscs.CRYSTAL_FRAGMENT);
                e.getEntity().remove();
                DragonEvent.endCrystals.remove(e.getEntity().getLocation());
                CustomEnderDragon.endCrystals -= 1;
                return;
            }
        }


        // Player (Projectile) to Mob
        if (e.getDamager().getType() == EntityType.ARROW && e.getEntity() instanceof LivingEntity && !e.getEntity().getType().equals(EntityType.PLAYER)) {

            Arrow arrow = (Arrow) e.getDamager();
            if (!(arrow.getShooter() instanceof Player)) return;
            Player plr = (Player) arrow.getShooter();

            ItemStack bow = plr.getInventory().getItemInHand();

            Player damager = (Player) arrow.getShooter();
            LivingEntity target = (LivingEntity) e.getEntity();

            if (!(target instanceof Player)) {
                PlayerObject po = PlayerManager.playerObjects.get(damager);
                MobObject mo = MobManager.mobObjects.get(target);

                if (po == null || mo == null) return;

                plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1000F, .8F);

                // Defining the player's stats
                int strength = po.getStrength();
                int weaponDamage = 0;
                int critDamage = po.getCritDamage();
                int critChance = po.getCritChance();
                int atkSpeed = po.getAtkSpeed();

                if (bow.hasItemMeta() && bow.getItemMeta().hasLore()) {

                    // Fetching the Weapon's Stats.
                    if (damager.getItemInHand().hasItemMeta() && damager.getItemInHand().getItemMeta().hasLore()) {
                        for (String lore : damager.getItemInHand().getItemMeta().getLore()) {
                            if (lore.startsWith(Utils.chat("&7Damage"))) weaponDamage += parseLore(lore);
                        }
                    }
                }

                // Attack Speed
                attackSpeed(atkSpeed, target);

                // Adding Crit Chance
                critChance += po.getCombatLevel() * .05;

                // Base Formula
                int base_damage = (int) ((5 + weaponDamage + (Math.floor(strength) / 5)) * (1 + strength / 100));

                // Final Damage
                int final_damage = (int) (base_damage + (base_damage * .04));

                String type = "normal";

                if ((Math.random() * 100) < critChance) {
                    final_damage = final_damage * (1 + critDamage / 100);
                    type = "crithit";
                }

                e.setDamage(0);
                mo.setHealth((mo.getHealth() - final_damage));

                if (e.getEntityType() == EntityType.ENDER_DRAGON && SkyblockRemastered.so.isDragonFight()) {
                    // Utils.broadCast("[DEBUG] " + ((mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth()));
                    if (target.getCustomName() != null && target.getCustomName().toLowerCase().contains("old")) {
                        final_damage = final_damage / 2;
                    }

                    target.setHealth(((mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth() > 0 ? (mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth() : 0));
                    SkyblockRemastered.dragonEvent.playerDamage.put(damager, SkyblockRemastered.dragonEvent.playerDamage.get(damager) + (double) final_damage);
                    SkyblockRemastered.so.setLastDragonHit(damager);
                }

                if (mo.getHealth() <= 0 && !target.isDead())
                    target.setHealth(0);

                Utils.damageIndicator(target, final_damage, type);
                if (!target.isDead())
                    e.getEntity().getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));
            }
        }

        // Player (Melee) to Mob
        else if (e.getDamager().getType() == EntityType.PLAYER && e.getEntity() instanceof LivingEntity && !e.getEntity().getType().equals(EntityType.PLAYER)) {

            Player damager = (Player) e.getDamager();
            LivingEntity target = (LivingEntity) e.getEntity();

            PlayerObject po = PlayerManager.playerObjects.get(damager);
            MobObject mo = MobManager.mobObjects.get(target);

            if (po == null || mo == null) return;

            // Defining the player's stats
            int strength = po.getStrength();
            int weaponDamage = 0;
            int critDamage = po.getCritDamage();
            int critChance = po.getCritChance();
            int atkSpeed = po.getAtkSpeed();

            // Fetching the Weapon's Stats.
            if (damager.getItemInHand().hasItemMeta() && damager.getItemInHand().getItemMeta().hasLore()) {
                for (String lore : damager.getItemInHand().getItemMeta().getLore()) {
                    if (lore.startsWith(Utils.chat("&7Damage"))) weaponDamage += parseLore(lore);
                }
            }

            // Attack Speed
            attackSpeed(atkSpeed, target);

            // Adding Crit Chance
            critChance += po.getCombatLevel() * .05;

            // Base Formula
            int base_damage = (int) ((5 + weaponDamage + (Math.floor(strength) / 5)) * (1 + strength / 100));

            // Final Damage
            int final_damage = (int) (base_damage + (base_damage * .04));

            String type = "normal";

            if ((Math.random() * 100) < critChance) {
                final_damage = final_damage * (1 + critDamage / 100);
                type = "crithit";
            }

            e.setDamage(0);

            if (damager.getItemInHand() != null && Helper.hasCustomName(damager.getItemInHand()) && Helper.getCustomName(damager.getItemInHand()).equals(Utils.chat("&6Mage Beam"))) {
                final_damage = (int) ((final_damage * 0.4) + (po.getBaseIntelligence() * 0.9));
            }

            mo.setHealth((mo.getHealth() - final_damage));

            if (e.getEntityType() == EntityType.ENDER_DRAGON && SkyblockRemastered.so.isDragonFight()) {
                target.setHealth(((mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth() > 0 ? (mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth() : 0));
                SkyblockRemastered.dragonEvent.playerDamage.put(damager, SkyblockRemastered.dragonEvent.playerDamage.get(damager) + (double) final_damage);
                SkyblockRemastered.so.setLastDragonHit(damager);
            }

            if (mo.getHealth() <= 0 && !target.isDead())
                target.setHealth(0);

            Utils.damageIndicator(target, final_damage, type);
            if (!target.isDead())
                e.getEntity().getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));
        }
        // Mob to Player
        else if (e.getDamager() instanceof LivingEntity && !e.getDamager().getType().equals(EntityType.PLAYER) && e.getEntity().getType() == EntityType.PLAYER) {

            Player target = (Player) e.getEntity();
            LivingEntity damager = (LivingEntity) e.getDamager();

            PlayerObject po = PlayerManager.playerObjects.get(target);
            MobObject mo = MobManager.mobObjects.get(damager);

            int damage = mo.getDamage() > 0 ? mo.getDamage() : (int) e.getDamage();

            if (e.getDamager().getType() == EntityType.ENDER_DRAGON) {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> target.setVelocity(new Vector()), 1L);
                damage = (int) Helper.triangularDistribution(300, 500, 700);
                target.setVelocity(new Vector(0, 1.5, 0));
                target.playSound(target.getLocation(), Sound.EXPLODE, 100F, 1F);
                if (!damager.getCustomName().isEmpty())
                    target.sendMessage(Utils.chat("&5❂ &c" + damager.getCustomName().replaceAll("&[0-9a-zA-z]", "") + " &dused &eRush &don you for &c" + damage + " damage"));
            }

            e.setDamage(0);
            po.subtractHealth(damage);
            if (po.getHealth() <= 0 && !target.isDead()) Helper.deathHandler(plugin, target, "other", null);
            Utils.damageIndicator(target, mo.getDamage(), "normal");
        }
        // Player (Melee) to Player
        else if (e.getDamager().getType().equals(EntityType.PLAYER) && e.getEntityType() == EntityType.PLAYER) {

            if (!SkyblockRemastered.so.isPvp()) {
                e.setCancelled(true);
                return;
            }

            Player damager = (Player) e.getDamager();
            Player target = (Player) e.getEntity();

            PlayerObject po = PlayerManager.playerObjects.get(damager);
            PlayerObject po2 = PlayerManager.playerObjects.get(target);

            // Defining the player's stats
            int strength = po.getStrength();
            int weaponDamage = 0;
            int critDamage = po.getCritDamage();
            int critChance = po.getCritChance();
            int atkSpeed = po.getAtkSpeed();

            // Fetching the Weapon's Stats.
            if (damager.getItemInHand().hasItemMeta() && damager.getItemInHand().getItemMeta().hasLore()) {
                for (String lore : damager.getItemInHand().getItemMeta().getLore()) {
                    if (lore.startsWith(Utils.chat("&7Damage"))) weaponDamage += parseLore(lore);
                }
            }

            // Attack Speed
            attackSpeed(atkSpeed, target);

            // Adding Crit Chance
            critChance += po.getCombatLevel() * .05;

            // Base Formula
            int base_damage = (int) ((5 + weaponDamage + (Math.floor(strength) / 5)) * (1 + strength / 100));

            // Final Damage
            int final_damage = (int) (base_damage + (base_damage * .04));

            String type = "normal";

            if ((Math.random() * 100) < critChance) {
                final_damage = final_damage * (1 + critDamage / 100);
                type = "crithit";
            }

            e.setDamage(0);
            po2.setHealth((po2.getHealth() - final_damage));

            if (e.getEntityType() == EntityType.ENDER_DRAGON && SkyblockRemastered.so.isDragonFight()) {
                target.setHealth(((po2.getHealth() / (double) po2.getMaxHealth()) * target.getMaxHealth() > 0 ? (po2.getHealth() / (double) po2.getMaxHealth()) * target.getMaxHealth() : 0));
                SkyblockRemastered.dragonEvent.playerDamage.put(damager, SkyblockRemastered.dragonEvent.playerDamage.get(damager) + (double) final_damage);
                SkyblockRemastered.so.setLastDragonHit(damager);
            }

            if (po2.getHealth() <= 0 && !target.isDead())
                Helper.deathHandler(plugin, target, "mob", damager);

            Utils.damageIndicator(target, final_damage, type);
        }
        // Player (Projectile) to Player
        else if (e.getDamager().getType() == EntityType.ARROW && ((Arrow) e.getDamager()).getShooter() instanceof Player && e.getEntityType() == EntityType.PLAYER) {

            if (!SkyblockRemastered.so.isPvp()) {
                e.setCancelled(true);
                return;
            }

            Arrow arrow = (Arrow) e.getDamager();
            Player plr = (Player) arrow.getShooter();

            ItemStack bow = plr.getInventory().getItemInHand();

            Player damager = (Player) arrow.getShooter();
            Player target = (Player) e.getEntity();

            if (damager.equals(target)) {
                e.setCancelled(true);
                return;
            }

            PlayerObject po = PlayerManager.playerObjects.get(damager);
            PlayerObject po2 = PlayerManager.playerObjects.get(target);

            plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1000F, .8F);

            // Defining the player's stats
            int strength = po.getStrength();
            int weaponDamage = 0;
            int critDamage = po.getCritDamage();
            int critChance = po.getCritChance();
            int atkSpeed = po.getAtkSpeed();

            if (bow.hasItemMeta() && bow.getItemMeta().hasLore()) {

                // Fetching the Weapon's Stats.
                if (damager.getItemInHand().hasItemMeta() && damager.getItemInHand().getItemMeta().hasLore()) {
                    for (String lore : damager.getItemInHand().getItemMeta().getLore()) {
                        if (lore.startsWith(Utils.chat("&7Damage"))) weaponDamage += parseLore(lore);
                    }
                }
            }

            // Attack Speed
            attackSpeed(atkSpeed, target);

            // Adding Crit Chance
            critChance += po.getCombatLevel() * .05;

            // Base Formula
            int base_damage = (int) ((5 + weaponDamage + (Math.floor(strength) / 5)) * (1 + strength / 100));

            // Final Damage
            int final_damage = (int) (base_damage + (base_damage * .04));

            String type = "normal";

            if ((Math.random() * 100) < critChance) {
                final_damage = final_damage * (1 + critDamage / 100);
                type = "crithit";
            }

            e.setDamage(0);
            po2.setHealth((po2.getHealth() - final_damage));

            if (po2.getHealth() <= 0 && !target.isDead())
                Helper.deathHandler(plugin, target, "mob", damager);

            Utils.damageIndicator(target, final_damage, type);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {

        try {

            if ((e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.LAVA) && e.getEntityType() == EntityType.ENDERMAN) {
                List<Location> locs = new ArrayList<>(SkyblockRemastered.endSpawnpoints.keySet());
                Location loc = locs.get((int) Math.floor(Math.random() * SkyblockRemastered.endSpawnpoints.size()));
                e.getEntity().setFireTicks(0);
                e.getEntity().setFallDistance(100);
                e.getEntity().teleport(loc);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION && e.getEntityType() == EntityType.ENDER_DRAGON || e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
                return;
            } else if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                e.setDamage(0);
                return;
            }

            // Preventing damage from a players island.
            if (e.getEntity() instanceof Player) {
                Player plr = (Player) e.getEntity();
                if (plr.getWorld().getName().startsWith("playerislands/")) {
                    if (!plr.getWorld().getName().replace("playerislands/", "").equals(plr.getUniqueId().toString())) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }

            // Checking if the damaged Entity was an ARMOR_STAND, since .setInvulnerable doesn't work.
            if (e.getEntityType() == EntityType.ARMOR_STAND) e.setCancelled(true);

            // Checking if the entity is a creature and not an arrow or a dropped item
            if (e.getEntity() instanceof LivingEntity) {

                EntityDamageEvent.DamageCause[] uniqueCauses = {
                        EntityDamageEvent.DamageCause.POISON,
                        EntityDamageEvent.DamageCause.FIRE_TICK,
                        EntityDamageEvent.DamageCause.FIRE,
                        EntityDamageEvent.DamageCause.LAVA,
                        EntityDamageEvent.DamageCause.WITHER,
                        EntityDamageEvent.DamageCause.DROWNING,
                        EntityDamageEvent.DamageCause.BLOCK_EXPLOSION,
                        EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                };

                if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    e.setCancelled(true);
                    return;
                }

                if (e.getEntity().getType() == EntityType.PLAYER) {

                    Player target = (Player) e.getEntity();
                    PlayerObject po = PlayerManager.playerObjects.get(target);

                    String type = "normal";
                    int damage = 5;

                    if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
                    else if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                        if (e.getEntityType() == EntityType.PLAYER) Helper.deathHandler(plugin, target, "void", null);
                    }

                    String ca = "";
                    // Checks if the damage cause was unique to have its own custom color.
                    for (EntityDamageEvent.DamageCause cause : uniqueCauses) {
                        if (e.getCause() == cause) {
                            type = cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK || cause == EntityDamageEvent.DamageCause.LAVA ? "fire" : cause == EntityDamageEvent.DamageCause.DROWNING ? "water" : cause == EntityDamageEvent.DamageCause.WITHER ? "wither" : cause == EntityDamageEvent.DamageCause.POISON ? "poison" : "normal";
                            damage = cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ? 200 : cause == EntityDamageEvent.DamageCause.LAVA ? 20 : cause == EntityDamageEvent.DamageCause.WITHER ? 25 : 5;
                            ca = cause.name();
                            break;
                        }
                    }

                    // Shows the damage dealt using armor stands.
                    Utils.damageIndicator(e.getEntity(), damage, type);
                    e.setDamage(0);

                    po.subtractHealth(damage);
                    int health = po.getHealth();
                    if (health <= 0 && !target.isDead()) Helper.deathHandler(plugin, target, ca, null);
                } else {

                    LivingEntity target = (LivingEntity) e.getEntity();
                    MobObject mo = MobManager.mobObjects.get(target);
                    String type = "normal";
                    int damage = 5;

                    if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;

                    // Checks if the damage cause was unique to have its own custom color.
                    for (EntityDamageEvent.DamageCause cause : uniqueCauses) {
                        if (e.getCause() == cause) {
                            type = cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK || cause == EntityDamageEvent.DamageCause.LAVA ? "fire" : cause == EntityDamageEvent.DamageCause.DROWNING ? "water" : cause == EntityDamageEvent.DamageCause.WITHER ? "wither" : cause == EntityDamageEvent.DamageCause.POISON ? "poison" : "normal";
                            damage = cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ? 200 : cause == EntityDamageEvent.DamageCause.LAVA ? 20 : cause == EntityDamageEvent.DamageCause.WITHER ? 25 : 5;
                            break;
                        }
                    }

                    // Shows the damage dealt using armor stands.
                    Utils.damageIndicator(e.getEntity(), damage, type);
                    e.setDamage(0);

                    mo.subtractHealth(damage);
                    if (mo.getHealth() <= 0) ((LivingEntity) e.getEntity()).setHealth(0);

                    if (!target.isDead())
                        e.getEntity().getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));

                }
            }
        } catch (Exception ignored) {
        }

    }

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent e) {

        if (e.getEntityType() == EntityType.ENDER_DRAGON) {
            if (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.CUSTOM || e.getRegainReason() == EntityRegainHealthEvent.RegainReason.ENDER_CRYSTAL) {
                LivingEntity mob = (LivingEntity) e.getEntity();
                MobObject mo = MobManager.mobObjects.get(mob);
                e.setCancelled(true);
                mo.setHealth(Math.min(mo.getHealth() + 22500, mo.getMaxHealth()));
                mob.setHealth(((mo.getHealth() / (double) mo.getMaxHealth()) * mob.getMaxHealth()));
            }
        }

    }

    private void attackSpeed(int atkSpeed, LivingEntity e) {
        if (e.getType() == EntityType.ENDER_DRAGON) {
            e.setNoDamageTicks(0);
            e.setMaximumNoDamageTicks(0);
        } else if (atkSpeed > 0) {
            if (atkSpeed >= 100) atkSpeed = 100;
            atkSpeed = atkSpeed / 20;
            e.setMaximumNoDamageTicks(20 - atkSpeed);
            e.setNoDamageTicks(20 - atkSpeed);
        } else {
            e.setMaximumNoDamageTicks(20);
            e.setNoDamageTicks(20);
        }
    }

    private int parseLore(String lore) {
        try {
            return Integer.parseInt(lore.split(" ")[lore.split(" ").length - 1].replaceAll(",", "").replace(Utils.chat("&c+"), "").replace(Utils.chat("&a+"), "").replace("%", ""));
        } catch (Exception ignored) {
        }
        return 0;
    }
}
