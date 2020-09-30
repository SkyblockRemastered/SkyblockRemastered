package xyz.apollo30.skyblockremastered;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.apollo30.skyblockremastered.abilities.Miscs;
import xyz.apollo30.skyblockremastered.abilities.Weapons;
import xyz.apollo30.skyblockremastered.commands.Build;
import xyz.apollo30.skyblockremastered.commands.Gamemode;
import xyz.apollo30.skyblockremastered.commands.Hub;
import xyz.apollo30.skyblockremastered.commands.Visit;
import xyz.apollo30.skyblockremastered.listeners.*;
import xyz.apollo30.skyblockremastered.managers.*;
import xyz.apollo30.skyblockremastered.tasks.*;

import java.util.HashMap;

public class SkyblockRemastered extends JavaPlugin {

    public ConfigManager db;
    public HashMap<Entity, Entity> health_indicator = new HashMap<>();
    public BlockManager blockManager;
    public PlayerManager playerManager;
    public MobManager mobManager;
    public HashMap<Entity, Long> indicator = new HashMap<>();
    public Miscs miscAbilities;
    public Weapons weaponAbilities;

    @Override
    public void onEnable() {

        db = new ConfigManager(this);
        db.saveDefaultPlayers();
        db.saveDefaultMinions();
        db.saveDefaultSpawns();

        // Listener
        new BlockFade(this);
        new BlockForm(this);
        new BlockIgnite(this);
        new BlockSpread(this);
        new EndermanPickup(this);
        new EntityCombust(this);
        new EntityDamage(this);
        new EntityDamageByEntity(this);
        new EntityDeath(this);
        new EntitySpawn(this);
        new EntityTeleport(this);
        new InventoryClick(this);
        new InventoryItemMove(this);
        new ItemDamage(this);
        new ItemDrag(this);
        new PlayerDeath(this);
        new PlayerDrop(this);
        new PlayerInteract(this);
        new PlayerJoin(this);
        new PlayerLeave(this);
        new PlayerPortal(this);
        new RegainHealth(this);
        new SlimeSplit(this);
        new UnloadChunk(this);
        new LeavesDecay(this);
        new BlockBreak(this);
        new PlayerMove(this);
        new BlockPlace(this);
        new EntityExplode(this);
        new WeatherChange(this);
        new PlayerBucketEmpty(this);
        new PlayerBucketFill(this);
        new PlayerPickupItem(this);

        // Command
        new Gamemode(this);
        new xyz.apollo30.skyblockremastered.commands.SkyblockRemastered(this);
        new Visit(this);
        new Hub(this);
        new Build(this);

        // Abilities
        this.miscAbilities = new Miscs(this);
        this.weaponAbilities = new Weapons(this);

        // Managers
        this.blockManager = new BlockManager(this);
        this.playerManager = new PlayerManager(this);
        this.mobManager = new MobManager(this);

        // When reloaded and the players still exist, it just recreates their database again.
        for (Player plr : Bukkit.getOnlinePlayers()) {
            if (playerManager.getPlayerData(plr) == null) playerManager.createPlayerData(plr);
        }
        // Removes all entities in the server bc I am lazy to re-register them.
        for (World world : Bukkit.getWorlds()) {
            world.setPVP(false);
            for (LivingEntity entity : world.getLivingEntities()) {
                if (entity.getType() != EntityType.PLAYER) entity.remove();
            }
        }



        // Inits the timer for all managers.
        blockManager.initTimer();
        // mobManager.initBloccCheck();
        mobManager.initTimer();
        new ScoreboardTask(this).runTaskTimer(this, 30, 30);
        new ActionBarTask(this).runTaskTimer(this, 20, 20);
        new RegenerationTask(this).runTaskTimer(this, 30, 30);
        new WheatCrystalTask(this).runTaskTimer(this, 1, 1);
        new LagPreventerTask(this).runTaskTimer(this, 0, 60);
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