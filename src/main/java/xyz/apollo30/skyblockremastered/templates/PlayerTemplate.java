package xyz.apollo30.skyblockremastered.templates;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PlayerTemplate {

    // Useless shit that I will never use in my life.
    String IGN;
    String UUID;

    // Armor & Held Item Detection
    ItemStack helmet;
    ItemStack chestplate;
    ItemStack leggings;
    ItemStack boots;
    ItemStack heldItem;

    // Settings
    Constants.Rarities selectedRarity = Constants.Rarities.COMMON;
    boolean fullInventoryAlert = true;

    double purse = 100;
    double bank = 0;
    double bits = 0;

    // Stats
    int itemHealth = 0;
    int itemMaxHealth = 0;
    int itemDefense = 0;
    int itemStrength = 0;
    int itemSpeed = 0;
    int itemCritChance = 0;
    int itemCritDamage = 0;
    int itemAtkSpeed = 0;
    int itemIntelligence = 0;
    int itemMaxIntelligence = 0;
    int itemSeaCreatureChance = 0;
    int itemMagicFind = 0;
    int itemPetLuck = 0;
    int itemTrueDamage = 0;
    int itemTrueDefense = 0;
    int itemFerocity = 0;

    int baseHealth = 100;
    int health = 0;
    int absorptionHealth = 0;
    int extraHealth = 0;
    int maxHealth = baseHealth;
    int baseDefense = 0;
    int defense = 0;
    int baseStrength = 0;
    int strength = 0;
    int baseSpeed = 100;
    int speed = baseSpeed + itemSpeed;
    int baseCritChance = 100;
    int critChance = baseCritChance + itemCritChance;
    int baseCritDamage = 1000;
    int critDamage = baseCritDamage + itemCritDamage;
    int baseAtkSpeed = 0;
    int atkSpeed = 0;
    int baseIntelligence = 100;
    int intelligence = baseIntelligence;
    int extraIntelligence = 0;
    int maxIntelligence = baseIntelligence;
    int baseSeaCreatureChance = 0;
    int seaCreatureChance = 0;
    int baseMagicFind = 0;
    int magicFind = 0;
    int basePetLuck = 0;
    int petLuck = 0;
    int baseTrueDamage = 0;
    int trueDamage = 0;
    int baseTrueDefense = 0;
    int trueDefense = 0;
    int baseFerocity = 0;
    int ferocity = baseFerocity + itemFerocity;

    // Island
    boolean visitPublic = true;
    boolean visitFriend = true;

    // Inventories to save
    String accessoryBag; // 961a918c0c49ba8d053e522cb91abc74689367b4d8aa06bfc1ba9154730985ff
    String quiverBag; // 4cb3acdc11ca747bf710e59f4c8e9b3d949fdd364c6869831ca878f0763d1787
    String fishingBag;
    String wardrobe; //
    String potionBag; // 372466603bae9063ce7d94351103469beca3c5fe2b1e977e6427def37699ec9
    String enderChest;
    String inventory;

    // Information
    String profile = "Missing";
    int fairySouls = 0;
    int fairySoulsClaimed = 0;
    int totalDeaths = 0;
    double highestCritDamage = 0;

    // Database
    int zealotKills = 0;
    double coins_gained = 0;
    boolean blockBreak = false;
    boolean vanished = false;
    double damageToDragon = 0;
    long lastDeath = 0;
    long lastInvFullNotification = 0;

    // Skills
    int farmingLevel = 0;
    double farmingXP = 0;
    double farmingNeeded = 0;
    double farmingTotal = 0;
    int farmingExtra = 0;
    int miningLevel = 0;
    double miningXP = 0;
    double miningNeeded = 0;
    double miningTotal = 0;
    int miningExtra = 0;
    int combatLevel = 0;
    double combatXP = 0;
    double combatNeeded = 0;
    double combatTotal = 0;
    int combatExtra = 0;
    int foragingLevel = 0;
    double foragingXP = 0;
    double foragingNeeded = 0;
    double foragingTotal = 0;
    int foragingExtra = 0;
    int fishingLevel = 0;
    double fishingXP = 0;
    double fishingNeeded = 0;
    double fishingTotal = 0;
    int fishingExtra = 0;
    int enchantingLevel = 0;
    double enchantingXP = 0;
    double enchantingNeeded = 0;
    double enchantingTotal = 0;
    int enchantingExtra = 0;
    int alchemyLevel = 0;
    double alchemyXP = 0;
    double alchemyNeeded = 0;
    double alchemyTotal = 0;
    int alchemyExtra = 0;
    int carpentryLevel = 0;
    double carpentryXP = 0;
    double carpentryNeeded = 0;
    double carpentryTotal = 0;
    int carpentryExtra = 0;
    int runecraftingLevel = 0;
    double runecraftingXP = 0;
    double runecraftingNeeded = 0;
    double runecraftingTotal = 0;
    int runecraftingExtra = 0;
    int tamingLevel = 0;
    double tamingXP = 0;
    double tamingNeeded = 0;
    double tamingTotal = 0;
    int tamingExtra = 0;

    String currentSlayer = "none";

    public void resetHealth() {
        maxHealth = baseHealth + itemHealth;
        health = maxHealth;
    }

    public void resetIntelligence() {
        maxIntelligence = baseHealth + itemHealth;
        intelligence = maxIntelligence;
    }

    public void subtractHealth(int i) {
        health -= i;
    }

    public void addZealotKill() {
        zealotKills += 1;
    }

    public void resetZealotKills() {
        zealotKills = 0;
    }
}
