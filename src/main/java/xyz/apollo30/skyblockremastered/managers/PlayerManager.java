package xyz.apollo30.skyblockremastered.managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.HashMap;

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

            po.setBank(player.getConfigurationSection(".Information").getDouble("bank"));
            po.setPurse(player.getConfigurationSection(".Information").getDouble("purse"));
            po.setGems(player.getConfigurationSection(".Information").getDouble("gems"));

            po.setStatOverride(player.getConfigurationSection(".Database").getBoolean("statOverride"));
            po.setBlockBreak(player.getConfigurationSection(".Database").getBoolean("blockBreak"));
            po.setVanished(player.getConfigurationSection(".Database").getBoolean("vanished"));

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

            po.setRevenantKills(player.getConfigurationSection(".Slayers.Revenant").getInt("revenantkills"));
            po.setTarantulaKills(player.getConfigurationSection(".Slayers.Tarantula").getInt("tarantulakills"));
            po.setSvenKills(player.getConfigurationSection(".Slayers.Sven").getInt("svenkills"));
            po.setSkeletorKills(player.getConfigurationSection(".Slayers.Skeletor").getInt("skeletorkills"));
            po.setTotalKills(player.getConfigurationSection(".Slayers.TotalStats").getInt("totalkills"));

            po.setRevTier(player.getConfigurationSection(".Slayers.Revenant").getInt("revtier"));
            po.setTaraTier(player.getConfigurationSection(".Slayers.Tarantula").getInt("taratier"));
            po.setSvenTier(player.getConfigurationSection(".Slayers.Sven").getInt("sventier"));
            po.setSkeleTier(player.getConfigurationSection(".Slayers.Skeletor").getInt("skeletier"));

            po.setRevLevel(player.getConfigurationSection(".Slayers.Revenant").getInt("revlevel"));
            po.setTaraLevel(player.getConfigurationSection(".Slayers.Tarantula").getInt("taralevel"));
            po.setSvenLevel(player.getConfigurationSection(".Slayers.Sven").getInt("svenlevel"));
            po.setSkeleLevel(player.getConfigurationSection(".Slayers.Skeletor").getInt("skelelevel"));

            po.setRevRng(player.getConfigurationSection(".Slayers.Revenant").getInt("revrng"));
            po.setTaraRng(player.getConfigurationSection(".Slayers.Tarantula").getInt("tararng"));
            po.setSvenRng(player.getConfigurationSection(".Slayers.Sven").getInt("svenrng"));
            po.setSkeleRng(player.getConfigurationSection(".Slayers.Skeletor").getInt("skelerng"));
            po.setTotalRng(player.getConfigurationSection(".Slayers.TotalStats").getInt("totalrng"));

            po.setRevSpent(player.getConfigurationSection(".Slayers.Revenant").getDouble("revspent"));
            po.setTaraSpent(player.getConfigurationSection(".Slayers.Tarantula").getDouble("taraspent"));
            po.setSvenSpent(player.getConfigurationSection(".Slayers.Sven").getDouble("svenspent"));
            po.setSkeleSpent(player.getConfigurationSection(".Slayers.Skeletor").getDouble("skelespent"));
            po.setTotalSpent(player.getConfigurationSection(".Slayers.TotalStats").getDouble("totalspent"));

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

        player.getConfigurationSection(".Database").set("statOverride", po.isStatOverride());
        player.getConfigurationSection(".Database").set("blockBreak", po.isBlockBreak());
        player.getConfigurationSection(".Database").set("vanished", po.isVanished());

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

        player.getConfigurationSection(".Slayers.Revenant").set("revenantkills", po.getRevenantKills());
        player.getConfigurationSection(".Slayers.Tarantula").set("tarantulakills", po.getTarantulaKills());
        player.getConfigurationSection(".Slayers.Sven").set("svenkills", po.getSvenKills());
        player.getConfigurationSection(".Slayers.Skeletor").set("skeletorkills", po.getSkeletorKills());
        player.getConfigurationSection(".Slayers.TotalStats").set("totalkills", po.getTotalKills());

        player.getConfigurationSection(".Slayers.Revenant").set("revtier", po.getRevTier());
        player.getConfigurationSection(".Slayers.Tarantula").set("taratier", po.getTaraTier());
        player.getConfigurationSection(".Slayers.Sven").set("sventier", po.getSvenTier());
        player.getConfigurationSection(".Slayers.Skeletor").set("skeletier", po.getSkeleTier());

        player.getConfigurationSection(".Slayers.Revenant").set("revlevel", po.getRevLevel());
        player.getConfigurationSection(".Slayers.Tarantula").set("taralevel", po.getTaraLevel());
        player.getConfigurationSection(".Slayers.Sven").set("svenlevel", po.getSvenLevel());
        player.getConfigurationSection(".Slayers.Skeletor").set("skelelevel", po.getSkeleLevel());

        player.getConfigurationSection(".Slayers.Revenant").set("revrng", po.getRevRng());
        player.getConfigurationSection(".Slayers.Tarantula").set("tararng", po.getTaraRng());
        player.getConfigurationSection(".Slayers.Sven").set("svenrng", po.getSvenRng());
        player.getConfigurationSection(".Slayers.Skeletor").set("skelerng", po.getSkeleRng());
        player.getConfigurationSection(".Slayers.TotalStats").set("totalrng", po.getTotalRng());

        player.getConfigurationSection(".Slayers.Revenant").set("revspent", po.getRevSpent());
        player.getConfigurationSection(".Slayers.Tarantula").set("taraspent", po.getTaraSpent());
        player.getConfigurationSection(".Slayers.Sven").set("svenspent", po.getSvenSpent());
        player.getConfigurationSection(".Slayers.Skeletor").set("skelespent", po.getSkeleSpent());
        player.getConfigurationSection(".Slayers.TotalStats").set("totalspent", po.getTotalSpent());

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

    public static boolean createPlayerData(Player plr, String uuid, FileConfiguration db) {

        String[] profile_fruits = {"Grapes", "Watermelon", "Mango", "Peach", "Apple", "Pear", "Kiwi"};

        if (!db.isConfigurationSection(uuid)) {
            try {
                db.createSection(uuid);
                db.set(uuid + ".IGN", plr.getName());
                db.getConfigurationSection(uuid).createSection("Information");
                db.getConfigurationSection(uuid + ".Information").set("currentMinionsUsed", 0);
                db.getConfigurationSection(uuid + ".Information").set("maxMinions", 3);
                db.getConfigurationSection(uuid + ".Information").set("fairySouls", 0);
                db.getConfigurationSection(uuid + ".Information").set("bank", (double) 0);
                db.getConfigurationSection(uuid + ".Information").set("purse", (double) 0);

                db.getConfigurationSection(uuid).createSection("Stats");
                db.getConfigurationSection(uuid + ".Stats").set("health", 100);
                db.getConfigurationSection(uuid + ".Stats").set("defense", 0);
                db.getConfigurationSection(uuid + ".Stats").set("strength", 0);
                db.getConfigurationSection(uuid + ".Stats").set("speed", 100);
                db.getConfigurationSection(uuid + ".Stats").set("critchance", 30);
                db.getConfigurationSection(uuid + ".Stats").set("critdamage", 50);
                db.getConfigurationSection(uuid + ".Stats").set("atkSpeed", 0);
                db.getConfigurationSection(uuid + ".Stats").set("intel", 100);
                db.getConfigurationSection(uuid + ".Stats").set("seacreaturechance", 20);
                db.getConfigurationSection(uuid + ".Stats").set("magicfind", 0);
                db.getConfigurationSection(uuid + ".Stats").set("petluck", 0);

                // Database Category Creation
                db.getConfigurationSection(uuid).createSection("Database");
                db.getConfigurationSection(uuid + ".Database").createSection("statOverride");
                db.getConfigurationSection(uuid + ".Database").createSection("blockBreak");
                db.getConfigurationSection(uuid + ".Database").createSection("vanished");

                // Slayer Category Creation
                db.getConfigurationSection(uuid).createSection("Slayers");
                db.getConfigurationSection(uuid + ".Slayers").createSection("Revenant");
                db.getConfigurationSection(uuid + ".Slayers").createSection("Tarantula");
                db.getConfigurationSection(uuid + ".Slayers").createSection("Sven");
                db.getConfigurationSection(uuid + ".Slayers").createSection("Skeletor");
                db.getConfigurationSection(uuid + ".Slayers").createSection("TotalStats");

                db.getConfigurationSection(uuid + ".Slayers.Revenant").set("revenantkills", 0);
                db.getConfigurationSection(uuid + ".Slayers.Tarantula").set("tarantulakills", 0);
                db.getConfigurationSection(uuid + ".Slayers.Sven").set("svenkills", 0);
                db.getConfigurationSection(uuid + ".Slayers.Skeletor").set("skeletorkills", 0);
                db.getConfigurationSection(uuid + ".Slayers.TotalStats").set("totalkills", 0);

                db.getConfigurationSection(uuid + ".Slayers.Revenant").set("revtier", 0);
                db.getConfigurationSection(uuid + ".Slayers.Tarantula").set("taratier", 0);
                db.getConfigurationSection(uuid + ".Slayers.Sven").set("sventier", 0);
                db.getConfigurationSection(uuid + ".Slayers.Skeletor").set("skeletier", 0);

                db.getConfigurationSection(uuid + ".Slayers.Revenant").set("revlevel", 0);
                db.getConfigurationSection(uuid + ".Slayers.Tarantula").set("taralevel", 0);
                db.getConfigurationSection(uuid + ".Slayers.Sven").set("svenlevel", 0);
                db.getConfigurationSection(uuid + ".Slayers.Skeletor").set("skelelevel", 0);

                db.getConfigurationSection(uuid + ".Slayers.Revenant").set("revrng", 0);
                db.getConfigurationSection(uuid + ".Slayers.Tarantula").set("tararng", 0);
                db.getConfigurationSection(uuid + ".Slayers.Sven").set("svenrng", 0);
                db.getConfigurationSection(uuid + ".Slayers.Skeletor").set("skelerng", 0);
                db.getConfigurationSection(uuid + ".Slayers.TotalStats").set("totalrng", 0);

                db.getConfigurationSection(uuid + ".Slayers.Revenant").set("revspent", 0);
                db.getConfigurationSection(uuid + ".Slayers.Tarantula").set("taraspent", 0);
                db.getConfigurationSection(uuid + ".Slayers.Sven").set("svenspent", 0);
                db.getConfigurationSection(uuid + ".Slayers.Skeletor").set("skelespent", 0);
                db.getConfigurationSection(uuid + ".Slayers.TotalStats").set("totalspent", 0);

                db.set(uuid + ".Profile", profile_fruits[(int) Math.floor(Math.random() * profile_fruits.length)]);

                db.getConfigurationSection(uuid).createSection("Skills");
                String[] skills = {"Farming", "Mining", "Combat", "Foraging", "Fishing", "Enchanting", "Alchemy", "Carpentry", "Runecrafting", "Taming"};
                for (String skill : skills) {
                    db.getConfigurationSection(uuid + ".Skills").createSection(skill);
                    db.getConfigurationSection(uuid + ".Skills." + skill).set("level", 0);
                    db.getConfigurationSection(uuid + ".Skills." + skill).set("totalXP", 0);
                    db.getConfigurationSection(uuid + ".Skills." + skill).set("currentXP", 0);
                    db.getConfigurationSection(uuid + ".Skills." + skill).set("neededXP", 50);
                }
                db.getConfigurationSection(uuid).createSection("Collections");

                String[] farmingCollection = {"Wheat", "Carrot", "Potato", "Pumpkin", "Melon", "Seeds", "Mushroom",
                        "Cocoa Beans", "Cactus", "Sugar Cane", "Feather", "Leather", "Raw Porkchop", "Raw Chicken",
                        "Mutton", "Raw Rabbit", "Nether Wart"};
                db.getConfigurationSection(uuid + ".Collections").createSection("Farming");

                for (String category : farmingCollection) {
                    db.getConfigurationSection(uuid + ".Collections.Farming").createSection(category);
                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("level", 0);
                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("totalXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("currentXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("neededXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("totalGathered", 0);
                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("level", 0);
                }

                String[] miningCollection = {"Cobblestone", "Coal", "Iron Ingot", "Gold Ingot", "Diamond",
                        "Lapis Lazuli", "Emerald", "Redstone", "Nether Quartz", "Obsidian", "Glowstone", "Gravel",
                        "Ice", "Netherrack", "Sand", "Endstone"};
                db.getConfigurationSection(uuid + ".Collections").createSection("Mining");

                for (String category : miningCollection) {
                    db.getConfigurationSection(uuid + ".Collections.Mining").createSection(category);
                    db.getConfigurationSection(uuid + ".Collections.Mining." + category).set("level", 0);
                    db.getConfigurationSection(uuid + ".Collections.Mining." + category).set("totalXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Mining." + category).set("currentXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Mining." + category).set("neededXP", 0);
                }

                String[] combatCollection = {"Rotten Flesh", "Bone", "String", "Spider Eye", "Gunpowder",
                        "Ender Pearl", "Ghast Tear", "Slimeball", "Blaze Rod", "Magma Cream"};
                db.getConfigurationSection(uuid + ".Collections").createSection("Combat");

                for (String category : combatCollection) {
                    db.getConfigurationSection(uuid + ".Collections.Combat").createSection(category);
                    db.getConfigurationSection(uuid + ".Collections.Combat." + category).set("level", 0);
                    db.getConfigurationSection(uuid + ".Collections.Combat." + category).set("totalXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Combat." + category).set("currentXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Combat." + category).set("neededXP", 0);
                }

                String[] foragingCollection = {"Oak Wood", "Spruce Wood", "Birch Wood", "Dark Oak Wood", "Acacia Wood",
                        "Jungle Wood"};
                db.getConfigurationSection(uuid + ".Collections").createSection("Foraging");

                for (String category : foragingCollection) {
                    db.getConfigurationSection(uuid + ".Collections.Foraging").createSection(category);
                    db.getConfigurationSection(uuid + ".Collections.Foraging." + category).set("level", 0);
                    db.getConfigurationSection(uuid + ".Collections.Foraging." + category).set("totalXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Foraging." + category).set("currentXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Foraging." + category).set("neededXP", 0);
                }

                String[] fishingCollection = {"Raw Fish", "Raw Salmon", "Clownfish", "Pufferfish", "Prismarine Shard",
                        "Prismarine Crystal", "Clay", "Lily Pad", "Ink Sack", "Sponge"};
                db.getConfigurationSection(uuid + ".Collections").createSection("Fishing");

                for (String category : fishingCollection) {
                    db.getConfigurationSection(uuid + ".Collections.Fishing").createSection(category);
                    db.getConfigurationSection(uuid + ".Collections.Fishing." + category).set("level", 0);
                    db.getConfigurationSection(uuid + ".Collections.Fishing." + category).set("totalXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Fishing." + category).set("currentXP", 0);
                    db.getConfigurationSection(uuid + ".Collections.Fishing." + category).set("neededXP", 0);
                }

                return true;
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(Utils.chat(e.toString()));
                return false;
            }
        } else
            return true;
    }

    public PlayerObject getPlayerData(Player plr) {
        return playerObjects.get(plr);
    }
}
