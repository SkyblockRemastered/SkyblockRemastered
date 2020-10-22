package xyz.apollo30.skyblockremastered.managers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
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

    public void savePlayerData(Player plr) {


        PlayerObject po = playerObjects.get(plr);

        Document tempDoc;
        Document tempDoc2 = null;
        Document doc = new Document("uuid", plr.getUniqueId().toString());
        doc.append("IGN", plr.getName());
        tempDoc = new Document("bank", (double) po.getBank())
                .append("purse", (double) po.getPurse())
                .append("gems", po.getGems());
        doc.append("information", tempDoc);

        tempDoc = new Document("health", po.getHealth())
                .append("defense", po.getDefense())
                .append("strength", po.getStrength())
                .append("speed", po.getSpeed())
                .append("critChance", po.getCrit_chance())
                .append("critDamage", po.getCrit_damage())
                .append("atkSpeed", po.getAtk_speed())
                .append("intel", po.getIntelligence())
                .append("seaCreatureChance", po.getSea_creature_chance())
                .append("magicFind", po.getMagic_find())
                .append("petLuck", po.getPet_luck());

        doc.append("Stats", tempDoc);

        tempDoc = new Document("statOverride", po.isStatOverride())
                .append("blockBreak", po.isBlockBreak())
                .append("vanished", po.isVanished());
        doc.append("database", tempDoc);
        tempDoc = new Document("Revenant", new Document("revenantkills", po.getRevenantKills())
                .append("revtier", po.getRevTier())
                .append("revlevel", po.getRevLevel())
                .append("revrng", po.getRevRng())
                .append("revspent", po.getRevSpent()))
                .append("Tarantula", new Document("tarantulakills", po.getTarantulaKills())
                        .append("taratier", po.getTaraTier())
                        .append("taralevel", po.getTaraLevel())
                        .append("tararng", po.getTaraRng())
                        .append("taraspent", po.getTaraSpent()))
                .append("Sven", new Document("svenkills", po.getSvenKills())
                        .append("sventier", po.getSvenTier())
                        .append("svenlevel", po.getSvenLevel())
                        .append("svenrng", po.getSvenRng())
                        .append("svenspent", po.getSvenSpent()))
                .append("Skeletor", new Document("skeletorkills", po.getSkeletorKills())
                        .append("skeletier", po.getSkeleTier())
                        .append("skelelevel", po.getSkeleLevel())
                        .append("skelerng", po.getSkeleRng())
                        .append("skelespent", po.getSkeleSpent()))
                .append("TotalStats", new Document("totalkills", po.getTotalKills())
                        .append("totalrng", po.getTotalRng())
                        .append("totalspent", po.getTotalSpent()));

        doc.append("Slayers", tempDoc);

        /*
        "level", po.getFarmingLevel())
                .append("totalXP", po.getFarmingTotal())
                .append("currentXP", po.getFarmingXP())
                .append("neededXP", po.getFarmingNeeded())
         */

        tempDoc = new Document();
        tempDoc.append("Farming", new Document("level", po.getFarmingLevel())
                .append("totalXP", po.getFarmingTotal())
                .append("currentXP", po.getFarmingXP())
                .append("neededXP", po.getFarmingNeeded()))
        .append("Mining", new Document("level", po.getMiningLevel())
                .append("totalXP", po.getMiningTotal())
                .append("currentXP", po.getMiningXP())
                .append("neededXP", po.getMiningNeeded()))
        .append("Combat", new Document("level", po.getCombatLevel())
                .append("totalXP", po.getCombatTotal())
                .append("currentXP", po.getCombatXP())
                .append("neededXP", po.getCombatNeeded()))
        .append("Foraging", new Document("level", po.getForagingLevel())
                .append("totalXP", po.getForagingTotal())
                .append("currentXP", po.getForagingXP())
                .append("neededXP", po.getForagingNeeded()))
        .append("Fishing", new Document("level", po.getFishingLevel())
                .append("totalXP", po.getFishingTotal())
                .append("currentXP", po.getFishingXP())
                .append("neededXP", po.getFishingNeeded()))
        .append("Enchanting", new Document("level", po.getEnchantingLevel())
                .append("totalXP", po.getEnchantingTotal())
                .append("currentXP", po.getEnchantingXP())
                .append("neededXP", po.getEnchantingNeeded()))
        .append("Alchemy", new Document("level", po.getAlchemyLevel())
                .append("totalXP", po.getAlchemyTotal())
                .append("currentXP", po.getAlchemyXP())
                .append("neededXP", po.getAlchemyNeeded()))
        .append("Carpentry", new Document("level", po.getCarpentryLevel())
                .append("totalXP", po.getCarpentryTotal())
                .append("currentXP", po.getCarpentryXP())
                .append("neededXP", po.getCarpentryNeeded()))
        .append("Runecrafting", new Document("level", po.getRunecraftingLevel())
                .append("totalXP", po.getRunecraftingTotal())
                .append("currentXP", po.getRunecraftingXP())
                .append("neededXP", po.getRunecraftingNeeded()))
        .append("Taming", new Document("level", po.getTamingLevel())
                .append("totalXP", po.getTamingTotal())
                .append("currentXP", po.getTamingXP())
                .append("neededXP", po.getTamingNeeded()));

        doc.append("Skills", tempDoc);

        FileConfiguration db = plugin.db.getPlayers();
        if (db.isConfigurationSection(plr.getUniqueId().toString())) {
            createPlayerData(plr);
        }

        Utils.broadCast("Database saved for " + plr.getName());
    }
    //

    public static void createPlayerData(Player plr) {

        try {
            PlayerObject po = new PlayerObject();
            FindIterable<Document> findResult = MongoUtils.getInstance().getPlayerCollection().find(new Document("uuid", plr.getUniqueId().toString()));
            if (findResult == null) {
                Utils.broadCast("Database created for " + plr.getName());
                buildPlayerData(plr);
                createPlayerData(plr);
                return;
            }
            Document doc = findResult.first();
            if (playerObjects.get(plr) != null) return;

            po.setIgn(plr.getName());
            po.setUuid(plr.getUniqueId().toString());

            po.setBank(doc.getInteger("Information.bank"));
            po.setPurse(doc.getInteger("Information.purse"));
            po.setGems(doc.getInteger("Information.gems"));

            po.setStatOverride(doc.getBoolean("Database.statOverride"));
            po.setBlockBreak(doc.getBoolean("Database.blockBreak"));
            po.setVanished(doc.getBoolean("Database.vanished"));

            po.setHealth(doc.getInteger("Stats.health"));
            po.setDefense(doc.getInteger("Stats.defense"));
            po.setStrength(doc.getInteger("Stats.strength"));
            po.setSpeed(doc.getInteger("Stats.speed"));
            po.setCrit_chance(doc.getInteger("Stats.critChance"));
            po.setCrit_damage(doc.getInteger("Stats.critDamage"));
            po.setAtk_speed(doc.getInteger("Stats.atkSpeed"));
            po.setIntelligence(doc.getInteger("Stats.intel"));
            po.setSea_creature_chance(doc.getInteger("Stats.seaCreatureChance"));
            po.setMagic_find(doc.getInteger("Stats.magicFind"));
            po.setPet_luck(doc.getInteger("Stats.petLuck"));

            po.setRevenantKills(doc.getInteger("Slayers.Revenant.revenantkills"));
            po.setTarantulaKills(doc.getInteger("Slayers.Tarantula.revenantkills"));
            po.setSvenKills(doc.getInteger("Slayers.Sven.svenkills"));
            po.setSkeletorKills(doc.getInteger("Slayers.Skeletor.skeletorkills"));
            po.setTotalKills(doc.getInteger("Slayers.TotalStats.totalkills"));

            po.setRevTier(doc.getInteger("Slayers.Revenant.revtier"));
            po.setTaraTier(doc.getInteger("Slayers.Tarantula.taratier"));
            po.setSvenTier(doc.getInteger("Slayers.Sven.sventier"));
            po.setSkeleTier(doc.getInteger("Slayers.Skeletor.skeletier"));

            po.setRevLevel(doc.getInteger("Slayers.Revenant.revlevel"));
            po.setTaraLevel(doc.getInteger("Slayers.Tarantula.taralevel"));
            po.setSvenLevel(doc.getInteger("Slayers.Sven.svenlevel"));
            po.setSkeleLevel(doc.getInteger("Slayers.skelelevel.skelelevel"));

            po.setRevRng(doc.getInteger("Slayers.Revenant.revrng"));
            po.setTaraRng(doc.getInteger("Slayers.Tarantula.tararng"));
            po.setSvenRng(doc.getInteger("Slayers.Sven.svenrng"));
            po.setSkeleRng(doc.getInteger("Slayers.Skeletor.skelerng"));
            po.setTotalRng(doc.getInteger("Slayers.TotalStats.totalrng"));

            po.setRevSpent(doc.getDouble("Slayers.Revenant.revspent"));
            po.setTaraSpent(doc.getDouble("Slayers.Tarantula.taraspent"));
            po.setSvenSpent(doc.getDouble("Slayers.Sven.svenspent"));
            po.setSkeleSpent(doc.getDouble("Slayers.Skeletor.skelespent"));
            po.setTotalSpent(doc.getDouble("Slayers.TotalStats.totalspent"));

            po.setFarmingLevel(doc.getInteger("Skills.Farming.level"));
            po.setFarmingXP(doc.getInteger("Skills.Farming.currentXP"));
            po.setFarmingNeeded(doc.getInteger("Skills.Farming.neededXP"));
            po.setFarmingTotal(doc.getInteger("Skills.Farming.totalXP"));
            po.setMiningLevel(doc.getInteger("Skills.Mining.level"));
            po.setMiningXP(doc.getInteger("Skills.Mining.currentXP"));
            po.setMiningNeeded(doc.getInteger("Skills.Mining.neededXP"));
            po.setMiningTotal(doc.getInteger("Skills.Mining.totalXP"));
            po.setCombatLevel(doc.getInteger("Skills.Combat.level"));
            po.setCombatXP(doc.getInteger("Skills.Combat.currentXP"));
            po.setCombatNeeded(doc.getInteger("Skills.Combat.neededXP"));
            po.setCombatTotal(doc.getInteger("Skills.Combat.totalXP"));
            po.setForagingLevel(doc.getInteger("Skills.Foraging.level"));
            po.setForagingXP(doc.getInteger("Skills.Foraging.currentXP"));
            po.setForagingNeeded(doc.getInteger("Skills.Foraging.neededXP"));
            po.setForagingTotal(doc.getInteger("Skills.Foraging.totalXP"));
            po.setFishingLevel(doc.getInteger("Skills.Fishing.level"));
            po.setFishingXP(doc.getInteger("Skills.Fishing.currentXP"));
            po.setFishingNeeded(doc.getInteger("Skills.Fishing.neededXP"));
            po.setFishingTotal(doc.getInteger("Skills.Fishing.totalXP"));
            po.setEnchantingLevel(doc.getInteger("SSkills.Enchanting.level"));
            po.setEnchantingXP(doc.getInteger("Skills.Enchanting.currentXP"));
            po.setEnchantingNeeded(doc.getInteger("Skills.Enchanting.neededXP"));
            po.setEnchantingTotal(doc.getInteger("Skills.Enchanting.totalXP"));
            po.setAlchemyLevel(doc.getInteger("Skills.Alchemy.level"));
            po.setAlchemyXP(doc.getInteger("Skills.Alchemy.currentXP"));
            po.setAlchemyNeeded(doc.getInteger("Skills.Alchemy.neededXP"));
            po.setAlchemyTotal(doc.getInteger("Skills.Alchemy.totalXP"));
            po.setCarpentryLevel(doc.getInteger("Skills.Carpentry.level"));
            po.setCarpentryXP(doc.getInteger("Skills.Carpentry.currentXP"));
            po.setCarpentryNeeded(doc.getInteger("Skills.Carpentry.neededXP"));
            po.setCarpentryTotal(doc.getInteger("Skills.Carpentry.totalXP"));
            po.setRunecraftingLevel(doc.getInteger("Skills.Runecrafting.level"));
            po.setRunecraftingXP(doc.getInteger("Skills.Runecrafting.currentXP"));
            po.setRunecraftingNeeded(doc.getInteger("Skills.Runecrafting.neededXP"));
            po.setRunecraftingTotal(doc.getInteger("Skills.Runecrafting.totalXP"));
            po.setTamingLevel(doc.getInteger("Skills.Taming.level"));
            po.setTamingXP(doc.getInteger("Skills.Taming.currentXP"));
            po.setTamingNeeded(doc.getInteger("Skills.Taming.neededXP"));
            po.setTamingTotal(doc.getInteger("Skills.Taming.totalXP"));

            playerObjects.put(plr, po);
            Utils.broadCast("[DEBUG] Database created for " + plr.getName());
        } catch (Exception ignored) {

        }
    }

    //



    public static boolean buildPlayerData(Player plr) {

        if (MongoUtils.getInstance().getPlayerCollection().find(new Document("uuid", plr.getUniqueId().toString())) == null)
            return true;

        String[] profile_fruits = {"Grapes", "Watermelon", "Mango", "Peach", "Apple", "Pear", "Kiwi"};
        Document tempDoc;
        Document tempDoc2 = null;
        Document doc = new Document("uuid", plr.getUniqueId().toString());
        doc.append("IGN", plr.getName());
        tempDoc = new Document("currentMinionsUsed", 0)
                .append("maxMinions", 3)
                .append("fairySouls", 0)
                .append("bank", (double) 0)
                .append("purse", (double) 0);
        doc.append("information", tempDoc);

        tempDoc = new Document("health", 100)
                .append("defense", 0)
                .append("strength", 0)
                .append("speed", 100)
                .append("critChance", 30)
                .append("critDamage", 50)
                .append("atkSpeed", 0)
                .append("intel", 100)
                .append("seaCreatureChance", 20)
                .append("magicFind", 0)
                .append("petLuck", 0);

        doc.append("Stats", tempDoc);

        tempDoc = new Document("statOverride", false)
                .append("blockBreak", false)
                .append("vanished", false);
        doc.append("database", tempDoc);
        tempDoc = new Document("Revenant", new Document("revenantkills", 0)
            .append("revtier", 0)
            .append("revlevel", 0)
            .append("revrng", 0)
            .append("revspent", 0))
        .append("Tarantula", new Document("tarantulakills", 0)
            .append("taratier", 0)
            .append("taralevel", 0)
            .append("tararng", 0)
            .append("taraspent", 0))
        .append("Sven", new Document("svenkills", 0)
            .append("sventier", 0)
            .append("svenlevel", 0)
            .append("svenrng", 0)
            .append("svenspent", 0))
        .append("Skeletor", new Document("skeletorkills", 0)
            .append("skeletier", 0)
            .append("skelelevel", 0)
            .append("skelerng", 0)
            .append("skelespent", 0))
        .append("TotalStats", new Document("totalkills", 0)
            .append("totalrng", 0)
            .append("totalspent", 0));

        doc.append("Slayers", tempDoc);
        doc.append("Profile", profile_fruits[(int) Math.floor(Math.random() * profile_fruits.length)]);


        String[] skills = {"Farming", "Mining", "Combat", "Foraging", "Fishing", "Enchanting", "Alchemy", "Carpentry", "Runecrafting", "Taming"};
        tempDoc = new Document();
        for (String skill : skills) {
            tempDoc.append(skill, new Document("level", 0)
                .append("totalXP", 0)
                .append("currentXP", 0)
                .append("neededXP", 0));
        }
        doc.append("Skills", tempDoc);

        HashMap<String, String[]> collections = new HashMap<String, String[]>();
        collections.put("farming", new String[]{"Wheat", "Carrot", "Potato", "Pumpkin", "Melon", "Seeds", "Mushroom",
                "Cocoa Beans", "Cactus", "Sugar Cane", "Feather", "Leather", "Raw Porkchop", "Raw Chicken",
                "Mutton", "Raw Rabbit", "Nether Wart"});
        collections.put("mining", new String[]{"Cobblestone", "Coal", "Iron Ingot", "Gold Ingot", "Diamond",
                "Lapis Lazuli", "Emerald", "Redstone", "Nether Quartz", "Obsidian", "Glowstone", "Gravel",
                "Ice", "Netherrack", "Sand", "Endstone"});
        collections.put("combat", new String[]{"Rotten Flesh", "Bone", "String", "Spider Eye", "Gunpowder",
                "Ender Pearl", "Ghast Tear", "Slimeball", "Blaze Rod", "Magma Cream"});
        collections.put("foraging", new String[]{"Oak Wood", "Spruce Wood", "Birch Wood", "Dark Oak Wood", "Acacia Wood",
                "Jungle Wood"});
        collections.put("fishing", new String[]{"Raw Fish", "Raw Salmon", "Clownfish", "Pufferfish", "Prismarine Shard",
                "Prismarine Crystal", "Clay", "Lily Pad", "Ink Sack", "Sponge"});

        tempDoc = new Document();
        for (Map.Entry<String, String[]> map : collections.entrySet()) {
            for (String coll : map.getValue()) {
                tempDoc2 = new Document("level", 0)
                        .append("totalXP", 0)
                        .append("currentXP", 0)
                        .append("neededXP", 0)
                        .append("totalGathered", 0)
                        .append("level", 0);
            }
            tempDoc.append(map.getKey(), tempDoc2);
        }
        doc.append("Collections", tempDoc);
        MongoUtils.getInstance().getPlayerCollection().insertOne(doc);

        return true;
    }

    public PlayerObject getPlayerData(Player plr) {
        return playerObjects.get(plr);
    }
}
