package xyz.apollo30.skyblockremastered;

import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.apollo30.skyblockremastered.guis.constructors.MenuUtility;
import xyz.apollo30.skyblockremastered.abilities.Miscs;
import xyz.apollo30.skyblockremastered.abilities.Weapons;
import xyz.apollo30.skyblockremastered.commands.*;
import xyz.apollo30.skyblockremastered.events.bagHandler.AccessoryBag;
import xyz.apollo30.skyblockremastered.events.bagHandler.PotionBag;
import xyz.apollo30.skyblockremastered.events.bagHandler.QuiverBag;
import xyz.apollo30.skyblockremastered.events.dragonHandler.CustomEnderDragon;
import xyz.apollo30.skyblockremastered.events.dragonHandler.lootTable.DragLootStructure;
import xyz.apollo30.skyblockremastered.events.dragonHandler.DragonEvent;
import xyz.apollo30.skyblockremastered.events.playerHandler.ProfileViewer;
import xyz.apollo30.skyblockremastered.events.playerHandler.TradeMenu;
import xyz.apollo30.skyblockremastered.items.*;
import xyz.apollo30.skyblockremastered.listeners.*;
import xyz.apollo30.skyblockremastered.managers.MobManager;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.tasks.*;
import xyz.apollo30.skyblockremastered.objects.ServerObject;
import xyz.apollo30.skyblockremastered.utils.MongoUtils;
import xyz.apollo30.skyblockremastered.utils.NMSUtil;

import java.util.HashMap;

public class SkyblockRemastered extends JavaPlugin {

    public static HashMap<Player, Location> frozenPlayers = new HashMap<>();
    public static HashMap<Entity, Entity> health_indicator = new HashMap<>();
    public static PlayerManager playerManager;
    public static MobManager mobManager;
    public static HashMap<Entity, Long> indicator = new HashMap<>();
    public static Miscs miscAbilities;
    public static Weapons weaponAbilities;
    public static DragonEvent dragonEvent;
    public static DragLootStructure dragon;
    public static ServerObject so = new ServerObject();
    public static NMSUtil nmsu = new NMSUtil();
    public MobSpawnTask mobSpawnTask = new MobSpawnTask(this);

    // Spawnpoints
    public static HashMap<Location, Block> endSpawnpoints = new HashMap<>();

    // ItemStack Defining
    public xyz.apollo30.skyblockremastered.items.Weapons weapons = new xyz.apollo30.skyblockremastered.items.Weapons(this);
    public xyz.apollo30.skyblockremastered.items.Miscs miscs = new xyz.apollo30.skyblockremastered.items.Miscs(this);
    public Pets pets = new Pets(this);
    public Armor armor = new Armor(this);
    public Fragments fragments = new Fragments(this);
    public Bows bows = new Bows(this);
    public Stones stones = new Stones(this);
    public Accessories accessories = new Accessories(this);

    @Override
    public void onEnable() {

        new MongoUtils(
                "mongodb+srv://console:t3rk1reyaseocmdb2skel@skyblockremastered.olzwn.mongodb.net/constants?retryWrites=true&w=majority",
                "constants",
                "playerData",
                "apiKeys");

        // Registering Custom Dragons
        nmsu.registerEntity("Dragon", 63, EntityEnderDragon.class, CustomEnderDragon.class);

        // Listener
        new DamageEvents(this);
        new InventoryEvents(this);
        new MiscEvents(this);
        new PlayerEvents(this);
        new ProtectionEvents(this);
        new SpawnEvents(this);
        new EnchantEvents(this);
        new TradeMenu(this);
        new ItemEvents(this);
        new DeathEvents(this);
        new PotionBag(this);
        new QuiverBag(this);
        new AccessoryBag(this);
        new ProfileViewer(this);

        // Command
        new Gamemode(this);
        new xyz.apollo30.skyblockremastered.commands.SkyblockRemastered(this);
        new Visit(this);
        new Hub(this);
        new Build(this);
        new AuctionHouse(this);
        new Debug(this);
        new Mob(this);
        new API(this);

        // Abilities
        miscAbilities = new Miscs(this);
        weaponAbilities = new Weapons(this);
        dragonEvent = new DragonEvent(this);

        // Managers
        playerManager = new PlayerManager(this);
        mobManager = new MobManager(this);

        // When reloaded and the players still exist, it just recreates their database again.
        for (Player plr : Bukkit.getOnlinePlayers()) {
            if (playerManager.getPlayerData(plr) == null) PlayerManager.createPlayerData(plr);
        }
        // Removes all entities in the server bc I am lazy to re-register them.
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (entity.getType() != EntityType.PLAYER) entity.remove();
            }
        }

        // Loads all spawnpoints in the map

        try {
            // Dragon's Nest
            for (int x = -90; x < 78; x++) {
                for (int y = 0; y < 13; y++) {
                    for (int z = -96; z < 69; z++) {
                        Block block = Bukkit.getWorld("hub").getBlockAt(x, y, z);
                        if (block.getType().equals(Material.CARPET) && block.getData() == 5) {
                            // Utils.broadCast("Block Saved at " + x + ", " + y + ", " + z);
                            endSpawnpoints.put(new Location(Bukkit.getWorld("hub"), x, y, z), block);
                        }
                    }
                }
            }

        } catch (Exception err) {
            System.out.println("[ERROR] Spawnpoints of the dragon's nest did not load.");
        }

        // Inits the timer for all managers.
        new ScoreboardTask(this).runTaskTimer(this, 30, 30);
        new ActionBarTask(this).runTaskTimer(this, 20, 20);
        new RegenerationTask().runTaskTimer(this, 30, 30);
        new WheatCrystalTask(this).runTaskTimer(this, 1, 1);
        new LagPreventerTask(this).runTaskTimer(this, 0, 20);
        new EnchantEvents(this).runTaskTimer(this, 0, 1);
        new ConstantTask().runTaskTimer(this, 0, 1);
        mobSpawnTask.runTaskTimer(this, 0, 20 * 15);

    }

    private static final HashMap<Player, MenuUtility> menuUtilityMap = new HashMap<>();

    public static MenuUtility getMenuUtility(Player plr) {
        MenuUtility menuUtility;
        if (menuUtilityMap.containsKey(plr)) {
            return menuUtilityMap.get(plr);
        } else {
            menuUtility = new MenuUtility(plr);
            menuUtilityMap.put(plr, menuUtility);
            return menuUtility;
        }
    }

    @Override
    public void onDisable() {

        // Saves all playerdata once the plugin deactivates.
        for (Player plr : Bukkit.getOnlinePlayers()) {
            PlayerManager.savePlayerData(plr);
        }

    }
}