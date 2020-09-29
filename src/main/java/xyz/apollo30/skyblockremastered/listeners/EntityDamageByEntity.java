package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.managers.MobManager;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EntityDamageByEntity implements Listener {

    private final SkyblockRemastered plugin;

    public EntityDamageByEntity(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        try {

            // Cancels the damage on armor stands
            if (e.getEntityType() == EntityType.ARMOR_STAND) e.setCancelled(true);

            // Checks if the damaged entity is living.
            if (e.getEntity() instanceof LivingEntity) {

                // Player hits monster.
                if (e.getEntityType() != EntityType.PLAYER) {

                    // Define the player (Shooter)
                    Player plr = (Player) e.getDamager();
                    LivingEntity target = (Player) e.getDamager();
                    PlayerObject po = plugin.playerManager.getPlayerData(plr);
                    MobObject mo = plugin.mobManager.mobObjects.get(e.getEntity());

                    int strength = po.getStrength();
                    int combat = po.getCombatLevel();
                    int critdamage = po.getCrit_damage();
                    int critchance = po.getCrit_chance();
                    int atk_speed = 0;
                    int weapon_damage = 0;
                    int damage;
                    String type = "";

                    ItemStack sword = plr.getItemInHand();
                    if (sword != null && sword.hasItemMeta() && sword.getItemMeta().hasLore()) {
                        for (String lore : sword.getItemMeta().getLore()) {
                            if (lore.startsWith(Utils.chat("&7Damage: "))) {
                                lore = lore.replaceAll(Utils.chat("&7Damage: &c+"), "").replaceAll(",", "");
                                weapon_damage = Integer.parseInt(lore);
                            } else if (lore.startsWith(Utils.chat("&7Strength: "))) {
                                lore = lore.replaceAll(Utils.chat("&7Strength: &c+"), "").replaceAll(",", "");
                                strength = Integer.parseInt(lore);
                            } else if (lore.startsWith(Utils.chat("&7Crit Chance: "))) {
                                lore = lore.replaceAll(Utils.chat("&7Crit Chance: &c+"), "").replaceAll("%", "").replaceAll(",", "");
                                critchance += Integer.parseInt(lore);
                            } else if (lore.startsWith(Utils.chat("&7Crit Damage: "))) {
                                lore = lore.replaceAll(Utils.chat("&7Crit Damage: &c+"), "").replaceAll("%", "").replaceAll(",", "");
                                critdamage += Integer.parseInt(lore);
                            } else if (lore.startsWith(Utils.chat("&7Bonus Attack Speed: "))) {
                                lore = lore.replaceAll(Utils.chat("&7Bonus Attack Speed: &c+"), "").replaceAll("%", "").replaceAll(",", "");
                                atk_speed += Integer.parseInt(lore);
                            }
                        }
                    }

                    // Base Formula
                    damage = (int) ((5 + weapon_damage + (Math.floor(strength) / 5)) * (1 + strength / 100));

                    // Enchantments

                    // Attacc Speed
                    if (atk_speed > 0) {
                        if (atk_speed >= 100) atk_speed = 100;
                        atk_speed = atk_speed / 5;
                        ((LivingEntity) e.getEntity()).setMaximumNoDamageTicks(20 - atk_speed);
                        ((LivingEntity) e.getEntity()).setNoDamageTicks(20 - atk_speed);
                    } else {
                        ((LivingEntity) e.getEntity()).setMaximumNoDamageTicks(20);
                        ((LivingEntity) e.getEntity()).setNoDamageTicks(20);
                    }

                    // Combat Damage Addition
                    damage += (int) (combat * .04) * damage;

                    // Combat Crit Chance
                    critchance = (int) (critchance + (combat * .05));
                    if ((Math.random() * 100) < critchance) {
                        damage = damage * (1 + critdamage / 100);
                        type = "crithit";
                    } else type = "normal";

                    // Defense Calculation
                    float final_damage = (float) damage;
                    float defense = (float) po.getDefense();
                    final_damage = final_damage - (final_damage * (1 - (defense / (defense + 100))));

                    Utils.damageIndicator(e.getEntity(), (int) final_damage, type, plugin);
                    e.setDamage(0);

                    mo.subtractHealth((int) final_damage);
                    if (mo.getHealth() <= 0 && !e.getEntity().isDead()) ((LivingEntity) e.getEntity()).setHealth(0);
                    e.getEntity().getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));

                    // Slayers for the player.
                    if (!po.getSlayer().equals("none")){
                        // this is going to be worked on tomorrow as well so don't mind it
                    }
                } else {
                    // Mob hits player.
                    LivingEntity killer = (LivingEntity) e.getDamager();
                    Player target = (Player) e.getEntity();
                    MobObject mo = plugin.mobManager.mobObjects.get(killer);
                    PlayerObject po = plugin.playerManager.getPlayerData(target);

                    EntityDamageEvent ev = new EntityDamageEvent(target, e.getCause(), 1);
                    target.setLastDamageCause(ev);

                    // Defense Calculation
                    float damage = mo.getDamage();
                    float defense = (float) po.getDefense();
                    damage = damage - (damage * (1 - (defense / (defense + 100))));

                    po.subtractHealth((int) damage);
                    Utils.damageIndicator(target, (int) damage, "normal", plugin);
                    e.setDamage(0);
                    // Utils.broadCast(Integer.toString(player_data.getHealth()));
                    if (po.getHealth() <= 0 && !e.getEntity().isDead()) {
                        target.setHealth(0);
                    }
                }
            }

        } catch (Exception err) {

        }
    }

}
