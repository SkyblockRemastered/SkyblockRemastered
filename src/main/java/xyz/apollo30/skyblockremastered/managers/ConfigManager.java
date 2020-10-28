package xyz.apollo30.skyblockremastered.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private final SkyblockRemastered plugin;

    public ConfigManager(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration playerConfig;
    private File playerFile;

    public void reloadPlayers() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        playerFile = new File(plugin.getDataFolder() + File.separator + "Players.yml");
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        playerConfig = YamlConfiguration.loadConfiguration(playerFile);
    }

    public FileConfiguration getPlayers() {
        if (this.playerConfig == null) {
            this.reloadPlayers();
        }
        return this.playerConfig;
    }

    public void savePlayers() {
        if (this.playerConfig == null || this.playerFile == null)
            return;

        try {
            this.getPlayers().save(this.playerFile);
        } catch (IOException e) {
            System.out.print("Could not save config to " + this.playerFile);
        }
    }

    public void saveDefaultPlayers() {
        this.plugin.saveResource("Players.yml", false);
    }

    // Config
    private FileConfiguration config;
    private File configFile;

    public FileConfiguration getConfig() {
        if (this.config == null) {
            this.reloadConfig();
        }
        return this.config;
    }
    public void reloadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        minionFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        if (this.config == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            System.out.print("Could not save config to " + this.configFile);
        }
    }

    // Minions

    private FileConfiguration config;
    private File configFile;

    public FileConfiguration getConfig() {
        if (this.config == null) {
            this.reloadConfig();
        }
        return this.config;
    }
    public void reloadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        minionFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        if (this.config == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            System.out.print("Could not save config to " + this.configFile);
        }
    }


    private FileConfiguration minionConfig;
    private File minionFile;

    public void reloadMinions() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        minionFile = new File(plugin.getDataFolder() + File.separator + "Minions.yml");
        if (!minionFile.exists()) {
            try {
                minionFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        minionConfig = YamlConfiguration.loadConfiguration(minionFile);
    }

    public FileConfiguration getMinions() {
        if (this.minionConfig == null) {
            this.reloadMinions();
        }
        return this.minionConfig;
    }

    public void saveMinions() {
        if (this.minionConfig == null || this.minionFile == null)
            return;

        try {
            this.getMinions().save(this.minionFile);
        } catch (IOException e) {
            System.out.print("Could not save config to " + this.minionFile);
        }
    }

    public void saveDefaultMinions() {
        this.plugin.saveResource("Minions.yml", false);
    }

    // Spawn Points

    private FileConfiguration spawnConfig;
    private File spawnFile;

    public void reloadSpawns() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        spawnFile = new File(plugin.getDataFolder() + File.separator + "Spawns.yml");
        if (!spawnFile.exists()) {
            try {
                spawnFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        spawnConfig = YamlConfiguration.loadConfiguration(spawnFile);
    }

    public FileConfiguration getSpawns() {
        if (this.spawnConfig == null) {
            this.reloadSpawns();
        }
        return this.spawnConfig;
    }

    public void saveSpawns() {
        if (this.spawnConfig == null || this.spawnFile == null)
            return;

        try {
            this.getPlayers().save(this.spawnFile);
        } catch (IOException e) {
            System.out.print("Could not save config to " + this.spawnFile);
        }
    }

    public void saveDefaultSpawns() {
        this.plugin.saveResource("Spawns.yml", false);
    }
}