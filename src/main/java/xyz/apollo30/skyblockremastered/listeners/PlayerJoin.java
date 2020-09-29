package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class PlayerJoin implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerJoin(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {

        Player plr = e.getPlayer();

        e.setJoinMessage(Utils.chat("&e" + plr.getName() + " has joined the game."));

        if (!plr.isOp()) {
            plr.setGameMode(GameMode.SURVIVAL);
        }

        plr.sendMessage(Utils.chat("&8&m--------------------------------------------------"));
        plr.sendMessage(Utils.chat("&7Welcome to &6SkyblockRemastered&7, " + plr.getName() + "!"));
        plr.sendMessage(Utils.chat("&8&m--------------------------------------------------"));


        ItemStack nether_star = Utils.createItem(null, "NETHER_STAR", 1, 9, "&aSkyBlock Menu &7(Right Click)",
                "&7View all of your SkyBlock", "&7progress, including your Skills,",
                "&7Collections, Recipes, and more!");
        plr.getInventory().remove(nether_star);

        Utils.addItem(plr.getInventory(), nether_star, 9);

        // Load the Private Island
        World clone = Bukkit.getServer().createWorld(new WorldCreator("private_island_template"));

        // Now we copi pasta
        Utils.copyWorld(clone, "islands/" + plr.getUniqueId().toString());

        // Define Variables
        World island = Bukkit.getServer().getWorld("islands/" + plr.getUniqueId().toString());
        // Teleport them
        Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
        plr.teleport(loc);

        // Create Player.yml section for them
        GuiUtils.createPlayerData(plr, plr.getUniqueId().toString(), plugin.db.getPlayers());
        plugin.db.savePlayers();

        // Give the player a temporary database.
        if (plugin.playerManager.getPlayerData(plr) == null) plugin.playerManager.createPlayerData(plr);
    }
}
