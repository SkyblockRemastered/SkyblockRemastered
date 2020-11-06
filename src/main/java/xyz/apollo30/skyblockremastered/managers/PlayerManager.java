package xyz.apollo30.skyblockremastered.managers;

import com.mongodb.*;
import org.bson.*;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.MongoUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private final SkyblockRemastered plugin;

    public PlayerManager(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static HashMap<Player, PlayerObject> playerObjects = new HashMap<>();

//    public void savePlayerData(Player plr) {
//
//        PlayerObject po = playerObjects.get(plr);
//
//        Document tempDoc;
//        Document doc = new Document("uuid", plr.getUniqueId().toString());
//        doc.append("IGN", plr.getName());
//        tempDoc = new Document("bank", po.getBank())
//                .append("purse", po.getPurse())
//                .append("bits", po.getBits());
//        doc.append("information", tempDoc);
//
//        tempDoc = new Document("health", po.getBaseHealth())
//                .append("defense", po.getBaseDefense())
//                .append("strength", po.getBaseStrength())
//                .append("speed", po.getBaseSpeed())
//                .append("critChance", po.getBaseCritChance())
//                .append("critDamage", po.getBaseCritDamage())
//                .append("atkSpeed", po.getBaseAtkSpeed())
//                .append("intel", po.getBaseIntelligence())
//                .append("seaCreatureChance", po.getBaseSeaCreatureChance())
//                .append("magicFind", po.getBaseMagicFind())
//                .append("petLuck", po.getBasePetLuck());
//
//        doc.append("Stats", tempDoc);
//
//        tempDoc = new Document("statOverride", po.isStatOverride())
//                .append("blockBreak", po.isBlockBreak())
//                .append("vanished", po.isVanished());
//        doc.append("database", tempDoc);
//        tempDoc = new Document("Revenant", new Document("revenantkills", po.getRevenantKills())
//                .append("revtier", po.getRevTier())
//                .append("revlevel", po.getRevLevel())
//                .append("revrng", po.getRevRng())
//                .append("revspent", po.getRevSpent()))
//                .append("Tarantula", new Document("tarantulakills", po.getTarantulaKills())
//                        .append("taratier", po.getTaraTier())
//                        .append("taralevel", po.getTaraLevel())
//                        .append("tararng", po.getTaraRng())
//                        .append("taraspent", po.getTaraSpent()))
//                .append("Sven", new Document("svenkills", po.getSvenKills())
//                        .append("sventier", po.getSvenTier())
//                        .append("svenlevel", po.getSvenLevel())
//                        .append("svenrng", po.getSvenRng())
//                        .append("svenspent", po.getSvenSpent()))
//                .append("Skeletor", new Document("skeletorkills", po.getSkeletorKills())
//                        .append("skeletier", po.getSkeleTier())
//                        .append("skelelevel", po.getSkeleLevel())
//                        .append("skelerng", po.getSkeleRng())
//                        .append("skelespent", po.getSkeleSpent()))
//                .append("TotalStats", new Document("totalkills", po.getTotalKills())
//                        .append("totalrng", po.getTotalRng())
//                        .append("totalspent", po.getTotalSpent()));
//
//        doc.append("Slayers", tempDoc);
//
//        /*
//        "level", po.getFarmingLevel())
//                .append("totalXP", po.getFarmingTotal())
//                .append("currentXP", po.getFarmingXP())
//                .append("neededXP", po.getFarmingNeeded())
//         */
//
//        tempDoc = new Document();
//        tempDoc.append("Farming", new Document("level", po.getFarmingLevel())
//                .append("totalXP", po.getFarmingTotal())
//                .append("currentXP", po.getFarmingXP())
//                .append("neededXP", po.getFarmingNeeded()))
//                .append("Mining", new Document("level", po.getMiningLevel())
//                        .append("totalXP", po.getMiningTotal())
//                        .append("currentXP", po.getMiningXP())
//                        .append("neededXP", po.getMiningNeeded()))
//                .append("Combat", new Document("level", po.getCombatLevel())
//                        .append("totalXP", po.getCombatTotal())
//                        .append("currentXP", po.getCombatXP())
//                        .append("neededXP", po.getCombatNeeded()))
//                .append("Foraging", new Document("level", po.getForagingLevel())
//                        .append("totalXP", po.getForagingTotal())
//                        .append("currentXP", po.getForagingXP())
//                        .append("neededXP", po.getForagingNeeded()))
//                .append("Fishing", new Document("level", po.getFishingLevel())
//                        .append("totalXP", po.getFishingTotal())
//                        .append("currentXP", po.getFishingXP())
//                        .append("neededXP", po.getFishingNeeded()))
//                .append("Enchanting", new Document("level", po.getEnchantingLevel())
//                        .append("totalXP", po.getEnchantingTotal())
//                        .append("currentXP", po.getEnchantingXP())
//                        .append("neededXP", po.getEnchantingNeeded()))
//                .append("Alchemy", new Document("level", po.getAlchemyLevel())
//                        .append("totalXP", po.getAlchemyTotal())
//                        .append("currentXP", po.getAlchemyXP())
//                        .append("neededXP", po.getAlchemyNeeded()))
//                .append("Carpentry", new Document("level", po.getCarpentryLevel())
//                        .append("totalXP", po.getCarpentryTotal())
//                        .append("currentXP", po.getCarpentryXP())
//                        .append("neededXP", po.getCarpentryNeeded()))
//                .append("Runecrafting", new Document("level", po.getRunecraftingLevel())
//                        .append("totalXP", po.getRunecraftingTotal())
//                        .append("currentXP", po.getRunecraftingXP())
//                        .append("neededXP", po.getRunecraftingNeeded()))
//                .append("Taming", new Document("level", po.getTamingLevel())
//                        .append("totalXP", po.getTamingTotal())
//                        .append("currentXP", po.getTamingXP())
//                        .append("neededXP", po.getTamingNeeded()));
//
//        doc.append("Skills", tempDoc);
//
//        MongoUtils.insertOrUpdate(new Document("uuid", plr.getUniqueId().toString()), doc);
//
//        Utils.broadCast("Database saved for " + plr.getName());
//    }
//
//    public static void createPlayerData(Player plr) {
//
//        try {
//            PlayerObject po = new PlayerObject();
//            FindIterable<Document> findResult = MongoUtils.getInstance().getPlayerCollection().find(new Document("uuid", plr.getUniqueId().toString()));
//            if (findResult.first() == null) {
//                Utils.broadCast("Database created for " + plr.getName());
//                buildPlayerData(plr);
//                createPlayerData(plr);
//                return;
//            }
//
//            Document doc = findResult.first();
//            if (doc == null || playerObjects.get(plr) != null) return;
//
//            po.setIgn(plr.getName());
//            po.setUuid(plr.getUniqueId().toString());
//
//            po.setBank(doc.getDouble("Information.bank"));
//            po.setPurse(doc.getDouble("Information.purse"));
//            po.setBits(doc.getDouble("Information.bits"));
//
//            po.setStatOverride(doc.getBoolean("Database.statOverride"));
//            po.setBlockBreak(doc.getBoolean("Database.blockBreak"));
//            po.setVanished(doc.getBoolean("Database.vanished"));
//
//            po.setBaseHealth(doc.getInteger("Stats.health"));
//            po.setBaseDefense(doc.getInteger("Stats.defense"));
//            po.setBaseStrength(doc.getInteger("Stats.strength"));
//            po.setSpeed(doc.getInteger("Stats.speed"));
//            po.setBaseCritChance(doc.getInteger("Stats.critChance"));
//            po.setBaseCritDamage(doc.getInteger("Stats.critDamage"));
//            po.setBaseAtkSpeed(doc.getInteger("Stats.atkSpeed"));
//            po.setBaseIntelligence(doc.getInteger("Stats.intel"));
//            po.setBaseSeaCreatureChance(doc.getInteger("Stats.seaCreatureChance"));
//            po.setBaseMagicFind(doc.getInteger("Stats.magicFind"));
//            po.setBasePetLuck(doc.getInteger("Stats.petLuck"));
//
//            po.setRevenantKills(doc.getInteger("Slayers.Revenant.revenantkills"));
//            po.setTarantulaKills(doc.getInteger("Slayers.Tarantula.revenantkills"));
//            po.setSvenKills(doc.getInteger("Slayers.Sven.svenkills"));
//            po.setSkeletorKills(doc.getInteger("Slayers.Skeletor.skeletorkills"));
//            po.setTotalKills(doc.getInteger("Slayers.TotalStats.totalkills"));
//
//            po.setRevTier(doc.getInteger("Slayers.Revenant.revtier"));
//            po.setTaraTier(doc.getInteger("Slayers.Tarantula.taratier"));
//            po.setSvenTier(doc.getInteger("Slayers.Sven.sventier"));
//            po.setSkeleTier(doc.getInteger("Slayers.Skeletor.skeletier"));
//
//            po.setRevLevel(doc.getInteger("Slayers.Revenant.revlevel"));
//            po.setTaraLevel(doc.getInteger("Slayers.Tarantula.taralevel"));
//            po.setSvenLevel(doc.getInteger("Slayers.Sven.svenlevel"));
//            po.setSkeleLevel(doc.getInteger("Slayers.skelelevel.skelelevel"));
//
//            po.setRevRng(doc.getInteger("Slayers.Revenant.revrng"));
//            po.setTaraRng(doc.getInteger("Slayers.Tarantula.tararng"));
//            po.setSvenRng(doc.getInteger("Slayers.Sven.svenrng"));
//            po.setSkeleRng(doc.getInteger("Slayers.Skeletor.skelerng"));
//            po.setTotalRng(doc.getInteger("Slayers.TotalStats.totalrng"));
//
//            po.setRevSpent(doc.getDouble("Slayers.Revenant.revspent"));
//            po.setTaraSpent(doc.getDouble("Slayers.Tarantula.taraspent"));
//            po.setSvenSpent(doc.getDouble("Slayers.Sven.svenspent"));
//            po.setSkeleSpent(doc.getDouble("Slayers.Skeletor.skelespent"));
//            po.setTotalSpent(doc.getDouble("Slayers.TotalStats.totalspent"));
//
//            po.setFarmingLevel(doc.getInteger("Skills.Farming.level"));
//            po.setFarmingXP(doc.getInteger("Skills.Farming.currentXP"));
//            po.setFarmingNeeded(doc.getInteger("Skills.Farming.neededXP"));
//            po.setFarmingTotal(doc.getInteger("Skills.Farming.totalXP"));
//            po.setMiningLevel(doc.getInteger("Skills.Mining.level"));
//            po.setMiningXP(doc.getInteger("Skills.Mining.currentXP"));
//            po.setMiningNeeded(doc.getInteger("Skills.Mining.neededXP"));
//            po.setMiningTotal(doc.getInteger("Skills.Mining.totalXP"));
//            po.setCombatLevel(doc.getInteger("Skills.Combat.level"));
//            po.setCombatXP(doc.getInteger("Skills.Combat.currentXP"));
//            po.setCombatNeeded(doc.getInteger("Skills.Combat.neededXP"));
//            po.setCombatTotal(doc.getInteger("Skills.Combat.totalXP"));
//            po.setForagingLevel(doc.getInteger("Skills.Foraging.level"));
//            po.setForagingXP(doc.getInteger("Skills.Foraging.currentXP"));
//            po.setForagingNeeded(doc.getInteger("Skills.Foraging.neededXP"));
//            po.setForagingTotal(doc.getInteger("Skills.Foraging.totalXP"));
//            po.setFishingLevel(doc.getInteger("Skills.Fishing.level"));
//            po.setFishingXP(doc.getInteger("Skills.Fishing.currentXP"));
//            po.setFishingNeeded(doc.getInteger("Skills.Fishing.neededXP"));
//            po.setFishingTotal(doc.getInteger("Skills.Fishing.totalXP"));
//            po.setEnchantingLevel(doc.getInteger("SSkills.Enchanting.level"));
//            po.setEnchantingXP(doc.getInteger("Skills.Enchanting.currentXP"));
//            po.setEnchantingNeeded(doc.getInteger("Skills.Enchanting.neededXP"));
//            po.setEnchantingTotal(doc.getInteger("Skills.Enchanting.totalXP"));
//            po.setAlchemyLevel(doc.getInteger("Skills.Alchemy.level"));
//            po.setAlchemyXP(doc.getInteger("Skills.Alchemy.currentXP"));
//            po.setAlchemyNeeded(doc.getInteger("Skills.Alchemy.neededXP"));
//            po.setAlchemyTotal(doc.getInteger("Skills.Alchemy.totalXP"));
//            po.setCarpentryLevel(doc.getInteger("Skills.Carpentry.level"));
//            po.setCarpentryXP(doc.getInteger("Skills.Carpentry.currentXP"));
//            po.setCarpentryNeeded(doc.getInteger("Skills.Carpentry.neededXP"));
//            po.setCarpentryTotal(doc.getInteger("Skills.Carpentry.totalXP"));
//            po.setRunecraftingLevel(doc.getInteger("Skills.Runecrafting.level"));
//            po.setRunecraftingXP(doc.getInteger("Skills.Runecrafting.currentXP"));
//            po.setRunecraftingNeeded(doc.getInteger("Skills.Runecrafting.neededXP"));
//            po.setRunecraftingTotal(doc.getInteger("Skills.Runecrafting.totalXP"));
//            po.setTamingLevel(doc.getInteger("Skills.Taming.level"));
//            po.setTamingXP(doc.getInteger("Skills.Taming.currentXP"));
//            po.setTamingNeeded(doc.getInteger("Skills.Taming.neededXP"));
//            po.setTamingTotal(doc.getInteger("Skills.Taming.totalXP"));
//
//            playerObjects.put(plr, po);
//            Utils.broadCast("[DEBUG] Database created for " + plr.getName());
//        } catch (Exception err) {
//            Utils.broadCast("[ERROR] " + err.toString());
//        }
//    }
//
//    public static void buildPlayerData(Player plr) {
//        if (MongoUtils.getInstance().getPlayerCollection().find(new Document("uuid", plr.getUniqueId().toString())).first() == null)
//            return;
//
//        String[] profile_fruits = {"Grapes", "Watermelon", "Mango", "Peach", "Apple", "Pear", "Kiwi"};
//        Document tempDoc;
//        Document tempDoc2 = null;
//        Document doc = new Document("uuid", plr.getUniqueId().toString());
//        doc.append("IGN", plr.getName());
//        tempDoc = new Document("currentMinionsUsed", 0)
//                .append("maxMinions", 3)
//                .append("fairySouls", 0)
//                .append("bank", (double) 0)
//                .append("purse", (double) 0);
//        doc.append("information", tempDoc);
//
//        tempDoc = new Document("health", 100)
//                .append("defense", 0)
//                .append("strength", 0)
//                .append("speed", 100)
//                .append("critChance", 30)
//                .append("critDamage", 50)
//                .append("atkSpeed", 0)
//                .append("intel", 100)
//                .append("seaCreatureChance", 20)
//                .append("magicFind", 0)
//                .append("petLuck", 0);
//
//        doc.append("Stats", tempDoc);
//
//        tempDoc = new Document("statOverride", false)
//                .append("blockBreak", false)
//                .append("vanished", false);
//        doc.append("database", tempDoc);
//        tempDoc = new Document("Revenant", new Document("revenantkills", 0)
//                .append("revtier", 0)
//                .append("revlevel", 0)
//                .append("revrng", 0)
//                .append("revspent", 0))
//                .append("Tarantula", new Document("tarantulakills", 0)
//                        .append("taratier", 0)
//                        .append("taralevel", 0)
//                        .append("tararng", 0)
//                        .append("taraspent", 0))
//                .append("Sven", new Document("svenkills", 0)
//                        .append("sventier", 0)
//                        .append("svenlevel", 0)
//                        .append("svenrng", 0)
//                        .append("svenspent", 0))
//                .append("Skeletor", new Document("skeletorkills", 0)
//                        .append("skeletier", 0)
//                        .append("skelelevel", 0)
//                        .append("skelerng", 0)
//                        .append("skelespent", 0))
//                .append("TotalStats", new Document("totalkills", 0)
//                        .append("totalrng", 0)
//                        .append("totalspent", 0));
//
//        doc.append("Slayers", tempDoc);
//        doc.append("Profile", profile_fruits[(int) Math.floor(Math.random() * profile_fruits.length)]);
//
//
//        String[] skills = {"Farming", "Mining", "Combat", "Foraging", "Fishing", "Enchanting", "Alchemy", "Carpentry", "Runecrafting", "Taming"};
//        tempDoc = new Document();
//        for (String skill : skills) {
//            tempDoc.append(skill, new Document("level", 0)
//                    .append("totalXP", 0)
//                    .append("currentXP", 0)
//                    .append("neededXP", 0));
//        }
//        doc.append("Skills", tempDoc);
//
//        HashMap<String, String[]> collections = new HashMap<String, String[]>();
//        collections.put("farming", new String[]{"Wheat", "Carrot", "Potato", "Pumpkin", "Melon", "Seeds", "Mushroom",
//                "Cocoa Beans", "Cactus", "Sugar Cane", "Feather", "Leather", "Raw Porkchop", "Raw Chicken",
//                "Mutton", "Raw Rabbit", "Nether Wart"});
//        collections.put("mining", new String[]{"Cobblestone", "Coal", "Iron Ingot", "Gold Ingot", "Diamond",
//                "Lapis Lazuli", "Emerald", "Redstone", "Nether Quartz", "Obsidian", "Glowstone", "Gravel",
//                "Ice", "Netherrack", "Sand", "Endstone"});
//        collections.put("combat", new String[]{"Rotten Flesh", "Bone", "String", "Spider Eye", "Gunpowder",
//                "Ender Pearl", "Ghast Tear", "Slimeball", "Blaze Rod", "Magma Cream"});
//        collections.put("foraging", new String[]{"Oak Wood", "Spruce Wood", "Birch Wood", "Dark Oak Wood", "Acacia Wood",
//                "Jungle Wood"});
//        collections.put("fishing", new String[]{"Raw Fish", "Raw Salmon", "Clownfish", "Pufferfish", "Prismarine Shard",
//                "Prismarine Crystal", "Clay", "Lily Pad", "Ink Sack", "Sponge"});
//
//        tempDoc = new Document();
//        for (Map.Entry<String, String[]> map : collections.entrySet()) {
//            for (String coll : map.getValue()) {
//                tempDoc2 = new Document("level", 0)
//                        .append("totalXP", 0)
//                        .append("currentXP", 0)
//                        .append("neededXP", 0)
//                        .append("totalGathered", 0)
//                        .append("level", 0);
//            }
//            tempDoc.append(map.getKey(), tempDoc2);
//        }
//        doc.append("Collections", tempDoc);
//        MongoUtils.getInstance().getPlayerCollection().insertOne(doc);
//
//    }
//
//    public PlayerObject getPlayerData(Player plr) {
//        return playerObjects.get(plr);
//    }

    public static void createPlayerData(Player plr) {
        PlayerObject po = new PlayerObject();
        playerObjects.put(plr, po);
    }

    public static void savePlayerData(Player plr) {

    }

    public PlayerObject getPlayerData(Player plr) {
        return playerObjects.get(plr);
    }

//    public void savePlayerData(Player plr) {
//        FileConfiguration db = plugin.db.getPlayers();
//        if (db.isConfigurationSection(plr.getUniqueId().toString())) {
//            createPlayerData(plr);
//        }
//
//        PlayerObject po = playerObjects.get(plr);
//        ConfigurationSection player = db.getConfigurationSection(plr.getUniqueId().toString());
//
//        player.getConfigurationSection(".Information").set("purse", po.getPurse());
//        player.getConfigurationSection(".Information").set("bank", po.getBank());
//        player.getConfigurationSection(".Information").set("bits", po.getBits());
//
//        player.getConfigurationSection(".Database").set("statOverride", po.isStatOverride());
//        player.getConfigurationSection(".Database").set("blockBreak", po.isBlockBreak());
//        player.getConfigurationSection(".Database").set("vanished", po.isVanished());
//
//        player.getConfigurationSection(".Stats").set("health", po.getBaseHealth());
//        player.getConfigurationSection(".Stats").set("defense", po.getBaseDefense());
//        player.getConfigurationSection(".Stats").set("strength", po.getBaseStrength());
//        player.getConfigurationSection(".Stats").set("speed", po.getBaseSpeed());
//        player.getConfigurationSection(".Stats").set("critchance", po.getBaseCritChance());
//        player.getConfigurationSection(".Stats").set("critdamage", po.getBaseCritDamage());
//        player.getConfigurationSection(".Stats").set("atkSpeed", po.getBaseAtkSpeed());
//        player.getConfigurationSection(".Stats").set("intel", po.getBaseIntelligence());
//        player.getConfigurationSection(".Stats").set("seacreaturechance", po.getBaseSeaCreatureChance());
//        player.getConfigurationSection(".Stats").set("magicfind", po.getBaseMagicFind());
//        player.getConfigurationSection(".Stats").set("petluck", po.getBasePetLuck());
//
//        player.getConfigurationSection(".Slayers.Revenant").set("revenantkills", po.getRevenantKills());
//        player.getConfigurationSection(".Slayers.Tarantula").set("tarantulakills", po.getTarantulaKills());
//        player.getConfigurationSection(".Slayers.Sven").set("svenkills", po.getSvenKills());
//        player.getConfigurationSection(".Slayers.Skeletor").set("skeletorkills", po.getSkeletorKills());
//        player.getConfigurationSection(".Slayers.TotalStats").set("totalkills", po.getTotalKills());
//
//        player.getConfigurationSection(".Slayers.Revenant").set("revtier", po.getRevTier());
//        player.getConfigurationSection(".Slayers.Tarantula").set("taratier", po.getTaraTier());
//        player.getConfigurationSection(".Slayers.Sven").set("sventier", po.getSvenTier());
//        player.getConfigurationSection(".Slayers.Skeletor").set("skeletier", po.getSkeleTier());
//
//        player.getConfigurationSection(".Slayers.Revenant").set("revlevel", po.getRevLevel());
//        player.getConfigurationSection(".Slayers.Tarantula").set("taralevel", po.getTaraLevel());
//        player.getConfigurationSection(".Slayers.Sven").set("svenlevel", po.getSvenLevel());
//        player.getConfigurationSection(".Slayers.Skeletor").set("skelelevel", po.getSkeleLevel());
//
//        player.getConfigurationSection(".Slayers.Revenant").set("revrng", po.getRevRng());
//        player.getConfigurationSection(".Slayers.Tarantula").set("tararng", po.getTaraRng());
//        player.getConfigurationSection(".Slayers.Sven").set("svenrng", po.getSvenRng());
//        player.getConfigurationSection(".Slayers.Skeletor").set("skelerng", po.getSkeleRng());
//        player.getConfigurationSection(".Slayers.TotalStats").set("totalrng", po.getTotalRng());
//
//        player.getConfigurationSection(".Slayers.Revenant").set("revspent", po.getRevSpent());
//        player.getConfigurationSection(".Slayers.Tarantula").set("taraspent", po.getTaraSpent());
//        player.getConfigurationSection(".Slayers.Sven").set("svenspent", po.getSvenSpent());
//        player.getConfigurationSection(".Slayers.Skeletor").set("skelespent", po.getSkeleSpent());
//        player.getConfigurationSection(".Slayers.TotalStats").set("totalspent", po.getTotalSpent());
//
//        ConfigurationSection farming = player.getConfigurationSection(".Skills.Farming");
//        ConfigurationSection mining = player.getConfigurationSection(".Skills.Mining");
//        ConfigurationSection combat = player.getConfigurationSection(".Skills.Combat");
//        ConfigurationSection foraging = player.getConfigurationSection(".Skills.Foraging");
//        ConfigurationSection fishing = player.getConfigurationSection(".Skills.Fishing");
//        ConfigurationSection enchanting = player.getConfigurationSection(".Skills.Enchanting");
//        ConfigurationSection alchemy = player.getConfigurationSection(".Skills.Alchemy");
//        ConfigurationSection carpentry = player.getConfigurationSection(".Skills.Carpentry");
//        ConfigurationSection runecrafting = player.getConfigurationSection(".Skills.Runecrafting");
//        ConfigurationSection taming = player.getConfigurationSection(".Skills.Taming");
//
//        farming.set("level", po.getFarmingLevel());
//        farming.set("currentXP", po.getFarmingXP());
//        farming.set("neededXP", po.getFarmingNeeded());
//        farming.set("totalXP", po.getFarmingTotal());
//        mining.set("level", po.getMiningLevel());
//        mining.set("currentXP", po.getMiningXP());
//        mining.set("neededXP", po.getMiningNeeded());
//        mining.set("totalXP", po.getMiningTotal());
//        combat.set("level", po.getCombatLevel());
//        combat.set("currentXP", po.getCombatXP());
//        combat.set("neededXP", po.getCombatNeeded());
//        combat.set("totalXP", po.getCombatTotal());
//        foraging.set("level", po.getForagingLevel());
//        foraging.set("currentXP", po.getForagingXP());
//        foraging.set("neededXP", po.getForagingNeeded());
//        foraging.set("totalXP", po.getForagingTotal());
//        fishing.set("level", po.getFishingLevel());
//        fishing.set("currentXP", po.getFishingXP());
//        fishing.set("neededXP", po.getFishingNeeded());
//        fishing.set("totalXP", po.getFishingTotal());
//        enchanting.set("level", po.getEnchantingLevel());
//        enchanting.set("currentXP", po.getEnchantingXP());
//        enchanting.set("neededXP", po.getEnchantingNeeded());
//        enchanting.set("totalXP", po.getEnchantingTotal());
//        alchemy.set("level", po.getAlchemyLevel());
//        alchemy.set("currentXP", po.getAlchemyXP());
//        alchemy.set("neededXP", po.getAlchemyNeeded());
//        alchemy.set("totalXP", po.getAlchemyTotal());
//        carpentry.set("level", po.getCarpentryLevel());
//        carpentry.set("currentXP", po.getCarpentryXP());
//        carpentry.set("neededXP", po.getCarpentryNeeded());
//        carpentry.set("totalXP", po.getCarpentryTotal());
//        runecrafting.set("level", po.getRunecraftingLevel());
//        runecrafting.set("currentXP", po.getRunecraftingXP());
//        runecrafting.set("neededXP", po.getRunecraftingNeeded());
//        runecrafting.set("totalXP", po.getRunecraftingTotal());
//        taming.set("level", po.getTamingLevel());
//        taming.set("currentXP", po.getTamingXP());
//        taming.set("neededXP", po.getTamingNeeded());
//        taming.set("totalXP", po.getTamingTotal());
//
//        plugin.db.savePlayers();
//        Utils.broadCast("[DEBUG] Database saved for " + plr.getName());
//    }

//    public static boolean createPlayerData(Player plr, String uuid, FileConfiguration db) {
//
//        String[] profile_fruits = {"Grapes", "Watermelon", "Mango", "Peach", "Apple", "Pear", "Kiwi"};
//
//        if (!db.isConfigurationSection(uuid)) {
//            try {
//                db.createSection(uuid);
//                db.set(uuid + ".IGN", plr.getName());
//                db.getConfigurationSection(uuid).createSection("Information");
//                db.getConfigurationSection(uuid + ".Information").set("currentMinionsUsed", 0);
//                db.getConfigurationSection(uuid + ".Information").set("maxMinions", 3);
//                db.getConfigurationSection(uuid + ".Information").set("fairySouls", 0);
//                db.getConfigurationSection(uuid + ".Information").set("bank", (double) 0);
//                db.getConfigurationSection(uuid + ".Information").set("purse", (double) 0);
//
//                db.getConfigurationSection(uuid).createSection("Stats");
//                db.getConfigurationSection(uuid + ".Stats").set("health", 100);
//                db.getConfigurationSection(uuid + ".Stats").set("defense", 0);
//                db.getConfigurationSection(uuid + ".Stats").set("strength", 0);
//                db.getConfigurationSection(uuid + ".Stats").set("speed", 100);
//                db.getConfigurationSection(uuid + ".Stats").set("critchance", 30);
//                db.getConfigurationSection(uuid + ".Stats").set("critdamage", 50);
//                db.getConfigurationSection(uuid + ".Stats").set("atkSpeed", 0);
//                db.getConfigurationSection(uuid + ".Stats").set("intel", 100);
//                db.getConfigurationSection(uuid + ".Stats").set("seacreaturechance", 20);
//                db.getConfigurationSection(uuid + ".Stats").set("magicfind", 0);
//                db.getConfigurationSection(uuid + ".Stats").set("petluck", 0);
//
//                // Database Category Creation
//                db.getConfigurationSection(uuid).createSection("Database");
//                db.getConfigurationSection(uuid + ".Database").createSection("statOverride");
//                db.getConfigurationSection(uuid + ".Database").createSection("blockBreak");
//                db.getConfigurationSection(uuid + ".Database").createSection("vanished");
//
//                // Slayer Category Creation
//                db.getConfigurationSection(uuid).createSection("Slayers");
//                db.getConfigurationSection(uuid + ".Slayers").createSection("Revenant");
//                db.getConfigurationSection(uuid + ".Slayers").createSection("Tarantula");
//                db.getConfigurationSection(uuid + ".Slayers").createSection("Sven");
//                db.getConfigurationSection(uuid + ".Slayers").createSection("Skeletor");
//                db.getConfigurationSection(uuid + ".Slayers").createSection("TotalStats");
//
//                db.getConfigurationSection(uuid + ".Slayers.Revenant").set("revenantkills", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Tarantula").set("tarantulakills", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Sven").set("svenkills", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Skeletor").set("skeletorkills", 0);
//                db.getConfigurationSection(uuid + ".Slayers.TotalStats").set("totalkills", 0);
//
//                db.getConfigurationSection(uuid + ".Slayers.Revenant").set("revtier", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Tarantula").set("taratier", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Sven").set("sventier", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Skeletor").set("skeletier", 0);
//
//                db.getConfigurationSection(uuid + ".Slayers.Revenant").set("revlevel", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Tarantula").set("taralevel", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Sven").set("svenlevel", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Skeletor").set("skelelevel", 0);
//
//                db.getConfigurationSection(uuid + ".Slayers.Revenant").set("revrng", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Tarantula").set("tararng", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Sven").set("svenrng", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Skeletor").set("skelerng", 0);
//                db.getConfigurationSection(uuid + ".Slayers.TotalStats").set("totalrng", 0);
//
//                db.getConfigurationSection(uuid + ".Slayers.Revenant").set("revspent", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Tarantula").set("taraspent", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Sven").set("svenspent", 0);
//                db.getConfigurationSection(uuid + ".Slayers.Skeletor").set("skelespent", 0);
//                db.getConfigurationSection(uuid + ".Slayers.TotalStats").set("totalspent", 0);
//
//                db.set(uuid + ".Profile", profile_fruits[(int) Math.floor(Math.random() * profile_fruits.length)]);
//
//                db.getConfigurationSection(uuid).createSection("Skills");
//                String[] skills = {"Farming", "Mining", "Combat", "Foraging", "Fishing", "Enchanting", "Alchemy", "Carpentry", "Runecrafting", "Taming"};
//                for (String skill : skills) {
//                    db.getConfigurationSection(uuid + ".Skills").createSection(skill);
//                    db.getConfigurationSection(uuid + ".Skills." + skill).set("level", 0);
//                    db.getConfigurationSection(uuid + ".Skills." + skill).set("totalXP", 0);
//                    db.getConfigurationSection(uuid + ".Skills." + skill).set("currentXP", 0);
//                    db.getConfigurationSection(uuid + ".Skills." + skill).set("neededXP", 50);
//                }
//                db.getConfigurationSection(uuid).createSection("Collections");
//
//                String[] farmingCollection = {"Wheat", "Carrot", "Potato", "Pumpkin", "Melon", "Seeds", "Mushroom",
//                        "Cocoa Beans", "Cactus", "Sugar Cane", "Feather", "Leather", "Raw Porkchop", "Raw Chicken",
//                        "Mutton", "Raw Rabbit", "Nether Wart"};
//                db.getConfigurationSection(uuid + ".Collections").createSection("Farming");
//
//                for (String category : farmingCollection) {
//                    db.getConfigurationSection(uuid + ".Collections.Farming").createSection(category);
//                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("level", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("totalXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("currentXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("neededXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("totalGathered", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Farming." + category).set("level", 0);
//                }
//
//                String[] miningCollection = {"Cobblestone", "Coal", "Iron Ingot", "Gold Ingot", "Diamond",
//                        "Lapis Lazuli", "Emerald", "Redstone", "Nether Quartz", "Obsidian", "Glowstone", "Gravel",
//                        "Ice", "Netherrack", "Sand", "Endstone"};
//                db.getConfigurationSection(uuid + ".Collections").createSection("Mining");
//
//                for (String category : miningCollection) {
//                    db.getConfigurationSection(uuid + ".Collections.Mining").createSection(category);
//                    db.getConfigurationSection(uuid + ".Collections.Mining." + category).set("level", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Mining." + category).set("totalXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Mining." + category).set("currentXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Mining." + category).set("neededXP", 0);
//                }
//
//                String[] combatCollection = {"Rotten Flesh", "Bone", "String", "Spider Eye", "Gunpowder",
//                        "Ender Pearl", "Ghast Tear", "Slimeball", "Blaze Rod", "Magma Cream"};
//                db.getConfigurationSection(uuid + ".Collections").createSection("Combat");
//
//                for (String category : combatCollection) {
//                    db.getConfigurationSection(uuid + ".Collections.Combat").createSection(category);
//                    db.getConfigurationSection(uuid + ".Collections.Combat." + category).set("level", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Combat." + category).set("totalXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Combat." + category).set("currentXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Combat." + category).set("neededXP", 0);
//                }
//
//                String[] foragingCollection = {"Oak Wood", "Spruce Wood", "Birch Wood", "Dark Oak Wood", "Acacia Wood",
//                        "Jungle Wood"};
//                db.getConfigurationSection(uuid + ".Collections").createSection("Foraging");
//
//                for (String category : foragingCollection) {
//                    db.getConfigurationSection(uuid + ".Collections.Foraging").createSection(category);
//                    db.getConfigurationSection(uuid + ".Collections.Foraging." + category).set("level", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Foraging." + category).set("totalXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Foraging." + category).set("currentXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Foraging." + category).set("neededXP", 0);
//                }
//
//                String[] fishingCollection = {"Raw Fish", "Raw Salmon", "Clownfish", "Pufferfish", "Prismarine Shard",
//                        "Prismarine Crystal", "Clay", "Lily Pad", "Ink Sack", "Sponge"};
//                db.getConfigurationSection(uuid + ".Collections").createSection("Fishing");
//
//                for (String category : fishingCollection) {
//                    db.getConfigurationSection(uuid + ".Collections.Fishing").createSection(category);
//                    db.getConfigurationSection(uuid + ".Collections.Fishing." + category).set("level", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Fishing." + category).set("totalXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Fishing." + category).set("currentXP", 0);
//                    db.getConfigurationSection(uuid + ".Collections.Fishing." + category).set("neededXP", 0);
//                }
//
//                return true;
//            } catch (Exception e) {
//                Bukkit.getConsoleSender().sendMessage(Utils.chat(e.toString()));
//                return false;
//            }
//        } else
//            return true;
//    }

}