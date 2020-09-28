package xyz.apollo30.skyblockremastered;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.apollo30.skyblockremastered.commands.Hub;
import xyz.apollo30.skyblockremastered.commands.Items;
import xyz.apollo30.skyblockremastered.commands.Tps;
import xyz.apollo30.skyblockremastered.commands.Visit;
import xyz.apollo30.skyblockremastered.listeners.*;
import xyz.apollo30.skyblockremastered.managers.*;
import xyz.apollo30.skyblockremastered.tasks.ActionBarTask;
import xyz.apollo30.skyblockremastered.tasks.ScoreboardTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkyblockRemastered extends JavaPlugin {

    public ConfigManager db;
    public HashMap<Entity, Entity> health_indicator = new HashMap<>();
    public BlockManager blockManager;
    public PlayerManager playerManager;
    public MobManager mobManager;
    public LagManager lagManager;

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

        // Command
        // new Gamemode(this);
        new xyz.apollo30.skyblockremastered.commands.SkyblockRemastered(this);
        new Visit(this);
        new Hub(this);
        new Tps(this);
        new Items(this);

        // Managers
        this.blockManager = new BlockManager(this);
        this.playerManager = new PlayerManager(this);
        this.mobManager = new MobManager(this);
        this.lagManager = new LagManager(this);

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
        playerManager.initTimer();
        // mobManager.initBloccCheck();
        mobManager.initTimer();
        lagManager.lagManager();
        startScoreboardTask();
        startActionBarTask();
    }

    @Override
    public void onDisable() {

        // Saves all playerdata once the plugin deactivates.
        for (Player plr : Bukkit.getOnlinePlayers()) {
            playerManager.savePlayerData(plr);
        }

    }

    public void startActionBarTask() {
        new ActionBarTask(this).runTaskTimer(this, 20, 20);
    }

    public void startScoreboardTask() {
        new ScoreboardTask(this).runTaskTimer(this, 1200, 1200);
    }

    // Skeletor:
    // eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODlkMDc0YWQ5Yjk5NzE4NzllYjMyNWJkZGZmMzY3NWY3MjI0ODU2YmQ2ZDU2OWZjOGQ0ODNjMTMzZDczMDA1ZCJ9fX0

    // Parrot:
    // eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRmNGIzNDAxYTRkMDZhZDY2YWM4YjVjNGQxODk2MThhZTYxN2Y5YzE0MzA3MWM4YWMzOWE1NjNjZjRlNDIwOCJ9fX0

}