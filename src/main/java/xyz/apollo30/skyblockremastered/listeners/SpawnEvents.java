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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PacketManager;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;

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
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), Utils.addLore(
                    Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ=="),
                    "&5Summoning Eye",
                    "&7Use this at the &5Ender Altar",
                    "&7in the &5Dragon's Nest&7 to",
                    "&7summon Ender Dragons!",
                    "",
                    "&5&lEPIC"));
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

        Player plr = e.getEntity();

        e.getEntity().setHealth(e.getEntity().getMaxHealth());
        e.setKeepInventory(true);
        e.setKeepLevel(true);
        e.setDroppedExp(0);
        e.setDeathMessage("");
        PlayerObject po = plugin.playerManager.getPlayerData(e.getEntity());
        if (po.getLastDeath() != 0 && new Date().getTime() - po.getLastDeath() <= 1000) return;
        po.setLastDeath(new Date().getTime());
        po.resetHealth();

        Bukkit.getScheduler().runTask(plugin, () -> e.getEntity().setFireTicks(0));
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> plr.setVelocity(new Vector()), 1L);

        for (ItemStack contents : plr.getInventory().getContents()) {
            if (contents.hasItemMeta() && contents.getItemMeta().getDisplayName() != null) {
                if (Utils.isInZone(plr.getLocation(), new Location(plr.getWorld(), -112, 255, -107), new Location(plr.getWorld(), 213, 29, 127))) {
                    if (contents.getItemMeta().getDisplayName().contains("Remnant of the Eye")) {
                        plr.setHealth(plr.getMaxHealth());
                        plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1.25F);
                        plr.getInventory().remove(contents);
                        if (plr.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                            plr.sendMessage(Utils.chat("&5Your Remnant of the Eye saved you from certain death! You were safely teleported back to spawn!"));
                            plr.teleport(new Location(plr.getWorld(), 173.5, 101, -3));
                            break;
                        } else {
                            plr.sendMessage(Utils.chat("&5Your Remnant of the Eye saved you from certain death!"));
                            break;
                        }
                    }
                }
            } else {
                double purse = po.getPurse();
                po.setPurse(po.getPurse() / 2);

                Location loc = e.getEntity().getWorld().getSpawnLocation();
                loc.setPitch(0);
                loc.setYaw(-180);
                e.getEntity().teleport(loc);

                plr.sendMessage(Utils.chat("&cYou died and lost " + String.format("%,.0f", purse / 2) + " coins!"));
                plr.playSound(plr.getLocation(), Sound.ANVIL_LAND, 1F, 10F);
                break;
            }
        }
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
        plr.sendMessage(Utils.chat("&6Store: &bhttp://store.apollo30.xyz/"));
        plr.sendMessage(Utils.chat("&8&m--------------------------------------------------"));

        // Rank Joins
        Utils.broadCast(Helper.getRank(plr) + " &7has joined the game!");

        ItemStack nether_star = Utils.createItem(null, "NETHER_STAR", 1, 9, "&aSkyBlock Menu &7(Right Click)",
                "&7View all of your SkyBlock", "&7progress, including your Skills,",
                "&7Collections, Recipes, and more!");
        plr.getInventory().remove(nether_star);

        Utils.addItem(plr.getInventory(), nether_star, 9);

        // Load the Private Island
        World clone = Bukkit.getServer().createWorld(new WorldCreator("private_island_template"));

        // Now we copi pasta
        Utils.copyWorld(clone, "playerislands/" + plr.getUniqueId().toString());

        // Define Variables
        World island = Bukkit.getServer().getWorld("playerislands/" + plr.getUniqueId().toString());
        // Teleport them
        Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
        plr.teleport(loc);

        // Create Player.yml section for them
        PlayerManager.createPlayerData(plr, plr.getUniqueId().toString(), plugin.db.getPlayers());
        plugin.db.savePlayers();

        // Give the player a temporary database.
        if (plugin.playerManager.getPlayerData(plr) == null) plugin.playerManager.createPlayerData(plr);

        // If a player joins during a dragon fight
        plugin.dragonEvent.playerDamage.put(plr, (double) 0);
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

        ItemStack thousand_coin = Utils.getSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/d7c4f6630597a49ad223d12cf648af2283d34a65bf9dd057d198d2980779c34"));
        thousand_coin.setAmount(thousand_coins);
        ItemMeta meta = thousand_coin.getItemMeta();
        meta.setDisplayName(Utils.chat("Coin 1000"));
        thousand_coin.setItemMeta(meta);

        ItemStack hundred_coin = Utils.getSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/1c0e914476e1b15da2a91f45696dd217669d4dac4fa621650929bace03de2254"));
        hundred_coin.setAmount(hundred_coins);
        ItemMeta meta1 = hundred_coin.getItemMeta();
        meta1.setDisplayName(Utils.chat("Coin 100"));
        hundred_coin.setItemMeta(meta1);

        ItemStack fifty_coin = Utils.getSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/c43f12c8369f9c3888a45aaf6d7761578402b4241958f7d4ae4eceb56a867d2a"));
        fifty_coin.setAmount(fifty_coins);
        ItemMeta meta2 = fifty_coin.getItemMeta();
        meta2.setDisplayName(Utils.chat("Coin 50"));
        fifty_coin.setItemMeta(meta2);

        ItemStack one_coin = Utils.getSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/740d6e362bc7eee4f911dbd0446307e7458d1050d09aee538ebcb0273cf75742"));
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
