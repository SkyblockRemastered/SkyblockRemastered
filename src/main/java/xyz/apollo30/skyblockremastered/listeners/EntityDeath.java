package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.managers.PacketManager;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EntityDeath implements Listener {

    private final SkyblockRemastered plugin;

    public EntityDeath(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        if (EntityType.PLAYER == e.getEntityType()) return;

        Player plr = e.getEntity().getKiller();
        if (plr == null) return;
        PlayerObject po = plugin.playerManager.getPlayerData(plr);
        MobObject mo = plugin.mobManager.mobObjects.get(e.getEntity());
        if (mo == null) Utils.broadCast("Mob not Found");

        assert mo != null;
        coinDrop(e.getEntity(), mo.getLevel());

        // Special Zealot
        if (e.getEntity().getPassenger().getCustomName().contains(Utils.chat("&cZealot"))) {
            double random = po.getZealot_kills() > 420 ? 0.00476190476 : 1;
            if (Math.random() > random) {
                po.addZealotKill();
            } else {
                int x = new Random().nextInt(10 - -10) + -10;
                int z = new Random().nextInt(10 - -10) + -10;

                plr.playSound(plr.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
                plr.sendMessage(Utils.chat("&aA special &5Zealot &ahas spawned nearby. &7(" + po.getZealot_kills() + ")"));

                Location loc;

                List<Location> list = new ArrayList<>(plugin.mobManager.zealotSpawnPoints);
                list = (List<Location>) list.stream().filter(a -> e.getEntity().getKiller() != null && Math.abs(e.getEntity().getKiller().getLocation().getZ() + a.getZ()) <= 10 && Math.abs(e.getEntity().getKiller().getLocation().getX() + a.getX()) <= 10);

                if (list.size() == 0) loc = e.getEntity().getLocation();
                else {
                    loc = list.get((int) Math.floor(Math.random() * list.size()));
                }

                Utils.broadCast(loc.getX() + ", " + loc.getY() + ", " + loc.getZ());

                Enderman enderman = (Enderman) Bukkit.getWorld("hub").spawnEntity(loc, EntityType.ENDERMAN);
                enderman.setCarriedMaterial(new MaterialData(Material.ENDER_PORTAL_FRAME));
                plugin.mobManager.createMob(enderman, "Special Zealot");

                po.resetZealotKills();
            }
        } else if (e.getEntityType() == EntityType.ENDERMAN && e.getEntity().getPassenger().getCustomName().contains(Utils.chat("Special Zealot"))) {
            plr.sendMessage(Utils.chat("&6&lRARE DROP!&r &5Summoning Eye&7!"));
            PacketManager.sendTitle(plr, 1, 15, 1, Utils.chat("&cSpecial Zealot!"), "");
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.LEATHER, 1, (byte) 69));
            plr.playSound(plr.getLocation(), Sound.SUCCESSFUL_HIT, 1F, .5F);
        }

        if (plugin.health_indicator.containsKey(e.getEntity())) {
            Entity armor_stand = plugin.health_indicator.get(e.getEntity());
            if (armor_stand != null) {
                plugin.health_indicator.remove(e.getEntity());
                armor_stand.remove();
            }
        } else if (e.getEntity().getPassenger() == null) e.getEntity().getPassenger().remove();

        if (EntityType.PLAYER != e.getEntityType()) plugin.mobManager.mobObjects.remove(e.getEntity());
    }

    public void coinDrop(Entity e, int level) {
        int thousand_coins = 0;
        int hundred_coins = 0;
        int fifty_coins = 0;
        int one_coins = 0;

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

        Utils.broadCast(one_coins + " Coins of 1");
        Utils.broadCast(thousand_coins + " Coins of 1000");
        Utils.broadCast(hundred_coins + " Coins of 100");
        Utils.broadCast(fifty_coins + " Coins of 50");

        ItemStack thousand_coin = Utils.getSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/d7c4f6630597a49ad223d12cf648af2283d34a65bf9dd057d198d2980779c34"));
        thousand_coin.setAmount(thousand_coins);
        ItemMeta meta = thousand_coin.getItemMeta();
        meta.setDisplayName(Utils.chat("&f"));
        thousand_coin.setItemMeta(meta);

        ItemStack hundred_coin = Utils.getSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/1c0e914476e1b15da2a91f45696dd217669d4dac4fa621650929bace03de2254"));
        hundred_coin.setAmount(hundred_coins);
        ItemMeta meta1 = hundred_coin.getItemMeta();
        meta1.setDisplayName(Utils.chat("&f&f"));
        hundred_coin.setItemMeta(meta1);

        ItemStack fifty_coin = Utils.getSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/c43f12c8369f9c3888a45aaf6d7761578402b4241958f7d4ae4eceb56a867d2a"));
        fifty_coin.setAmount(fifty_coins);
        ItemMeta meta2 = fifty_coin.getItemMeta();
        meta2.setDisplayName(Utils.chat("&f&f&f"));
        fifty_coin.setItemMeta(meta2);

        ItemStack one_coin = Utils.getSkull(Utils.urlToBase64("http://textures.minecraft.net/texture/740d6e362bc7eee4f911dbd0446307e7458d1050d09aee538ebcb0273cf75742"));
        one_coin.setAmount(one_coins);
        ItemMeta meta3 = one_coin.getItemMeta();
        meta3.setDisplayName(Utils.chat("&f&f&f&f"));
        one_coin.setItemMeta(meta3);

        if (thousand_coins > 0) e.getWorld().dropItem(e.getLocation(), thousand_coin);
        if (hundred_coins > 0) e.getWorld().dropItem(e.getLocation(), hundred_coin);
        if (fifty_coins > 0) e.getWorld().dropItem(e.getLocation(), fifty_coin);
        if (one_coins > 0) e.getWorld().dropItem(e.getLocation(), one_coin);
    }
}
