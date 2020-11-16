package xyz.apollo30.skyblockremastered.tasks;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.items.Miscs;
import xyz.apollo30.skyblockremastered.listeners.ItemEvents;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.PacketUtils;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.*;

public class ConstantTask extends BukkitRunnable {

    private int currentTickRotCount = 0;
    public static HashMap<ArmorStand, ArrayList> orbs = new HashMap<>();
    public static HashMap<ArmorStand, Boolean> motion = new HashMap<>();

    public ConstantTask() {
    }

    @Override
    public void run() {

        /*
         * Checking for orbs to spin :o
         */
        for (Map.Entry<Player, ArmorStand> entry : ItemEvents.orbs.entrySet()) {

            ArmorStand orb = entry.getValue();

            double originalLoc = (double) orbs.get(orb).get(1);

            motion.putIfAbsent(orb, false);
            boolean motionUpDown = motion.get(orb);
            if (!motionUpDown) {
                orb.setVelocity(new Vector(0, .05, 0));
                if ((orb.getLocation().getY() - originalLoc) >= 1) {
                    motion.remove(orb);
                    motion.put(orb, true);
                }
            } else {
                orb.setVelocity(new Vector(0, -.05, 0));
                if ((orb.getLocation().getY() - originalLoc) <= -1) {
                    motion.remove(orb);
                    motion.put(orb, false);
                }
            }

            Location newLoc = orb.getLocation();

            currentTickRotCount = currentTickRotCount + 1;
            if (currentTickRotCount == 8) currentTickRotCount = 0;

            int yaw = currentTickRotCount * 45;
            yaw = this.normalizeYaw(yaw);

            newLoc.setYaw(yaw);
            orb.teleport(newLoc);

            if (currentTickRotCount == 2 || currentTickRotCount == 6)
                spawnParticles(orb.getLocation().add(0, 1, 0), 255, 0, 0);
        }

        /*
         * Checking for frozen players :)
         */
        for (Player plr : SkyblockRemastered.frozenPlayers.keySet()) {
            plr.teleport(SkyblockRemastered.frozenPlayers.get(plr));
            plr.sendTitle(Utils.chat("&bYou have been frozen!"), "");
        }

        for (Player plr : Bukkit.getOnlinePlayers()) {

            EntityEquipment equipment = plr.getEquipment();
            ItemStack helmet = equipment.getHelmet();
            ItemStack chestplate = equipment.getChestplate();
            ItemStack leggings = equipment.getLeggings();
            ItemStack boots = equipment.getBoots();
            ItemStack heldItem = plr.getItemInHand();

            /*
             * Just to update all concurrent items in a player's inventory to check for missing rarities,
             * Or maybe to remove some attributes/flags.
             */
            for (ItemStack item : plr.getInventory().getContents()) {
                if (item == null) break;
                if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                    if (Helper.getRarity(item.getItemMeta().getLore()).equals("none")) {
                        // Utils.broadCast("");
                    }
                }
            }

            /*
             * Checking if the player is wearing Taran Boots or Spider Boots
             * If true, we enable fly and ItemAbilityEvents can handle the rest.
             */
            if (plr.getEquipment().getBoots() != null && plr.getEquipment().getBoots().hasItemMeta() && !plr.getEquipment().getBoots().getItemMeta().getDisplayName().isEmpty()) {
                if (plr.getEquipment().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&5Tarantula Boots"))) {
                    if (!plr.getGameMode().equals(GameMode.CREATIVE) && !plr.getGameMode().equals(GameMode.SPECTATOR)) {
                        if (plr.isFlying()) {
                            plr.setFlying(false);
                        }
                        plr.setAllowFlight(true);
                    }
                }
            } else if (!plr.getGameMode().equals(GameMode.CREATIVE) && !plr.getGameMode().equals(GameMode.SPECTATOR)) {
                plr.setFlying(false);
                plr.setAllowFlight(false);
            }

            /*
             * Infinite Quiver (Replaces Skyblock Menu)
             * This is temporary.
             */
            if (heldItem != null && heldItem.getType() == Material.BOW) {
                PlayerObject po = SkyblockRemastered.playerManager.getPlayerData(plr);
                int arrows = -1;

                if (po.getQuiverBag() == null) return;
                Inventory inv = Helper.stringToInventory(po.getQuiverBag());

                assert inv != null;
                for (ItemStack item : inv.getContents()) {
                    if (item != null) {
                        if (item.getType() == Material.ARROW) arrows += item.getAmount();
                        else if (item.getType() == Material.PRISMARINE_SHARD) arrows += item.getAmount();
                        else if (item.getType() == Material.MAGMA_CREAM) arrows += item.getAmount();
                    }
                }

                if (arrows > 0)
                    GUIHelper.addEnchantedItem(plr.getInventory(), 262, 0, Math.min(arrows, 64), 9, "&8Quiver Arrow", "&7This item is in your", "&7inventory because you are", "&7holding your bow currently.", " ", "&7Switch your held item to", "&7see the item that was here", "&7before.");
            } else {
                ItemStack nether_star = Miscs.SKYBLOCK_MENU;
                plr.getInventory().setItem(8, nether_star);
            }

            PlayerObject po = PlayerManager.playerObjects.get(plr);
            if (po == null) return;

            /*
             * Checking if the player's inventory is full
             * If so, it notifies them with a 30 second cooldown.
             */
            if ((plr.getInventory().firstEmpty() == -1)) {
                if (po.getLastInvFullNotification() > new Date().getTime()) return;
                plr.sendMessage(Utils.chat("&cYour inventory is full!"));
                PacketUtils.sendTitle(plr, 0, 180, 40, Utils.chat("&cInventory Full"), "");
                plr.playSound(plr.getLocation(), Sound.CHEST_OPEN, 1000L, 5L);
                po.setLastInvFullNotification(new Date().getTime() + 30000);
            }

            int health = 0;
            int defense = 0;
            int strength = 0;
            int speed = 0;
            int critChance = 0;
            int critDamage = 0;
            int atkSpeed = 0;
            int intelligence = 0;
            int seaCreatureChance = 0;
            int magicFind = 0;
            int petLuck = 0;
            int trueDamage = 0;
            int trueDefense = 0;

            if (helmet != null && po.getHelmet() != helmet) {
                po.setHelmet(helmet);
                if (Helper.hasLore(helmet)) {
                    for (String lore : helmet.getItemMeta().getLore()) {
                        if (lore.startsWith(Utils.chat("&7Health"))) health += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Defense"))) defense += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Strength"))) strength += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Speed"))) speed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Chance"))) critChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Damage"))) critDamage += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Bonus Attack Speed")) || lore.startsWith(Utils.chat("&7Attack Speed")))
                            atkSpeed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Intelligence"))) intelligence += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Sea Creature Chance"))) seaCreatureChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Magic Find"))) magicFind += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Pet Luck"))) petLuck += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7True Defense"))) trueDefense += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7True Damage"))) trueDamage += parseLore(lore);
                    }
                }
            }

            if (chestplate != null && po.getChestplate() != chestplate) {
                po.setChestplate(chestplate);
                if (Helper.hasLore(chestplate)) {
                    for (String lore : chestplate.getItemMeta().getLore()) {
                        if (lore.startsWith(Utils.chat("&7Health"))) health += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Defense"))) defense += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Strength"))) strength += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Speed"))) speed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Chance"))) critChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Damage"))) critDamage += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Bonus Attack Speed")) || lore.startsWith(Utils.chat("&7Attack Speed")))
                            atkSpeed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Intelligence"))) intelligence += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Sea Creature Chance"))) seaCreatureChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Magic Find"))) magicFind += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Pet Luck"))) petLuck += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7True Defense"))) trueDefense += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7True Damage"))) trueDamage += parseLore(lore);
                    }
                }
            }

            if (leggings != null && po.getLeggings() != leggings) {
                po.setLeggings(leggings);
                if (Helper.hasLore(leggings)) {
                    for (String lore : leggings.getItemMeta().getLore()) {
                        if (lore.startsWith(Utils.chat("&7Health"))) health += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Defense"))) defense += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Strength"))) strength += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Speed"))) speed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Chance"))) critChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Damage"))) critDamage += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Bonus Attack Speed")) || lore.startsWith(Utils.chat("&7Attack Speed")))
                            atkSpeed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Intelligence"))) intelligence += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Sea Creature Chance"))) seaCreatureChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Magic Find"))) magicFind += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Pet Luck"))) petLuck += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7True Defense"))) trueDefense += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7True Damage"))) trueDamage += parseLore(lore);
                    }
                }
            }

            if (boots != null && po.getBoots() != boots) {
                po.setBoots(boots);
                if (Helper.hasLore(boots)) {
                    for (String lore : boots.getItemMeta().getLore()) {
                        if (lore.startsWith(Utils.chat("&7Health"))) health += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Defense"))) defense += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Strength"))) strength += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Speed"))) speed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Chance"))) critChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Damage"))) critDamage += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Bonus Attack Speed")) || lore.startsWith(Utils.chat("&7Attack Speed")))
                            atkSpeed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Intelligence"))) intelligence += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Sea Creature Chance"))) seaCreatureChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Magic Find"))) magicFind += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Pet Luck"))) petLuck += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7True Defense"))) trueDefense += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7True Damage"))) trueDamage += parseLore(lore);
                    }
                }
            }

            if (po.getHeldItem() != heldItem) {
                po.setHeldItem(heldItem);
                if (heldItem != null && heldItem.hasItemMeta() && heldItem.getItemMeta().getLore().size() > 0) {
                    for (String lore : heldItem.getItemMeta().getLore()) {
                        if (lore.startsWith(Utils.chat("&7Health"))) health += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Defense"))) defense += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Strength"))) strength += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Speed"))) speed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Chance"))) critChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Crit Damage"))) critDamage += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Bonus Attack Speed")) || lore.startsWith(Utils.chat("&7Attack Speed")))
                            atkSpeed += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Intelligence"))) intelligence += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Sea Creature Chance"))) seaCreatureChance += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Magic Find"))) magicFind += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7Pet Luck"))) petLuck += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7True Defense"))) trueDefense += parseLore(lore);
                        if (lore.startsWith(Utils.chat("&7True Damage"))) trueDamage += parseLore(lore);
                    }
                }
            }

            /*
             * Young Armor Full Set Ability
             * When a full set is worn, the player gains +70 speed while above 50% health.
             */
            if (plr.getEquipment().getBoots() != null && plr.getEquipment().getBoots().hasItemMeta() && !plr.getEquipment().getBoots().getItemMeta().getDisplayName().isEmpty() && plr.getEquipment().getBoots().getItemMeta().getDisplayName().replaceAll("&[0-9a-zA-Z]", "").contains(Utils.chat("Young Dragon Boots"))) {
                if (plr.getEquipment().getChestplate() != null && plr.getEquipment().getChestplate().hasItemMeta() && !plr.getEquipment().getChestplate().getItemMeta().getDisplayName().isEmpty() && plr.getEquipment().getChestplate().getItemMeta().getDisplayName().replaceAll("&[0-9a-zA-Z]", "").contains(Utils.chat("Young Dragon Chestplate"))) {
                    if (plr.getEquipment().getLeggings() != null && plr.getEquipment().getLeggings().hasItemMeta() && !plr.getEquipment().getLeggings().getItemMeta().getDisplayName().isEmpty() && plr.getEquipment().getLeggings().getItemMeta().getDisplayName().replaceAll("&[0-9a-zA-Z]", "").contains(Utils.chat("Young Dragon Leggings"))) {
                        if (helmet != null && helmet.hasItemMeta() && !helmet.getItemMeta().getDisplayName().isEmpty() && helmet.getItemMeta().getDisplayName().replaceAll("&[0-9a-zA-Z]", "").contains(Utils.chat("Young Dragon Helmet"))) {
                            if ((po.getHealth() / 2) > po.getMaxHealth()) {
                                speed += 70;
                            }
                        }
                    }
                }
            }

            /*
             * Protector Dragon Helmet
             * Increases the defense of each armor piece by 1% defense for each missing percent of hp.
             */


            /*
             * Tarantula Helmet Ability
             * 1% Crit Damage per 10 strength
             */
            if (Helper.hasCustomName(helmet) && Helper.getCustomName(helmet).contains(Utils.chat("Tarantula Helmet"))) {
                critDamage += (strength / 10);
            }

            /*
             * Superior Armor Full Set Ability
             * When a full set is worn, the player's stats are increased by 5%
             */
            if (plr.getEquipment().getBoots() != null && plr.getEquipment().getBoots().hasItemMeta() && !plr.getEquipment().getBoots().getItemMeta().getDisplayName().isEmpty() && plr.getEquipment().getBoots().getItemMeta().getDisplayName().replaceAll("&[0-9a-zA-Z]", "").contains(Utils.chat("Superior Dragon Boots"))) {
                if (plr.getEquipment().getChestplate() != null && plr.getEquipment().getChestplate().hasItemMeta() && !plr.getEquipment().getChestplate().getItemMeta().getDisplayName().isEmpty() && plr.getEquipment().getChestplate().getItemMeta().getDisplayName().replaceAll("&[0-9a-zA-Z]", "").contains(Utils.chat("Superior Dragon Chestplate"))) {
                    if (plr.getEquipment().getLeggings() != null && plr.getEquipment().getLeggings().hasItemMeta() && !plr.getEquipment().getLeggings().getItemMeta().getDisplayName().isEmpty() && plr.getEquipment().getLeggings().getItemMeta().getDisplayName().replaceAll("&[0-9a-zA-Z]", "").contains(Utils.chat("Superior Dragon Leggings"))) {
                        if (helmet != null && helmet.hasItemMeta() && !helmet.getItemMeta().getDisplayName().isEmpty() && helmet.getItemMeta().getDisplayName().replaceAll("&[0-9a-zA-Z]", "").contains(Utils.chat("Superior Dragon Helmet"))) {
                            health += (health * 0.05);
                            defense += (defense * 0.05);
                            strength += (strength * 0.05);
                            speed += (speed * 0.05);
                            critChance += (critChance * 0.05);
                            critDamage += (critDamage * 0.05);
                            atkSpeed += (atkSpeed * 0.05);
                            intelligence += (intelligence * 0.05);
                            seaCreatureChance += (seaCreatureChance * 0.05);
                            magicFind += (magicFind * 0.05);
                            petLuck += (petLuck * 0.05);
                            trueDefense += (trueDefense * 0.05);
                            trueDamage += (trueDamage * 0.05);
                        }
                    }
                }
            }

            // Setting Item Stats
            po.setItemHealth(health);
            po.setItemDefense(defense);
            po.setItemStrength(strength);
            po.setItemSpeed(speed);
            po.setItemCritChance(critChance);
            po.setItemCritDamage(critDamage);
            po.setItemAtkSpeed(atkSpeed);
            po.setItemIntelligence(intelligence);
            po.setItemSeaCreatureChance(seaCreatureChance);
            po.setItemMagicFind(magicFind);
            po.setItemPetLuck(petLuck);
            po.setItemTrueDamage(trueDamage);
            po.setItemTrueDefense(trueDefense);

            // Stats
            po.setDefense(po.getBaseDefense() + po.getItemDefense());
            po.setStrength(po.getBaseStrength() + po.getItemStrength());
            po.setSpeed(po.getBaseSpeed() + po.getItemSpeed());
            po.setCritChance(po.getBaseCritChance() + po.getItemCritChance());
            po.setCritDamage(po.getBaseCritDamage() + po.getItemCritDamage());
            po.setAtkSpeed(po.getBaseAtkSpeed() + po.getItemAtkSpeed());
            po.setSeaCreatureChance(po.getBaseSeaCreatureChance() + po.getItemSeaCreatureChance());
            po.setMagicFind(po.getBaseMagicFind() + po.getItemMagicFind());
            po.setPetLuck(po.getBasePetLuck() + po.getItemPetLuck());
            po.setTrueDamage(po.getBaseTrueDamage() + po.getItemTrueDamage());
            po.setTrueDefense(po.getBaseTrueDefense() + po.getItemTrueDefense());
            po.setMaxHealth(po.getBaseHealth() + po.getItemHealth());
            po.setMaxIntelligence(po.getBaseIntelligence() + po.getItemIntelligence());
        }

    }

    private int parseLore(String lore) {
        try {
            return Integer.parseInt(lore.split(" ")[lore.split(" ").length - 1].replaceAll(",", "").replace(Utils.chat("&c+"), "").replace(Utils.chat("&a+"), "").replace("%", ""));
        } catch (Exception ignored) {
        }
        return 0;
    }

    private int normalizeYaw(int yaw) {
        int newYaw = yaw;
        while (newYaw <= -180) newYaw += 360;
        while (newYaw > 180) newYaw -= 360;
        return newYaw;
    }

    private void spawnParticles(Location location, int r, int g, int b) {
        Random random = new Random();
        int randomNum = random.nextInt((10 - 3) + 1) + 3;

        ArrayList<PacketPlayOutWorldParticles> particles = new ArrayList<>();

        for (int i = 0; i < randomNum; i++) {
            float randomPos = (float) (.1 + random.nextFloat() * (1.75f - .1));
            float randomPos1 = (float) (.1 + random.nextFloat() * (1.5f - .1));
            float randomPos2 = (float) (.1 + random.nextFloat() * (1.75f - .1));

            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float) location.getX() - randomPos + 1f, (float) location.getY() + randomPos1, (float) location.getZ() - randomPos2 + 1f, r / 255, g / 255, b / 255, 0, 1);
            particles.add(packet);
        }

        for (Player online : Bukkit.getOnlinePlayers()) {
            for (PacketPlayOutWorldParticles packetPlayOut : particles) {
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packetPlayOut);
            }
        }
    }
}
