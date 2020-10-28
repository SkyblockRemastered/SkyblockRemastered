package xyz.apollo30.skyblockremastered;

import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.apollo30.skyblockremastered.abilities.Miscs;
import xyz.apollo30.skyblockremastered.abilities.Weapons;
import xyz.apollo30.skyblockremastered.commands.*;
import xyz.apollo30.skyblockremastered.customMobs.CustomEnderDragon;
import xyz.apollo30.skyblockremastered.events.Dragon;
import xyz.apollo30.skyblockremastered.events.TradeEvents;
import xyz.apollo30.skyblockremastered.items.Armor;
import xyz.apollo30.skyblockremastered.items.Bows;
import xyz.apollo30.skyblockremastered.items.Fragments;
import xyz.apollo30.skyblockremastered.items.Pets;
import xyz.apollo30.skyblockremastered.listeners.*;
import xyz.apollo30.skyblockremastered.managers.ConfigManager;
import xyz.apollo30.skyblockremastered.managers.MobManager;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.ServerObject;
import xyz.apollo30.skyblockremastered.tasks.*;
import xyz.apollo30.skyblockremastered.utils.NMSUtil;
import xyz.apollo30.skyblockremastered.utils.MongoUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SkyblockRemastered extends JavaPlugin {

    public ConfigManager db;
    public HashMap<Entity, Entity> health_indicator = new HashMap<>();
    public PlayerManager playerManager;
    public MobManager mobManager;
    public HashMap<Entity, Long> indicator = new HashMap<>();
    public Miscs miscAbilities;
    public Weapons weaponAbilities;
    public Dragon dragonEvent;
    public xyz.apollo30.skyblockremastered.dragons.Dragon dragon;
    public ServerObject so = new ServerObject();
    public NMSUtil nmsu = new NMSUtil();

    // ItemStack Defining
    public xyz.apollo30.skyblockremastered.items.Weapons weapons = new xyz.apollo30.skyblockremastered.items.Weapons(this);
    public xyz.apollo30.skyblockremastered.items.Miscs miscs = new xyz.apollo30.skyblockremastered.items.Miscs(this);
    public Pets pets = new Pets(this);
    public Armor armor = new Armor(this);
    public Fragments fragments = new Fragments(this);
    public Bows bows = new Bows(this);

    @Override
    public void onEnable() {

        new MongoUtils("mongoStringHere", "databaseName", "collectionName");
        // Registering Custom Dragons
        nmsu.registerEntity("Dragon", 63, EntityEnderDragon.class, CustomEnderDragon.class);

        db = new ConfigManager(this);
        db.saveDefaultPlayers();
        db.saveDefaultMinions();
        db.saveDefaultSpawns();

        // Listener
        new DamageEvents(this);
        new InventoryEvents(this);
        new MiscEvents(this);
        new PlayerEvents(this);
        new ProtectionEvents(this);
        new SpawnEvents(this);
        new EnchantEvents(this);
        new TradeEvents(this);
        new ItemAbilityEvents(this);

        // Command
        new Gamemode(this);
        new xyz.apollo30.skyblockremastered.commands.SkyblockRemastered(this);
        new Visit(this);
        new Hub(this);
        new Build(this);
        new SpawnEgg(this);
        new Item(this);

        // Abilities
        this.miscAbilities = new Miscs(this);
        this.weaponAbilities = new Weapons(this);
        this.dragonEvent = new Dragon(this);

        // Managers
        this.playerManager = new PlayerManager(this);
        this.mobManager = new MobManager(this);

        // When reloaded and the players still exist, it just recreates their database again.
        for (Player plr : Bukkit.getOnlinePlayers()) {
            if (playerManager.getPlayerData(plr) == null) playerManager.createPlayerData(plr);
        }
        // Removes all entities in the server bc I am lazy to re-register them.
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (entity.getType() != EntityType.PLAYER) entity.remove();
            }
        }

        // Inits the timer for all managers.
        mobManager.initTimer();
        new ScoreboardTask(this).runTaskTimer(this, 30, 30);
        new ActionBarTask(this).runTaskTimer(this, 20, 20);
        new RegenerationTask(this).runTaskTimer(this, 30, 30);
        new WheatCrystalTask(this).runTaskTimer(this, 1, 1);
        new LagPreventerTask(this).runTaskTimer(this, 0, 20);
        new EnchantEvents(this).runTaskTimer(this, 0, 1);
        new ConstantTask(this).runTaskTimer(this, 0, 1);

    }

    @Override
    public void onDisable() {

        // Saves all playerdata once the plugin deactivates.
        for (Player plr : Bukkit.getOnlinePlayers()) {
            playerManager.savePlayerData(plr);
        }

    }

    // Overflux
    // eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ4NTlkMGFkZmM5M2JlMTliYjQ0MWU2ZWRmZDQzZjZiZmU2OTEyNzIzMDMzZjk2M2QwMDlhMTFjNDgyNDUxMCJ9fX0

    // Mana Flux
    // eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODJhZGExYzdmY2M4Y2YzNWRlZmViOTQ0YTRmOGZmYTlhOWQyNjA1NjBmYzdmNWY1ODI2ZGU4MDg1NDM1OTY3YyJ9fX0=

    // Radiant
    // eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2FiNGM0ZDZlZTY5YmMyNGJiYTJiOGZhZjY3YjlmNzA0YTA2YjAxYWE5M2YzZWZhNmFlZjdhOTY5NmM0ZmVlZiJ9fX0=

    // Skeletor:
    // eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODlkMDc0YWQ5Yjk5NzE4NzllYjMyNWJkZGZmMzY3NWY3MjI0ODU2YmQ2ZDU2OWZjOGQ0ODNjMTMzZDczMDA1ZCJ9fX0

    // Parrot:
    // eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRmNGIzNDAxYTRkMDZhZDY2YWM4YjVjNGQxODk2MThhZTYxN2Y5YzE0MzA3MWM4YWMzOWE1NjNjZjRlNDIwOCJ9fX0

}