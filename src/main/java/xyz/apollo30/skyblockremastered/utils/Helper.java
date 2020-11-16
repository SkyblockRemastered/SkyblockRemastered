package xyz.apollo30.skyblockremastered.utils;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import xyz.apollo30.skyblockremastered.managers.MobManager;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

public class Helper {

    public SkyblockRemastered plugin;

    public Helper(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static void damageMob(LivingEntity e, int d, String t) {
        MobObject mo = MobManager.mobObjects.get(e);
        if (mo == null) return;
        e.damage(0.001);
        mo.subtractHealth(d);
        if (mo.getHealth() <= 0 && !e.isDead())
            e.setHealth(0);
        Utils.damageIndicator(e, d, t);
        if (!e.isDead())
            e.getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));
    }

    public static boolean hasCustomName(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null && !item.getItemMeta().getDisplayName().isEmpty();
    }

    public static boolean hasLore(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasLore();
    }

    public static String getCustomName(ItemStack item) {
        return item.getItemMeta().getDisplayName();
    }

    public static List<String> getLore(ItemStack item) {
        return item.getItemMeta().getLore();
    }

    public static String getRank(Player plr, boolean a) {
        String prefix;
        if (plr.hasPermission("groups.admin")) prefix = a ? "&c[ADMIN] " : "&c";
        else if (plr.hasPermission("groups.mod")) prefix = a ? "&2[MOD] " : "&2";
        else if (plr.hasPermission("groups.helper")) prefix = a ? "&9[HELPER] " : "&9";
        else if (plr.hasPermission("groups.builder")) prefix = a ? "&d[BUILDER] " : "&d";
        else if (plr.hasPermission("groups.tester")) prefix = a ? "&6[BETA] " : "&6";
        else if (plr.hasPermission("groups.youtuber")) prefix = a ? "&c[&fYOUTUBE&c] " : "&c";
        else if (plr.hasPermission("groups.mvp++")) prefix = a ? "&e»» &6[MVP&0++&6] " : "&6";
        else if (plr.hasPermission("groups.mvp+")) prefix = a ? "&b[MVP&0+&b] " : "&b";
        else if (plr.hasPermission("groups.mvp")) prefix = a ? "&b[MVP] " : "&b";
        else if (plr.hasPermission("groups.vip+")) prefix = a ? "&a[VIP&6+&a] " : "&a";
        else if (plr.hasPermission("groups.vip")) prefix = a ? "&a[VIP] " : "&a";
        else prefix = "&7";
        return prefix + plr.getName();
    }

    public static String inventoryToString(Inventory inventory) {
        try {
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
            data.writeInt(inventory.getSize());
            data.writeObject(inventory.getName());
            for (int i = 0; i < inventory.getSize(); i++) {
                data.writeObject(inventory.getItem(i));
            }
            data.close();
            return Base64.getEncoder().encodeToString(str.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Inventory stringToInventory(String inventoryData) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(inventoryData));
            BukkitObjectInputStream data = new BukkitObjectInputStream(stream);
            Inventory inventory = Bukkit.createInventory(null, data.readInt(), data.readObject().toString());
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) data.readObject());
            }
            data.close();
            return inventory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack addEnchantGlow(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);

        return NMSUtil.setBoolean(item, "Unbreakable", true);
    }

    public static void addItem(Inventory inv, ItemStack item, String uuid) {
        inv.addItem(NMSUtil.addString(item, "UUID", uuid.length() > 0 ? uuid : UUID.randomUUID().toString()));
    }

    public static double triangularDistribution(double a, double b, double c) {
        double F = (c - a) / (b - a);
        double rand = Math.random();
        if (rand < F) return a + Math.sqrt(rand * (b - a) * (c - a));
        return b - Math.sqrt((1 - rand) * (b - a) * (b - c));
    }

    public static String getRarity(List<String> lores) {
        String rarity = "none";
        for (String lore : lores) {
            if (lore.length() == 0) break;
            if (lore.contains(Utils.chat("&f&lCOMMON"))) rarity = "common";
            else if (lore.contains(Utils.chat("&a&lUNCOMMON"))) rarity = "uncommon";
            else if (lore.contains(Utils.chat("&9&lRARE"))) rarity = "rare";
            else if (lore.contains(Utils.chat("&5&lEPIC"))) rarity = "epic";
            else if (lore.contains(Utils.chat("&6&lLEGENDARY"))) rarity = "legendary";
            else if (lore.contains(Utils.chat("&d&lMYTHIC"))) rarity = "mythic";
            else if (lore.contains(Utils.chat("&c&lSPECIAL"))) rarity = "special";
            else if (lore.contains(Utils.chat("&c&lVERY SPECIAL"))) rarity = "very special";
            else if (lore.contains(Utils.chat("&b&lCELESTIAL"))) rarity = "celestial";
        }
        return rarity;
    }

    public static void deathHandler(SkyblockRemastered plugin, Player plr, String type) {

        plr.setHealth(plr.getMaxHealth());
        PlayerObject po = PlayerManager.playerObjects.get(plr);
        po.resetHealth();

        for (PotionEffect potionEffect : plr.getActivePotionEffects()) {
            plr.removePotionEffect(potionEffect.getType());
        }

        Bukkit.getScheduler().runTask(plugin, () -> plr.setFireTicks(0));
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> plr.setVelocity(new Vector()), 1L);

        for (ItemStack content : plr.getInventory().getContents()) {
            if (content != null && content.hasItemMeta() && content.getItemMeta().getDisplayName() != null) {
                if (!plr.getWorld().getName().startsWith("playerislands/") && Utils.isInZone(plr.getLocation(), new Location(plr.getWorld(), -112, 255, -107), new Location(plr.getWorld(), 213, 29, 127))) {
                    if (content.getItemMeta().getDisplayName().contains("Remnant of the Eye")) {
                        if (plr.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                            plr.sendMessage(Utils.chat("&5Your Remnant of the Eye saved you from certain death! You were safely teleported back to spawn!"));
                            plr.teleport(new Location(plr.getWorld(), 173.5, 101, -3));
                        } else {
                            plr.sendMessage(Utils.chat("&5Your Remnant of the Eye saved you from certain death!"));
                        }
                        po.resetHealth();
                        plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1.25F);
                        plr.getInventory().removeItem(content);
                        return;
                    }
                }
            }
        }

        if (type.equals("void")) {
            if (!plr.getWorld().getName().startsWith("playerislands/")) {
                plr.playSound(plr.getLocation(), Sound.ANVIL_LAND, 1F, 10F);
                double purse = po.getPurse();
                po.setPurse(po.getPurse() / 2);
                plr.sendMessage(Utils.chat("&cYou died and lost " + String.format("%,.0f", purse / 2) + " coins!"));
            } else plr.sendMessage(Utils.chat("&cYou fell into the void"));
        } else {
            double purse = po.getPurse();
            po.setPurse(po.getPurse() / 2);

            Location loc = plr.getWorld().getSpawnLocation();
            loc.setPitch(0);
            loc.setYaw(90);
            plr.teleport(loc);

            plr.sendMessage(Utils.chat("&cYou died and lost " + String.format("%,.0f", purse / 2) + " coins!"));
            plr.playSound(plr.getLocation(), Sound.ANVIL_LAND, 1F, 10F);
        }
    }

    public static ItemStack setLeatherColor(ItemStack leatherArmor, int r, int b, int g) {
        LeatherArmorMeta meta = (LeatherArmorMeta) leatherArmor.getItemMeta();
        meta.setColor(Color.fromRGB(r, b, g));
        leatherArmor.setItemMeta(meta);
        return leatherArmor;
    }

    public static boolean consumeItem(Player plr, int count, ItemStack item) {
        if ((plr.getInventory().firstEmpty() == -1)) {
            plr.sendMessage(Utils.chat("&cYour inventory is full!"));
            return true;
        } else {
            Map<Integer, ? extends ItemStack> filteredItem = (plr.getInventory().all(item.getType()) != null ? plr.getInventory().all(item.getType()) : plr.getInventory().all(item));

            int found = 0;
            for (ItemStack stack : filteredItem.values())
                found += stack.getAmount();
            Utils.broadCast(count + "");
            Utils.broadCast(found + "");
            if (count > found)
                return false;

            for (Integer index : filteredItem.keySet()) {
                ItemStack stack = filteredItem.get(index);

                int removed = Math.min(count, stack.getAmount());
                count -= removed;

                if (stack.getAmount() == removed)
                    plr.getInventory().setItem(index, null);
                else
                    stack.setAmount(stack.getAmount() - removed);

                if (count <= 0)
                    break;
            }

            plr.updateInventory();
            return true;
        }
    }

}
