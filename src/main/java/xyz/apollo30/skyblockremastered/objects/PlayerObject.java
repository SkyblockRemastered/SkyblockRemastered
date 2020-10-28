package xyz.apollo30.skyblockremastered.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PlayerObject {

    // Useless shit that I will never use in my life.
    String ign;
    String uuid;

    // Armor & Held Item Detection
    ItemStack helmet;
    ItemStack chestplate;
    ItemStack leggings;
    ItemStack boots;
    ItemStack heldItem;

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

    // Settings
    Constants.Rarities selectedRarity = Constants.Rarities.COMMON;
    boolean fullInventoryAlert = true;

    double purse = 100;
    double bank = 0;
    double bits = 0;

    // Stats

    // Health
    int baseHealth = 100;
    int health = 0;
    int absorptionHealth = 0;
    int extraHealth = 0;
    int maxHealth = baseHealth;

    // Defense
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

    // Island
    List<UUID> coop = new ArrayList<>();
    List<UUID> builders = new ArrayList<>();
    boolean visitPublic = true;
    boolean visitFriend = true;

    // Database
    int zealotKills = 0;
    double coins_gained = 0;
    boolean statOverride = false;
    boolean blockBreak = false;
    boolean vanished = false;
    double damageToDragon = 0;
    long lastDeath = 0;
    long lastInvFullNotification = 0;

    // Skills
    int farmingLevel = 0;
    int farmingXP = 0;
    int farmingNeeded = 0;
    int farmingTotal = 0;
    int miningLevel = 0;
    int miningXP = 0;
    int miningNeeded = 0;
    int miningTotal = 0;
    int combatLevel = 0;
    int combatXP = 0;
    int combatNeeded = 0;
    int combatTotal = 0;
    int foragingLevel = 0;
    int foragingXP = 0;
    int foragingNeeded = 0;
    int foragingTotal = 0;
    int fishingLevel = 0;
    int fishingXP = 0;
    int fishingNeeded = 0;
    int fishingTotal = 0;
    int enchantingLevel = 0;
    int enchantingXP = 0;
    int enchantingNeeded = 0;
    int enchantingTotal = 0;
    int alchemyLevel = 0;
    int alchemyXP = 0;
    int alchemyNeeded = 0;
    int alchemyTotal = 0;
    int carpentryLevel = 0;
    int carpentryXP = 0;
    int carpentryNeeded = 0;
    int carpentryTotal = 0;
    int runecraftingLevel = 0;
    int runecraftingXP = 0;
    int runecraftingNeeded = 0;
    int runecraftingTotal = 0;
    int tamingLevel = 0;
    int tamingXP = 0;
    int tamingNeeded = 0;
    int tamingTotal = 0;

    String currentSlayer = "none";

    // kills for each slayer
    int revenantKills = 0;
    int tarantulaKills = 0;
    int svenKills = 0;
    int skeletorKills = 0;
    int totalKills = revenantKills + tarantulaKills + svenKills + skeletorKills;

    // highest tier of each slayer the player can do
    int revTier = 0;
    int taraTier = 0;
    int svenTier = 0;
    int skeleTier = 0;

    // player's level for each slayer
    int revLevel = 0;
    int taraLevel = 0;
    int svenLevel = 0;
    int skeleLevel = 0;

    // rng
    int revRng = 0;
    int taraRng = 0;
    int svenRng = 0;
    int skeleRng = 0;
    int totalRng = revRng + taraRng + svenRng + skeleRng;

    // money spent
    double revSpent = 0;
    double taraSpent = 0;
    double svenSpent = 0;
    double skeleSpent = 0;
    double totalSpent = revSpent + taraSpent + svenSpent + skeleSpent;

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
