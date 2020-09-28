package xyz.apollo30.skyblockremastered.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerObject {

    // Useless shit that I will never use in my life.
    String ign;
    String uuid;

    // Settings
    boolean statOverride = false;
    boolean blockBreak = false;
    boolean vanished = false;

    double purse = 100;
    double bank = 0;
    double gems = 0;

    // Stats
    int health = 100;
    int maxHealth = 100;
    int defense = 0;
    int strength = 0;
    int speed = 100;
    int crit_chance = 20;
    int crit_damage = 50;
    int atk_speed = 0;
    int intelligence = 100;
    int maxIntelligence = 100;
    int sea_creature_chance = 0;
    int magic_find = 0;
    int pet_luck = 0;

    // Island
    List<Player> coop = new ArrayList<>();
    List<Player> builders = new ArrayList<>();

    // Database
    int zealot_kills = 0;

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

    public void resetHealth() {
        health = maxHealth;
    }

    public void resetIntelligence() {
        intelligence = maxIntelligence;
    }

    public void subtractHealth(int i) {
        health -= i;
    }

    public void addZealotKill() {
        zealot_kills += 1;
    }

    public void resetZealotKills() {
        zealot_kills = 0;
    }
}
