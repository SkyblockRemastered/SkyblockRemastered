package xyz.apollo30.skyblockremastered.managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private final SkyblockRemastered plugin;

    public PlayerManager(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public HashMap<Player, PlayerObject> playerObjects = new HashMap<>();

    public void createPlayerData(Player plr) {

        try {
            PlayerObject po = new PlayerObject();

            FileConfiguration db = plugin.db.getPlayers();
            if (!db.isConfigurationSection(plr.getUniqueId().toString())) {
                Utils.broadCast("Database created for " + plr.getName());
                playerObjects.put(plr, po);
                return;
            }
            if (playerObjects.get(plr) != null) return;
            ConfigurationSection player = db.getConfigurationSection(plr.getUniqueId().toString());

            po.setIgn(plr.getName());
            po.setUuid(plr.getUniqueId().toString());

            po.setSlayer(player.getConfigurationSection(".Information").getString("slayer"));

            po.setBank(player.getConfigurationSection(".Information").getDouble("bank"));
            po.setPurse(player.getConfigurationSection(".Information").getDouble("purse"));
            po.setGems(player.getConfigurationSection(".Information").getDouble("gems"));

            po.setHealth(player.getConfigurationSection(".Stats").getInt("health"));
            po.setMaxHealth(player.getConfigurationSection(".Stats").getInt("health"));
            po.setDefense(player.getConfigurationSection(".Stats").getInt("defense"));
            po.setStrength(player.getConfigurationSection(".Stats").getInt("strength"));
            po.setSpeed(player.getConfigurationSection(".Stats").getInt("speed"));
            po.setCrit_chance(player.getConfigurationSection(".Stats").getInt("critchance"));
            po.setCrit_damage(player.getConfigurationSection(".Stats").getInt("critdamage"));
            po.setAtk_speed(player.getConfigurationSection(".Stats").getInt("atkSpeed"));
            po.setIntelligence(player.getConfigurationSection(".Stats").getInt("intel"));
            po.setMaxIntelligence(player.getConfigurationSection(".Stats").getInt("intel"));
            po.setSea_creature_chance(player.getConfigurationSection(".Stats").getInt("seacreaturechance"));
            po.setMagic_find(player.getConfigurationSection(".Stats").getInt("magicfind"));
            po.setPet_luck(player.getConfigurationSection(".Stats").getInt("petluck"));

            ConfigurationSection farming = player.getConfigurationSection(".Skills.Farming");
            ConfigurationSection mining = player.getConfigurationSection(".Skills.Mining");
            ConfigurationSection combat = player.getConfigurationSection(".Skills.Combat");
            ConfigurationSection foraging = player.getConfigurationSection(".Skills.Foraging");
            ConfigurationSection fishing = player.getConfigurationSection(".Skills.Fishing");
            ConfigurationSection enchanting = player.getConfigurationSection(".Skills.Enchanting");
            ConfigurationSection alchemy = player.getConfigurationSection(".Skills.Alchemy");
            ConfigurationSection carpentry = player.getConfigurationSection(".Skills.Carpentry");
            ConfigurationSection runecrafting = player.getConfigurationSection(".Skills.Runecrafting");
            ConfigurationSection taming = player.getConfigurationSection(".Skills.Taming");

            po.setFarmingLevel(farming.getInt("level"));
            po.setFarmingXP(farming.getInt("currentXP"));
            po.setFarmingNeeded(farming.getInt("neededXP"));
            po.setFarmingTotal(farming.getInt("totalXP"));
            po.setMiningLevel(mining.getInt("level"));
            po.setMiningXP(mining.getInt("currentXP"));
            po.setMiningNeeded(mining.getInt("neededXP"));
            po.setMiningTotal(mining.getInt("totalXP"));
            po.setCombatLevel(combat.getInt("level"));
            po.setCombatXP(combat.getInt("currentXP"));
            po.setCombatNeeded(combat.getInt("neededXP"));
            po.setCombatTotal(combat.getInt("totalXP"));
            po.setForagingLevel(foraging.getInt("level"));
            po.setForagingXP(foraging.getInt("currentXP"));
            po.setForagingNeeded(foraging.getInt("neededXP"));
            po.setForagingTotal(foraging.getInt("totalXP"));
            po.setFishingLevel(fishing.getInt("level"));
            po.setFishingXP(fishing.getInt("currentXP"));
            po.setFishingNeeded(fishing.getInt("neededXP"));
            po.setFishingTotal(fishing.getInt("totalXP"));
            po.setEnchantingLevel(enchanting.getInt("level"));
            po.setEnchantingXP(enchanting.getInt("currentXP"));
            po.setEnchantingNeeded(enchanting.getInt("neededXP"));
            po.setEnchantingTotal(enchanting.getInt("totalXP"));
            po.setAlchemyLevel(alchemy.getInt("level"));
            po.setAlchemyXP(alchemy.getInt("currentXP"));
            po.setAlchemyNeeded(alchemy.getInt("neededXP"));
            po.setAlchemyTotal(alchemy.getInt("totalXP"));
            po.setCarpentryLevel(carpentry.getInt("level"));
            po.setCarpentryXP(carpentry.getInt("currentXP"));
            po.setCarpentryNeeded(carpentry.getInt("neededXP"));
            po.setCarpentryTotal(carpentry.getInt("totalXP"));
            po.setRunecraftingLevel(runecrafting.getInt("level"));
            po.setRunecraftingXP(runecrafting.getInt("currentXP"));
            po.setRunecraftingNeeded(runecrafting.getInt("neededXP"));
            po.setRunecraftingTotal(runecrafting.getInt("totalXP"));
            po.setTamingLevel(taming.getInt("level"));
            po.setTamingXP(taming.getInt("currentXP"));
            po.setTamingNeeded(taming.getInt("neededXP"));
            po.setTamingTotal(taming.getInt("totalXP"));

            playerObjects.put(plr, po);
            Utils.broadCast("Database created for " + plr.getName());
        } catch (Exception ignored) {

        }
    }

//    gamemode:
//    description: Change a players gamemode.
//    aliases: [survival, creative, adventure, spectator, spec, gms, gmc, gma, gmsp, esurvival, ecreative, eadventure, espectator, espec]

    public void savePlayerData(Player plr) {
        FileConfiguration db = plugin.db.getPlayers();
        if (db.isConfigurationSection(plr.getUniqueId().toString())) {
            createPlayerData(plr);
        }

        PlayerObject po = playerObjects.get(plr);
        ConfigurationSection player = db.getConfigurationSection(plr.getUniqueId().toString());

        player.getConfigurationSection(".Information").set("purse", po.getPurse());
        player.getConfigurationSection(".Information").set("bank", po.getBank());
        player.getConfigurationSection(".Information").set("gems", po.getGems());

        player.getConfigurationSection(".Information").set("slayer", po.getSlayer());

        player.getConfigurationSection(".Stats").set("health", po.getMaxHealth());
        player.getConfigurationSection(".Stats").set("defense", po.getDefense());
        player.getConfigurationSection(".Stats").set("strength", po.getStrength());
        player.getConfigurationSection(".Stats").set("speed", po.getSpeed());
        player.getConfigurationSection(".Stats").set("critchance", po.getCrit_chance());
        player.getConfigurationSection(".Stats").set("critdamage", po.getCrit_damage());
        player.getConfigurationSection(".Stats").set("atkSpeed", po.getAtk_speed());
        player.getConfigurationSection(".Stats").set("intel", po.getMaxIntelligence());
        player.getConfigurationSection(".Stats").set("seacreaturechance", po.getSea_creature_chance());
        player.getConfigurationSection(".Stats").set("magicfind", po.getMagic_find());
        player.getConfigurationSection(".Stats").set("petluck", po.getPet_luck());

        ConfigurationSection farming = player.getConfigurationSection(".Skills.Farming");
        ConfigurationSection mining = player.getConfigurationSection(".Skills.Mining");
        ConfigurationSection combat = player.getConfigurationSection(".Skills.Combat");
        ConfigurationSection foraging = player.getConfigurationSection(".Skills.Foraging");
        ConfigurationSection fishing = player.getConfigurationSection(".Skills.Fishing");
        ConfigurationSection enchanting = player.getConfigurationSection(".Skills.Enchanting");
        ConfigurationSection alchemy = player.getConfigurationSection(".Skills.Alchemy");
        ConfigurationSection carpentry = player.getConfigurationSection(".Skills.Carpentry");
        ConfigurationSection runecrafting = player.getConfigurationSection(".Skills.Runecrafting");
        ConfigurationSection taming = player.getConfigurationSection(".Skills.Taming");

        farming.set("level", po.getFarmingLevel());
        farming.set("currentXP", po.getFarmingXP());
        farming.set("neededXP", po.getFarmingNeeded());
        farming.set("totalXP", po.getFarmingTotal());
        mining.set("level", po.getMiningLevel());
        mining.set("currentXP", po.getMiningXP());
        mining.set("neededXP", po.getMiningNeeded());
        mining.set("totalXP", po.getMiningTotal());
        combat.set("level", po.getCombatLevel());
        combat.set("currentXP", po.getCombatXP());
        combat.set("neededXP", po.getCombatNeeded());
        combat.set("totalXP", po.getCombatTotal());
        foraging.set("level", po.getForagingLevel());
        foraging.set("currentXP", po.getForagingXP());
        foraging.set("neededXP", po.getForagingNeeded());
        foraging.set("totalXP", po.getForagingTotal());
        fishing.set("level", po.getFishingLevel());
        fishing.set("currentXP", po.getFishingXP());
        fishing.set("neededXP", po.getFishingNeeded());
        fishing.set("totalXP", po.getFishingTotal());
        enchanting.set("level", po.getEnchantingLevel());
        enchanting.set("currentXP", po.getEnchantingXP());
        enchanting.set("neededXP", po.getEnchantingNeeded());
        enchanting.set("totalXP", po.getEnchantingTotal());
        alchemy.set("level", po.getAlchemyLevel());
        alchemy.set("currentXP", po.getAlchemyXP());
        alchemy.set("neededXP", po.getAlchemyNeeded());
        alchemy.set("totalXP", po.getAlchemyTotal());
        carpentry.set("level", po.getCarpentryLevel());
        carpentry.set("currentXP", po.getCarpentryXP());
        carpentry.set("neededXP", po.getCarpentryNeeded());
        carpentry.set("totalXP", po.getCarpentryTotal());
        runecrafting.set("level", po.getRunecraftingLevel());
        runecrafting.set("currentXP", po.getRunecraftingXP());
        runecrafting.set("neededXP", po.getRunecraftingNeeded());
        runecrafting.set("totalXP", po.getRunecraftingTotal());
        taming.set("level", po.getTamingLevel());
        taming.set("currentXP", po.getTamingXP());
        taming.set("neededXP", po.getTamingNeeded());
        taming.set("totalXP", po.getTamingTotal());

        plugin.db.savePlayers();
        Utils.broadCast("Database saved for " + plr.getName());
    }

    public PlayerObject getPlayerData(Player plr) {
        return playerObjects.get(plr);
    }

    public void initTimer() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            for (PlayerObject player_data : playerObjects.values()) {

                Player plr = Bukkit.getPlayer(UUID.fromString(player_data.getUuid()));
                if (!plr.isOnline()) playerObjects.remove(plr);

                // Set Saturation
                plr.setSaturation(20);
                plr.setWalkSpeed(player_data.getSpeed() > 500 ? 1.0f : player_data.getSpeed() <= 0 ? 0.0f : (float) player_data.getSpeed() / 500);
                plr.setFlySpeed(player_data.getSpeed() / 2 > 500 ? 1.0f : player_data.getSpeed() / 2 <= 0 ? 0.0f : (float) player_data.getSpeed() / 2 / 500);

                String hp = Integer.toString(player_data.getHealth());
                String maxhp = Integer.toString(player_data.getMaxHealth());

                String defense = Integer.toString(player_data.getDefense());

                String intell = Integer.toString(player_data.getIntelligence());
                String maxIntell = Integer.toString(player_data.getMaxIntelligence());

                String actionbar = "&c" + hp + "/" + maxhp + InventoryManager.getUnicode("heart") + " HP" + "     &a" + defense + InventoryManager.getUnicode("defense") + " Defense" + "     &b" + intell + "/" + maxIntell + InventoryManager.getUnicode("intel") + " Intelligence";
                PacketManager.sendMessage(plr, Utils.chat(actionbar));
            }
        }, 5L, 5L);

        // Regeneration
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (PlayerObject po : playerObjects.values()) {
                Player plr = Bukkit.getPlayer(UUID.fromString(po.getUuid()));
                if (!plr.isOnline()) playerObjects.remove(plr);

                // Health Regeneration
                if (po.getHealth() < po.getMaxHealth()) {
                    if (plr.getFireTicks() <= 0) {
                        int health_regen = po.getHealth() + (int) ((po.getMaxHealth() * 0.01) + 1.5);
                        po.setHealth(Math.min(health_regen, po.getMaxHealth()));
                    }
                }

                // Mana Regeneration
                if (po.getIntelligence() < po.getMaxIntelligence()) {
                    int mana_regen = po.getIntelligence() + (int) ((po.getMaxIntelligence() * 0.01) + 1.5);
                    po.setIntelligence(Math.min(mana_regen, po.getMaxIntelligence()));
                }
            }
        }, 40L, 40L);
    }
}
