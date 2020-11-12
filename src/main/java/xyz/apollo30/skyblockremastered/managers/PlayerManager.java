package xyz.apollo30.skyblockremastered.managers;

import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.templates.PlayerTemplate;
import xyz.apollo30.skyblockremastered.utils.MongoUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private final SkyblockRemastered plugin;

    public PlayerManager(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static HashMap<Player, PlayerTemplate> playerObjects = new HashMap<>();

    public static void savePlayerData(Player plr) {

        PlayerTemplate po = playerObjects.get(plr);
        if (po == null) return;

        Document tempDoc;
        Document doc = new Document("UUID", plr.getUniqueId().toString());
        doc.append("IGN", plr.getName());
        tempDoc = new Document("health", po.getBaseHealth())
                .append("defense", po.getBaseDefense())
                .append("strength", po.getBaseStrength())
                .append("attack_speed", po.getBaseAtkSpeed())
                .append("speed", po.getBaseSpeed())
                .append("crit_chance", po.getBaseCritChance())
                .append("crit_damage", po.getBaseCritDamage())
                .append("intelligence", po.getBaseIntelligence())
                .append("sea_creature_chance", po.getBaseSeaCreatureChance())
                .append("magic_find", po.getBaseMagicFind())
                .append("ferocity", po.getBaseFerocity())
                .append("pet_luck", po.getBasePetLuck());
        doc.append("base_stats", tempDoc);

        tempDoc = new Document("fairy_souls_collected", po.getFairySouls()).append("fairy_souls_exchanges", po.getFairySoulsClaimed());
        doc.append("fairy_souls_data", tempDoc);

        doc.append("death_count", po.getTotalDeaths());
        tempDoc = new Document("ender_chest", po.getEnderChest())
                .append("quiver", po.getQuiverBag())
                .append("wardrobe", po.getWardrobe())
                .append("inventory", po.getInventory())
                .append("accessory_bag", po.getAccessoryBag())
                .append("potion_bag", po.getPotionBag());
        doc.append("saved_inventories", tempDoc);

        doc.append("tutorial", new ArrayList<>());
        doc.append("coin_purse", po.getPurse());
        doc.append("bits_purse", po.getBits());
        doc.append("highest_critical_damage", po.getHighestCritDamage());

        tempDoc = new Document("balance", po.getBank())
                .append("transactions", new ArrayList<>());
        doc.append("banking", tempDoc);

        tempDoc = new Document("revenant",
                new Document("progression",
                        new Document("currentLevel", 0)
                                .append("xp", 0)
                                .append("maxLevel", 12)
                                .append("xpForNext", 100))
                        .append("kills",
                                new Document("tier_0", 0)
                                        .append("tier_1", 0)
                                        .append("tier_2", 0)
                                        .append("tier_3", 0)
                                        .append("tier_4", 0))
                        .append("claimed_levels",
                                new Document("level_1", false)
                                        .append("level_2", false)
                                        .append("level_3", false)
                                        .append("level_4", false)
                                        .append("level_5", false)))
                .append("tarantula", new Document("progression",
                        new Document("currentLevel", 0)
                                .append("xp", 0)
                                .append("maxLevel", 12)
                                .append("xpForNext", 100))
                        .append("kills",
                                new Document("tier_0", 0)
                                        .append("tier_1", 0)
                                        .append("tier_2", 0)
                                        .append("tier_3", 0)
                                        .append("tier_4", 0))
                        .append("claimed_levels",
                                new Document("level_1", false)
                                        .append("level_2", false)
                                        .append("level_3", false)
                                        .append("level_4", false)
                                        .append("level_5", false)))
                .append("sven", new Document("progression",
                        new Document("currentLevel", 0)
                                .append("xp", 0)
                                .append("maxLevel", 12)
                                .append("xpForNext", 100))
                        .append("kills",
                                new Document("tier_0", 0)
                                        .append("tier_1", 0)
                                        .append("tier_2", 0)
                                        .append("tier_3", 0)
                                        .append("tier_4", 0))
                        .append("claimed_levels",
                                new Document("level_1", false)
                                        .append("level_2", false)
                                        .append("level_3", false)
                                        .append("level_4", false)
                                        .append("level_5", false)));
        doc.append("slayer_bosses", tempDoc);

        tempDoc = new Document("type", null)
                .append("tier", 0)
                .append("start_timestamp", 0)
                .append("completion_state", 0)
                .append("combat_xp", 0)
                .append("spawn_timestamp", 0)
                .append("killed_timestamp", 0);
        doc.append("slayer_quest", tempDoc);

        doc.append("visited_zones", new ArrayList<>());
        doc.append("pets", new ArrayList<>());

        String[] skills = {"farming", "mining", "combat", "foraging", "fishing", "enchanting", "alchemy", "carpentry", "runecrafting", "taming", "rebirths"};
        tempDoc = new Document();
        for (String skill : skills) {
            tempDoc.append(skill, new Document("level",
                    skill.equals("farming") ? po.getFarmingLevel() :
                            skill.equals("mining") ? po.getMiningLevel() :
                                    skill.equals("combat") ? po.getCombatLevel() :
                                            skill.equals("foraging") ? po.getForagingLevel() :
                                                    skill.equals("fishing") ? po.getFishingLevel() :
                                                            skill.equals("enchanting") ? po.getEnchantingLevel() :
                                                                    skill.equals("alchemy") ? po.getAlchemyLevel() :
                                                                            skill.equals("carpentry") ? po.getCarpentryLevel() :
                                                                                    skill.equals("runecrafting") ? po.getRunecraftingLevel() :
                                                                                            skill.equals("taming") ? po.getTamingLevel() : 0)
                    .append("totalXP", skill.equals("farming") ? po.getFarmingTotal() :
                            skill.equals("mining") ? po.getMiningTotal() :
                                    skill.equals("combat") ? po.getCombatTotal() :
                                            skill.equals("foraging") ? po.getForagingTotal() :
                                                    skill.equals("fishing") ? po.getFishingTotal() :
                                                            skill.equals("enchanting") ? po.getEnchantingTotal() :
                                                                    skill.equals("alchemy") ? po.getAlchemyTotal() :
                                                                            skill.equals("carpentry") ? po.getCarpentryTotal() :
                                                                                    skill.equals("runecrafting") ? po.getRunecraftingTotal() :
                                                                                            skill.equals("taming") ? po.getTamingTotal() : 0)
                    .append("currentXP", skill.equals("farming") ? po.getFarmingXP() :
                            skill.equals("mining") ? po.getMiningXP() :
                                    skill.equals("combat") ? po.getCombatXP() :
                                            skill.equals("foraging") ? po.getForagingXP() :
                                                    skill.equals("fishing") ? po.getFishingXP() :
                                                            skill.equals("enchanting") ? po.getEnchantingXP() :
                                                                    skill.equals("alchemy") ? po.getAlchemyXP() :
                                                                            skill.equals("carpentry") ? po.getCarpentryXP() :
                                                                                    skill.equals("runecrafting") ? po.getRunecraftingXP() :
                                                                                            skill.equals("taming") ? po.getTamingXP() : 0)
                    .append("neededXP", skill.equals("farming") ? po.getFarmingNeeded() :
                            skill.equals("mining") ? po.getMiningNeeded() :
                                    skill.equals("combat") ? po.getCombatNeeded() :
                                            skill.equals("foraging") ? po.getForagingNeeded() :
                                                    skill.equals("fishing") ? po.getFishingNeeded() :
                                                            skill.equals("enchanting") ? po.getEnchantingNeeded() :
                                                                    skill.equals("alchemy") ? po.getAlchemyNeeded() :
                                                                            skill.equals("carpentry") ? po.getCarpentryNeeded() :
                                                                                    skill.equals("runecrafting") ? po.getRunecraftingNeeded() :
                                                                                            skill.equals("taming") ? po.getTamingNeeded() : 0)
                    .append("extraLevel", skill.equals("farming") ? po.getFarmingExtra() :
                            skill.equals("mining") ? po.getMiningExtra() :
                                    skill.equals("combat") ? po.getCombatExtra() :
                                            skill.equals("foraging") ? po.getForagingExtra() :
                                                    skill.equals("fishing") ? po.getFishingExtra() :
                                                            skill.equals("enchanting") ? po.getEnchantingExtra() :
                                                                    skill.equals("alchemy") ? po.getAlchemyExtra() :
                                                                            skill.equals("carpentry") ? po.getCarpentryExtra() :
                                                                                    skill.equals("runecrafting") ? po.getRunecraftingExtra() :
                                                                                            skill.equals("taming") ? po.getTamingExtra() : 0));
        }
        doc.append("skills", tempDoc);

        HashMap<String, String[]> collections = new HashMap<>();
        collections.put("farming", new String[]{"wheat", "carrot", "potato", "pumpkin", "melon", "seeds", "mushroom",
                "cocoa beans", "cactus", "sugar cane", "feather", "leather", "raw porkchop", "raw chicken",
                "mutton", "raw rabbit", "nether rart"});
        collections.put("mining", new String[]{"cobblestone", "coal", "iron ingot", "gold ingot", "diamond",
                "lapis lazuli", "emerald", "redstone", "nether quartz", "obsidian", "glowstone", "gravel",
                "ice", "netherrack", "sand", "endstone"});
        collections.put("combat", new String[]{"rotten flesh", "bone", "string", "spider eye", "gunpowder",
                "ender pearl", "ghast tear", "slimeball", "blaze rod", "magma cream"});
        collections.put("foraging", new String[]{"oak wood", "spruce wood", "birch wood", "dark oak wood", "acacia wood",
                "jungle wood"});
        collections.put("fishing", new String[]{"raw fish", "raw salmon", "clownfish", "pufferfish", "prismarine shard",
                "prismarine crystal", "clay", "lily pad", "ink sack", "sponge"});

        tempDoc = new Document();

        Document tempDoc2 = null;
        for (Map.Entry<String, String[]> map : collections.entrySet()) {
            for (String ignored : map.getValue()) {
                tempDoc2 = new Document("level", 0)
                        .append("totalXP", 0)
                        .append("currentXP", 0)
                        .append("neededXP", 0)
                        .append("totalGathered", 0)
                        .append("level", 0);
            }
            tempDoc.append(map.getKey(), tempDoc2);
        }
        doc.append("collections", tempDoc);

        MongoUtils.insertOrUpdate(new Document("UUID", plr.getUniqueId().toString()), doc);
    }

    public static void createPlayerData(Player plr) {
        FindIterable<Document> findResult = MongoUtils.getInstance().getPlayerCollection().find(new Document("UUID", plr.getUniqueId().toString()));
        if (findResult.first() == null) {
            Utils.broadCast("Database created for " + plr.getName());
            buildPlayerData(plr);
            createPlayerData(plr);
            return;
        }

        Document doc = findResult.first();
        if (doc == null || playerObjects.get(plr) != null) return;

        PlayerTemplate po = new PlayerTemplate();

        po.setIGN(plr.getName());
        po.setUUID(plr.getUniqueId().toString());

        po.setBank(getDouble(doc, "banking.balance"));
        po.setPurse(doc.getDouble("coin_purse"));
        po.setBits(doc.getDouble("bits_purse"));

        po.setBaseHealth(getInteger(doc, "base_stats.health"));
        po.setBaseDefense(getInteger(doc, "base_stats.defense"));
        po.setBaseStrength(getInteger(doc, "base_stats.strength"));
        po.setSpeed(getInteger(doc, "base_stats.speed"));
        po.setBaseCritChance(getInteger(doc, "base_stats.crit_chance"));
        po.setBaseCritDamage(getInteger(doc, "base_stats.crit_damage"));
        po.setBaseAtkSpeed(getInteger(doc, "base_stats.attack_speed"));
        po.setBaseIntelligence(getInteger(doc, "base_stats.intelligence"));
        po.setBaseSeaCreatureChance(getInteger(doc, "base_stats.sea_creature_chance"));
        po.setBaseMagicFind(getInteger(doc, "base_stats.magic_find"));
        po.setBasePetLuck(getInteger(doc, "base_stats.pet_luck"));

        po.setFarmingLevel(getInteger(doc, "skills.farming.level"));
        po.setFarmingXP(getDouble(doc, "skills.farming.currentXP"));
        po.setFarmingNeeded(getDouble(doc, "skills.farming.neededXP"));
        po.setFarmingTotal(getDouble(doc, "skills.farming.totalXP"));
        po.setFarmingExtra(getInteger(doc, "skills.farming.extraLevel"));

        po.setMiningLevel(getInteger(doc, "skills.mining.level"));
        po.setMiningXP(getDouble(doc, "skills.mining.currentXP"));
        po.setMiningNeeded(getDouble(doc, "skills.mining.neededXP"));
        po.setMiningTotal(getDouble(doc, "skills.mining.totalXP"));
        po.setMiningExtra(getInteger(doc, "skills.mining.extraLevel"));

        po.setCombatLevel(getInteger(doc, "skills.combat.level"));
        po.setCombatXP(getDouble(doc, "skills.combat.currentXP"));
        po.setCombatNeeded(getDouble(doc, "skills.combat.neededXP"));
        po.setCombatTotal(getDouble(doc, "skills.combat.totalXP"));
        po.setCombatExtra(getInteger(doc, "skills.combat.extraLevel"));

        po.setForagingLevel(getInteger(doc, "skills.foraging.level"));
        po.setForagingXP(getDouble(doc, "skills.foraging.currentXP"));
        po.setForagingNeeded(getDouble(doc, "skills.foraging.neededXP"));
        po.setForagingTotal(getDouble(doc, "skills.foraging.totalXP"));
        po.setForagingExtra(getInteger(doc, "skills.foraging.extraLevel"));

        po.setFishingLevel(getInteger(doc, "skills.fishing.level"));
        po.setFishingXP(getDouble(doc, "skills.fishing.currentXP"));
        po.setFishingNeeded(getDouble(doc, "skills.fishing.neededXP"));
        po.setFishingTotal(getDouble(doc, "skills.fishing.totalXP"));
        po.setFishingExtra(getInteger(doc, "skills.fishing.extraLevel"));

        po.setEnchantingLevel(getInteger(doc, "skills.enchanting.level"));
        po.setEnchantingXP(getDouble(doc, "skills.enchanting.currentXP"));
        po.setEnchantingNeeded(getDouble(doc, "skills.enchanting.neededXP"));
        po.setEnchantingTotal(getDouble(doc, "skills.enchanting.totalXP"));
        po.setEnchantingExtra(getInteger(doc, "skills.enchanting.extraLevel"));

        po.setAlchemyLevel(getInteger(doc, "skills.alchemy.level"));
        po.setAlchemyXP(getDouble(doc, "skills.alchemy.currentXP"));
        po.setAlchemyNeeded(getDouble(doc, "skills.alchemy.neededXP"));
        po.setAlchemyTotal(getDouble(doc, "skills.alchemy.totalXP"));
        po.setAlchemyExtra(getInteger(doc, "skills.alchemy.extraLevel"));

        po.setCarpentryLevel(getInteger(doc, "skills.carpentry.level"));
        po.setCarpentryXP(getDouble(doc, "skills.carpentry.currentXP"));
        po.setCarpentryNeeded(getDouble(doc, "skills.carpentry.neededXP"));
        po.setCarpentryTotal(getDouble(doc, "skills.carpentry.totalXP"));
        po.setCarpentryExtra(getInteger(doc, "skills.carpentry.extraLevel"));

        po.setRunecraftingLevel(getInteger(doc, "skills.runecrafting.level"));
        po.setRunecraftingXP(getDouble(doc, "skills.runecrafting.currentXP"));
        po.setRunecraftingNeeded(getDouble(doc, "skills.runecrafting.neededXP"));
        po.setRunecraftingTotal(getDouble(doc, "skills.runecrafting.totalXP"));
        po.setRunecraftingExtra(getInteger(doc, "skills.runecrafting.extraLevel"));

        po.setTamingLevel(getInteger(doc, "skills.taming.level"));
        po.setTamingXP(getDouble(doc, "skills.taming.currentXP"));
        po.setTamingNeeded(getDouble(doc, "skills.taming.neededXP"));
        po.setTamingTotal(getDouble(doc, "skills.taming.totalXP"));
        po.setTamingExtra(getInteger(doc, "skills.taming.extraLevel"));

        playerObjects.put(plr, po);
    }

    public static void buildPlayerData(Player plr) {
        if (MongoUtils.getInstance().getPlayerCollection().find(new Document("UUID", plr.getUniqueId().toString())).first() != null)
            return;

        String[] profileFruits = {
                "Grapes",
                "Watermelon",
                "Mango",
                "Peach",
                "Apple",
                "Pear",
                "Kiwi",
                "Berry",
                "Avocado",
                "Papaya",
                "Cherry",
                "Orange",
                "Apple",
                "Grapefruit",
                "Pear",
                "Strawberry",
                "Banana",
                "Pineapple"
        };

        Document tempDoc;
        Document tempDoc2 = null;
        Document doc = new Document("UUID", plr.getUniqueId().toString());
        doc.append("IGN", plr.getName());
        tempDoc = new Document("health", 100)
                .append("defense", 0)
                .append("strength", 0)
                .append("speed", 100)
                .append("attack_speed", 0)
                .append("crit_chance", 30)
                .append("crit_damage", 50)
                .append("intelligence", 100)
                .append("sea_creature_chance", 20)
                .append("magic_find", 0)
                .append("ferocity", 0)
                .append("pet_luck", 0);
        doc.append("base_stats", tempDoc);

        tempDoc = new Document("fairy_souls_collected", 0).append("fairy_souls_exchanges", 0);
        doc.append("fairy_souls_data", tempDoc);

        doc.append("death_count", 0);
        tempDoc = new Document("ender_chest", "")
                .append("quiver", "")
                .append("wardrobe", "")
                .append("inventory", "")
                .append("accessory_bag", "")
                .append("potion_bag", "");
        doc.append("saved_inventories", tempDoc);

        doc.append("tutorial", new ArrayList<>());
        doc.append("coin_purse", (double) 0);
        doc.append("bits_purse", (double) 0);
        doc.append("highest_critical_damage", (double) 0);

        tempDoc = new Document("balance", (double) 0)
                .append("transactions", new ArrayList<>());
        doc.append("banking", tempDoc);

        tempDoc = new Document("revenant",
                new Document("progression",
                        new Document("currentLevel", 0)
                                .append("xp", (double) 0)
                                .append("maxLevel", 12)
                                .append("xpForNext", (double) 100))
                        .append("kills",
                                new Document("tier_0", 0)
                                        .append("tier_1", 0)
                                        .append("tier_2", 0)
                                        .append("tier_3", 0)
                                        .append("tier_4", 0))
                        .append("claimed_levels",
                                new Document("level_1", false)
                                        .append("level_2", false)
                                        .append("level_3", false)
                                        .append("level_4", false)
                                        .append("level_5", false)))
                .append("tarantula", new Document("progression",
                        new Document("currentLevel", 0)
                                .append("xp", (double) 0)
                                .append("maxLevel", 12)
                                .append("xpForNext", (double) 100))
                        .append("kills",
                                new Document("tier_0", 0)
                                        .append("tier_1", 0)
                                        .append("tier_2", 0)
                                        .append("tier_3", 0)
                                        .append("tier_4", 0))
                        .append("claimed_levels",
                                new Document("level_1", false)
                                        .append("level_2", false)
                                        .append("level_3", false)
                                        .append("level_4", false)
                                        .append("level_5", false)))
                .append("sven", new Document("progression",
                        new Document("currentLevel", 0)
                                .append("xp", (double) 0)
                                .append("maxLevel", 12)
                                .append("xpForNext", (double) 100))
                        .append("kills",
                                new Document("tier_0", 0)
                                        .append("tier_1", 0)
                                        .append("tier_2", 0)
                                        .append("tier_3", 0)
                                        .append("tier_4", 0))
                        .append("claimed_levels",
                                new Document("level_1", false)
                                        .append("level_2", false)
                                        .append("level_3", false)
                                        .append("level_4", false)
                                        .append("level_5", false)));
        doc.append("slayer_bosses", tempDoc);

        tempDoc = new Document("type", null)
                .append("tier", 0)
                .append("start_timestamp", (long) 0)
                .append("completion_state", 0)
                .append("combat_xp", (double) 0)
                .append("spawn_timestamp", 0)
                .append("killed_timestamp", (long) 0);
        doc.append("slayer_quest", tempDoc);

        doc.append("visited_zones", new ArrayList<>());
        doc.append("profile", profileFruits[(int) Math.floor(Math.random() * profileFruits.length)]);
        doc.append("pets", new ArrayList<>());

        String[] skills = {"farming", "mining", "combat", "foraging", "fishing", "enchanting", "alchemy", "carpentry", "runecrafting", "taming", "rebirths"};

        Document skillDoc = new Document();
        for (String skill : skills) {
            skillDoc.append(skill, new Document("level", 0)
                    .append("totalXP", (double) 0)
                    .append("currentXP", (double) 0)
                    .append("neededXP", (double) 0)
                    .append("extraLevel", 0));
        }
        doc.append("skills", skillDoc);

        HashMap<String, String[]> collections = new HashMap<>();
        collections.put("farming", new String[]{"wheat", "carrot", "potato", "pumpkin", "melon", "seeds", "mushroom",
                "cocoa_beans", "cactus", "sugar_cane", "feather", "leather", "raw_porkchop", "raw_chicken",
                "mutton", "raw_rabbit", "nether_wart"});
        collections.put("mining", new String[]{"cobblestone", "coal", "iron_ingot", "gold_ingot", "diamond",
                "lapis_lazuli", "emerald", "redstone", "nether_quartz", "obsidian", "glowstone", "gravel",
                "ice", "netherrack", "sand", "endstone"});
        collections.put("combat", new String[]{"rotten_flesh", "bone", "string", "spider_eye", "gunpowder",
                "ender_pearl", "ghast_tear", "slimeball", "blaze_rod", "magma_cream"});
        collections.put("foraging", new String[]{"oak_wood", "spruce_wood", "birch_wood", "dark_oak_wood", "acacia_wood",
                "jungle_wood"});
        collections.put("fishing", new String[]{"raw_fish", "raw_salmon", "clownfish", "pufferfish", "prismarine_shard",
                "prismarine_crystal", "clay", "lily_pad", "ink_sack", "sponge"});

        Document doc2 = new Document();
        for (Map.Entry<String, String[]> map : collections.entrySet()) {
            Document doc4 = new Document();
            for (String item : map.getValue()) {
                doc4.append(item, new Document("level", 0)
                        .append("totalXP", (double) 0)
                        .append("currentXP", (double) 0)
                        .append("neededXP", (double) 0)
                        .append("totalGathered", (double) 0));
            }
            doc2.append(map.getKey(), doc4);
        }
        doc.append("collections", doc2);
        MongoUtils.getInstance().getPlayerCollection().insertOne(doc);

    }

    public PlayerTemplate getPlayerData(Player plr) {
        return playerObjects.get(plr);
    }

    private static Object get(Document document, String dots)
            throws MongoException {

        String[] keys = dots.split("\\.");
        Document doc = document;

        for (int i = 0; i < keys.length - 1; i++) {
            Object o = doc.get(keys[i]);
            if (!(o instanceof Document)) {
                throw new MongoException(String.format("Field '%s' does not exist or s not a Document", keys[i]));
            }
            doc = (Document) o;
        }

        return doc.get(keys[keys.length - 1]);
    }

    private static Integer getInteger(Document document, String dots)
            throws MongoException {

        String[] keys = dots.split("\\.");
        Document doc = document;

        for (int i = 0; i < keys.length - 1; i++) {
            Object o = doc.get(keys[i]);
            if (!(o instanceof Document)) {
                throw new MongoException(String.format("Field '%s' does not exist or s not a Document", keys[i]));
            }
            doc = (Document) o;
        }

        return doc.getInteger(keys[keys.length - 1]);
    }

    private static Double getDouble(Document document, String dots)
            throws MongoException {

        String[] keys = dots.split("\\.");
        Document doc = document;

        for (int i = 0; i < keys.length - 1; i++) {
            Object o = doc.get(keys[i]);
            if (!(o instanceof Document)) {
                throw new MongoException(String.format("Field '%s' does not exist or s not a Document", keys[i]));
            }
            doc = (Document) o;
        }

        return doc.getDouble(keys[keys.length - 1]);
    }
}