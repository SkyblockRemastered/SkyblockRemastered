package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class DamageEvents implements Listener {

    private final SkyblockRemastered plugin;

    public DamageEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        if (e.getDamager().getType() == EntityType.PLAYER && e.getEntityType() == EntityType.ENDER_CRYSTAL) {
            for (Player plr : e.getEntity().getWorld().getPlayers()) {
                plr.sendMessage(Utils.chat("&d" + Helper.getRank((Player) e.getDamager()) + "&d broke an &5End Crystal&d!"));
            }
            e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), Utils.addLore(new ItemStack(Material.QUARTZ), "&5Crystal Fragment", "&eRight-click to view recipes!", "", "&5&lEPIC"));
        } else if (e.getDamager().getType() == EntityType.ARROW) {
            if (e.getEntityType() == EntityType.ENDER_CRYSTAL) {
                Arrow arrow = (Arrow) e.getDamager();
                for (Player player : e.getEntity().getWorld().getPlayers()) {
                    player.sendMessage(Utils.chat("&d" + Helper.getRank((Player) arrow.getShooter()) + "&d broke an &5End Crystal&d!"));
                }
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), Utils.addLore(new ItemStack(Material.QUARTZ), "&5Crystal Fragment", "&eRight-click to view recipes!", "", "&5&lEPIC"));
            }
        }

        // Player (Projectile) to Mob
        if (e.getDamager().getType() == EntityType.ARROW && e.getEntity() instanceof LivingEntity) {

            Arrow arrow = (Arrow) e.getDamager();
            Player plr = (Player) arrow.getShooter();

            ItemStack bow = plr.getInventory().getItemInHand();

            Player damager = (Player) arrow.getShooter();
            LivingEntity target = (LivingEntity) e.getEntity();

            PlayerObject po = plugin.playerManager.playerObjects.get(damager);
            MobObject mo = plugin.mobManager.mobObjects.get(target);

            plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1000F, .8F);

            // Defining the player's stats
            int strength = po.getStrength();
            int weaponDamage = 0;
            int critDamage = po.getCritDamage();
            int critChance = po.getCritChance();
            int atkSpeed = po.getAtkSpeed();
            int intelligence = po.getIntelligence();

            if (bow.hasItemMeta() && bow.getItemMeta().hasLore()) {

                // Fetching the Weapon's Stats.
                if (damager.getItemInHand().hasItemMeta() && damager.getItemInHand().getItemMeta().hasLore()) {
                    for (String lore : damager.getItemInHand().getItemMeta().getLore()) {
                        if (lore.startsWith(Utils.chat("&7Damage"))) weaponDamage += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Strength"))) strength += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Chance"))) critChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Damage"))) critDamage += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Bonus Attack Speed")) || lore.startsWith(Utils.chat("&7Attack Speed")))
                            atkSpeed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Intelligence"))) intelligence += parseLore(lore);
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

            if (e.getEntityType() == EntityType.ENDER_DRAGON) {
                // Utils.broadCast("[DEBUG] " + ((mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth()));
                target.setHealth(((mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth() > 0 ? (mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth() : 0));
                plugin.dragonEvent.playerDamage.put(damager, plugin.dragonEvent.playerDamage.get(damager) + (double) final_damage);
                plugin.so.setLastDragonHit(damager);
            }

            if (mo.getHealth() <= 0 && !target.isDead())
                target.setHealth(0);

            Utils.damageIndicator(target, final_damage, type, plugin);
            if (!target.isDead())
                e.getEntity().getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));
        }

        // Player (Melee) to Mob
        else if (e.getDamager().getType() == EntityType.PLAYER && e.getEntity() instanceof LivingEntity) {

            Player damager = (Player) e.getDamager();
            LivingEntity target = (LivingEntity) e.getEntity();

            PlayerObject po = plugin.playerManager.playerObjects.get(damager);
            MobObject mo = plugin.mobManager.mobObjects.get(target);

            // Defining the player's stats
            int strength = po.getStrength();
            int weaponDamage = 0;
            int critDamage = po.getCritDamage();
            int critChance = po.getCritChance();
            int atkSpeed = po.getAtkSpeed();
            int intelligence = po.getIntelligence();

            // Fetching the Weapon's Stats.
            if (damager.getItemInHand().hasItemMeta() && damager.getItemInHand().getItemMeta().hasLore()) {
                for (String lore : damager.getItemInHand().getItemMeta().getLore()) {
                    if (lore.startsWith(Utils.chat("&7Damage"))) weaponDamage += parseLore(lore);
                    if (lore.startsWith(Utils.chat("&7Strength"))) strength += parseLore(lore);
                    if (lore.startsWith(Utils.chat("&7Crit Chance"))) critChance += parseLore(lore);
                    if (lore.startsWith(Utils.chat("&7Crit Damage"))) critDamage += parseLore(lore);
                    if (lore.startsWith(Utils.chat("&7Bonus Attack Speed")) || lore.startsWith(Utils.chat("&7Attack Speed")))
                        atkSpeed += parseLore(lore);
                    if (lore.startsWith(Utils.chat("&7Intelligence"))) intelligence += parseLore(lore);
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

            if (e.getEntityType() == EntityType.ENDER_DRAGON && plugin.so.isDragonFight()) {
                Utils.broadCast("[DEBUG] " + ((mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth()));
                target.setHealth(((mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth() > 0 ? (mo.getHealth() / (double) mo.getMaxHealth()) * target.getMaxHealth() : 0));
                plugin.dragonEvent.playerDamage.put(damager, plugin.dragonEvent.playerDamage.get(damager) + (double) final_damage);
                plugin.so.setLastDragonHit(damager);
            }

            if (mo.getHealth() <= 0 && !target.isDead())
                target.setHealth(0);

            Utils.damageIndicator(target, final_damage, type, plugin);
            if (!target.isDead())
                e.getEntity().getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));
        }

        // Mob to Player
        else if (e.getDamager() instanceof LivingEntity && e.getEntity().getType() == EntityType.PLAYER) {

            Player target = (Player) e.getEntity();
            LivingEntity damager = (LivingEntity) e.getDamager();

            PlayerObject po = plugin.playerManager.playerObjects.get(target);
            MobObject mo = plugin.mobManager.mobObjects.get(damager);

            int damage = mo.getDamage() > 0 ? mo.getDamage() : (int) e.getDamage();

            e.setDamage(0);
            po.setHealth((po.getHealth() - damage));
            if (po.getHealth() <= 0 && !target.isDead()) {
                target.setHealth(0);
                return;
            }
            Utils.damageIndicator(target, mo.getDamage(), "normal", plugin);
            if (!target.isDead())
                e.getEntity().getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));
        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {

        try {

            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION && e.getEntityType() == EntityType.ENDER_DRAGON) {
                e.setCancelled(true);
                return;
            }

            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
                return;
            }

            // Preventing damage from a players island.
            if (e.getEntity() instanceof Player) {
                Player plr = (Player) e.getEntity();
                if (plr.getWorld().getName().startsWith("islands/")) {
                    if (!plr.getWorld().getName().replace("islands/", "").equals(plr.getUniqueId().toString())) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }

            // Checking if the damaged Entity was an ARMOR_STAND, since .setInvulnerable doesn't work.
            if (e.getEntityType() == EntityType.ARMOR_STAND) e.setCancelled(true);

            // Checking if the entity is a creature and not an arrow or a dropped item
            if (e.getEntity() instanceof LivingEntity) {

                if (e.getEntity().getType() == EntityType.PLAYER) {

                    Player target = (Player) e.getEntity();
                    PlayerObject po = plugin.playerManager.playerObjects.get(target);

                    String type = "normal";
                    int damage = 5;

                    if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
                    else if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                        if (e.getEntityType() == EntityType.PLAYER) po.setHealth(0);
                    }

                    EntityDamageEvent.DamageCause[] uniqueCauses = {
                            EntityDamageEvent.DamageCause.POISON,
                            EntityDamageEvent.DamageCause.FIRE_TICK,
                            EntityDamageEvent.DamageCause.FIRE,
                            EntityDamageEvent.DamageCause.LAVA,
                            EntityDamageEvent.DamageCause.WITHER,
                            EntityDamageEvent.DamageCause.DROWNING
                    };

                    // Checks if the damage cause was unique to have its own custom color.
                    for (EntityDamageEvent.DamageCause cause : uniqueCauses) {
                        if (e.getCause() == cause) {
                            type = cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK || cause == EntityDamageEvent.DamageCause.LAVA ? "fire" : cause == EntityDamageEvent.DamageCause.DROWNING ? "water" : cause == EntityDamageEvent.DamageCause.WITHER ? "wither" : cause == EntityDamageEvent.DamageCause.POISON ? "poison" : "normal";
                            damage = cause == EntityDamageEvent.DamageCause.LAVA ? 20 : 5;
                            break;
                        }
                    }

                    // Shows the damage dealt using armor stands.
                    Utils.damageIndicator(e.getEntity(), damage, type, plugin);
                    e.setDamage(0);


                    po.subtractHealth(damage);
                    int health = po.getHealth();
                    if (health <= 0) ((LivingEntity) e.getEntity()).setHealth(0);
                } else {

                    LivingEntity target = (LivingEntity) e.getEntity();
                    MobObject mo = plugin.mobManager.mobObjects.get(target);
                    String type = "normal";
                    int damage = 5;

                    if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;

                    EntityDamageEvent.DamageCause[] uniqueCauses = {
                            EntityDamageEvent.DamageCause.POISON,
                            EntityDamageEvent.DamageCause.FIRE_TICK,
                            EntityDamageEvent.DamageCause.FIRE,
                            EntityDamageEvent.DamageCause.LAVA,
                            EntityDamageEvent.DamageCause.WITHER,
                            EntityDamageEvent.DamageCause.DROWNING
                    };

                    // Checks if the damage cause was unique to have its own custom color.
                    for (EntityDamageEvent.DamageCause cause : uniqueCauses) {
                        if (e.getCause() == cause) {
                            type = cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK || cause == EntityDamageEvent.DamageCause.LAVA ? "fire" : cause == EntityDamageEvent.DamageCause.DROWNING ? "water" : cause == EntityDamageEvent.DamageCause.WITHER ? "wither" : cause == EntityDamageEvent.DamageCause.POISON ? "poison" : "normal";
                            damage = cause == EntityDamageEvent.DamageCause.LAVA ? 20 : 5;
                            break;
                        }
                    }

                    // Shows the damage dealt using armor stands.
                    Utils.damageIndicator(e.getEntity(), damage, type, plugin);
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
                MobObject mo = plugin.mobManager.mobObjects.get(mob);

                e.setCancelled(true);

                mo.setHealth(Math.min(mo.getHealth() + 22500, mo.getMaxHealth()));
                mob.setHealth(((mo.getHealth() / (double) mo.getMaxHealth()) * mob.getMaxHealth()));
            }
        }

    }

    private void attackSpeed(int atkSpeed, LivingEntity e) {
        if (atkSpeed > 0) {
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
            return Integer.parseInt(lore.split(" ")[lore.split(" ").length - 1].replace(Utils.chat("&c+"), "").replace(Utils.chat("&a+"), "").replace("%", ""));
        } catch (Exception ignored) {
        }
        return 0;
    }
}
