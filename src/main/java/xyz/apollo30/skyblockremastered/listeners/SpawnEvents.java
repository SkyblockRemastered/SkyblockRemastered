package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.GUIs.GUIHelper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PacketManager;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.NMSUtil;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;
import java.util.UUID;

public class SpawnEvents implements Listener {

    private final SkyblockRemastered plugin;

    public SpawnEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        if (e.getEntity() != null) {
            Entity entity = e.getEntity();
            EntityType type = e.getEntityType();

            // If the entity's armor stand just return
            if (type == EntityType.ARMOR_STAND)
                return;

            if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
                if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
                    if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.DISPENSE_EGG) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }

            // Return if its a baby zombie.
            if (type == EntityType.ZOMBIE) {
                Zombie zom = (Zombie) e.getEntity();
                if (zom.isBaby())
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (EntityType.PLAYER == e.getEntityType()) return;
        Player plr = e.getEntity().getKiller();
        if (plr == null) return;
        PlayerObject po = plugin.playerManager.getPlayerData(plr);
        MobObject mo = plugin.mobManager.mobObjects.get(e.getEntity());
        if (mo == null) return;

        if (e.getEntityType() != EntityType.ENDER_DRAGON)
            coinDrop(e.getEntity(), mo.getLevel());

        // Special Zealot
        if (e.getEntity().getPassenger().getCustomName().contains(Utils.chat("&cZealot"))) {

            if (mo.getName().contains("Special Zealot")) return;

            double random = po.getZealotKills() > 420 ? 0.00476190476 : 1;
            if (Math.random() > random) {
                po.addZealotKill();
            } else {
                plr.playSound(plr.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
                plr.sendMessage(Utils.chat("&aA special &5Zealot &ahas spawned nearby. &7(" + po.getZealotKills() + ")"));

                Location loc = e.getEntity().getLocation().add(Math.random() * 5, Math.random() * 2, Math.random() * 5);

                Enderman enderman = (Enderman) e.getEntity().getWorld().spawnEntity(loc, EntityType.ENDERMAN);
                plugin.mobManager.createMob(enderman, "Special Zealot");

                po.resetZealotKills();
            }

        } else if (e.getEntityType() == EntityType.ENDERMAN && e.getEntity().getPassenger().getCustomName().contains(Utils.chat("Special Zealot"))) {
            plr.sendMessage(Utils.chat("&6&lRARE DROP!&r &5Summoning Eye&7!"));
            PacketManager.sendTitle(plr, 0, 15, 1, Utils.chat("&cSpecial Zealot!"), "");
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), NMSUtil.addString(plugin.miscs.SUMMONING_EYE, "UUID", UUID.randomUUID().toString()));
            plr.playSound(plr.getLocation(), Sound.SUCCESSFUL_HIT, 1F, .5F);
        }

        if (plugin.health_indicator.containsKey(e.getEntity())) {
            Entity armor_stand = plugin.health_indicator.get(e.getEntity());
            if (armor_stand != null) {
                plugin.health_indicator.remove(e.getEntity());
                armor_stand.remove();
            }
        } else if (e.getEntity().getPassenger() == null) e.getEntity().getPassenger().remove();

        plugin.mobManager.mobObjects.remove(e.getEntity());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setKeepInventory(true);
        e.setKeepLevel(true);
        e.setDroppedExp(0);
        e.setDeathMessage("");
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        Player plr = e.getPlayer();
        e.setQuitMessage(Utils.chat("&e" + plr.getName() + " has left the game."));

        plugin.playerManager.savePlayerData(plr);
        plugin.playerManager.playerObjects.remove(plr);
        plugin.dragonEvent.playerDamage.remove(plr);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {

        Player plr = e.getPlayer();

        e.setJoinMessage("");

        if (!plr.isOp()) {
            plr.setGameMode(GameMode.SURVIVAL);
        }

        plr.sendMessage(Utils.chat("&8&m--------------------------------------------------"));
        plr.sendMessage(Utils.chat("&7Welcome to &6SkyblockRemastered&7, " + plr.getName() + "!"));
        plr.sendMessage(Utils.chat("&6Discord: &bhttps://discord.gg/gbXcMta"));
        plr.sendMessage(Utils.chat("&6IP: &bplay.apollo30.xyz"));
        plr.sendMessage(Utils.chat("&6Store: &bhttps://skyblockremastered.tebex.io/"));
        plr.sendMessage(Utils.chat(" "));
        plr.sendMessage(Utils.chat("&aYou can change your skyblock stats using the /sr set command!"));
        plr.sendMessage(Utils.chat("&a&l/sr set [stat] [amount]"));
        plr.sendMessage(Utils.chat("&aStats: health, intelligence, defense, critdamage, critchance, magicfind, speed"));
        plr.sendMessage(Utils.chat(" "));
        plr.sendMessage(Utils.chat("&aTo open a selection of items do /item [page]"));
        plr.sendMessage(Utils.chat("&8&m--------------------------------------------------"));

        // Rank Joins
        Utils.broadCast(Helper.getRank(plr, false) + " &7has joined the game!");

        ItemStack nether_star = plugin.miscs.SKYBLOCK_MENU;
        plr.getInventory().remove(nether_star);

        GUIHelper.addItem(plr.getInventory(), nether_star, 9);

        // Create Player.yml section for them
        PlayerManager.createPlayerData(plr);
        plugin.db.savePlayers();

        // Give the player a temporary database.
        if (plugin.playerManager.getPlayerData(plr) == null) PlayerManager.createPlayerData(plr);

        // If a player joins during a dragon fight
        plugin.dragonEvent.playerDamage.computeIfAbsent(plr, k -> (double) 0);

        // Load the Private Island
        World clone = Bukkit.getServer().createWorld(new WorldCreator("private_island_template"));

        // Now we copi pasta
        // File file = new File(Bukkit.getServer().getWorldContainer(), "playerislands/" + plr.getUniqueId().toString());
        // if (!file.exists())
        Utils.copyWorld(clone, "playerislands/" + plr.getUniqueId().toString());

        // Define Variables
        World island = Bukkit.getServer().getWorld("playerislands/" + plr.getUniqueId().toString());
        // Teleport them
        Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
        plr.teleport(loc);
    }

    private void coinDrop(Entity e, int level) {
        int thousand_coins = 0;
        int hundred_coins = 0;
        int fifty_coins = 0;
        int one_coins;

        if ((level / 10) > 1) {
            int coins = level / 10;
            if ((coins / 1000) > 1) {
                thousand_coins = (int) (coins / (double) 1000);
                coins -= thousand_coins * 1000;
            }
            if ((coins / 100) > 1) {
                hundred_coins = (int) (coins / (double) 100);
                coins -= hundred_coins * 100;
            }
            if ((coins / 10) > 1) {
                fifty_coins = (int) (coins / (double) 10);
                coins -= fifty_coins * 10;
            }
            one_coins = coins;
        } else one_coins = 1;

        ItemStack thousand_coin = GUIHelper.addSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/d7c4f6630597a49ad223d12cf648af2283d34a65bf9dd057d198d2980779c34"));
        thousand_coin.setAmount(thousand_coins);
        ItemMeta meta = thousand_coin.getItemMeta();
        meta.setDisplayName(Utils.chat("Coin 1000"));
        thousand_coin.setItemMeta(meta);

        ItemStack hundred_coin = GUIHelper.addSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/1c0e914476e1b15da2a91f45696dd217669d4dac4fa621650929bace03de2254"));
        hundred_coin.setAmount(hundred_coins);
        ItemMeta meta1 = hundred_coin.getItemMeta();
        meta1.setDisplayName(Utils.chat("Coin 100"));
        hundred_coin.setItemMeta(meta1);

        ItemStack fifty_coin = GUIHelper.addSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/c43f12c8369f9c3888a45aaf6d7761578402b4241958f7d4ae4eceb56a867d2a"));
        fifty_coin.setAmount(fifty_coins);
        ItemMeta meta2 = fifty_coin.getItemMeta();
        meta2.setDisplayName(Utils.chat("Coin 50"));
        fifty_coin.setItemMeta(meta2);

        ItemStack one_coin = GUIHelper.addSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/740d6e362bc7eee4f911dbd0446307e7458d1050d09aee538ebcb0273cf75742"));
        one_coin.setAmount(one_coins);
        ItemMeta meta3 = one_coin.getItemMeta();
        meta3.setDisplayName(Utils.chat("Coin 1"));
        one_coin.setItemMeta(meta3);

        if (thousand_coins > 0) e.getWorld().dropItem(e.getLocation(), thousand_coin);
        if (hundred_coins > 0) e.getWorld().dropItem(e.getLocation(), hundred_coin);
        if (fifty_coins > 0) e.getWorld().dropItem(e.getLocation(), fifty_coin);
        if (one_coins > 0) e.getWorld().dropItem(e.getLocation(), one_coin);
    }
}
