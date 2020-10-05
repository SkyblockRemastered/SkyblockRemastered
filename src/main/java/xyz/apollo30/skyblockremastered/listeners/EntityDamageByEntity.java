package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class EntityDamageByEntity implements Listener {

    private final SkyblockRemastered plugin;

    public EntityDamageByEntity(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof LivingEntity) {

            // PVE
            if (e.getDamager().getType() == EntityType.PLAYER && e.getEntityType() != EntityType.PLAYER) {
                damageDealerToEntity(e, (LivingEntity) e.getEntity(), (Player) e.getDamager());
            } else if (e.getDamager().getType() != EntityType.PLAYER && e.getEntityType() == EntityType.PLAYER) {
                damageDealerToPlayer(e, (LivingEntity) e.getEntity(), (Player) e.getDamager());
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

    private void damageDealerToEntity(EntityDamageByEntityEvent e, LivingEntity target, Player damager) {

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
        if (mo.getHealth() <= 0 && !target.isDead()) target.setHealth(0);
        Utils.damageIndicator(target, final_damage, type, plugin);
        e.getEntity().getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));
    }

    private void damageDealerToPlayer(EntityDamageByEntityEvent e, LivingEntity damager, Player target) {

        PlayerObject po = plugin.playerManager.playerObjects.get(target);
        MobObject mo = plugin.mobManager.mobObjects.get(damager);

        e.setDamage(0);
        po.setHealth((po.getHealth() - mo.getDamage()));
        if (po.getHealth() <= 0 && !target.isDead()) target.setHealth(0);
        Utils.damageIndicator(target, mo.getDamage(), "normal", plugin);
        e.getEntity().getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));
    }
}
