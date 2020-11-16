package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.items.Miscs;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.PacketUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.MobManager;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.utils.NMSUtil;

import java.util.UUID;

public class DeathEvents implements Listener {

    private final SkyblockRemastered plugin;

    public DeathEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (EntityType.PLAYER == e.getEntityType()) return;
        else if (e.getEntity().getPassenger() == null || e.getEntity().getPassenger() != null && e.getEntity().getPassenger().getCustomName().isEmpty())
            return;

        Player plr = e.getEntity().getKiller();
        if (plr == null) return;
        PlayerObject po = SkyblockRemastered.playerManager.getPlayerData(plr);
        MobObject mo = MobManager.mobObjects.get(e.getEntity());
        if (mo == null) return;

        if (e.getEntityType() != EntityType.ENDER_DRAGON)
            coinDrop(e.getEntity(), mo.getLevel());

        String entityName = e.getEntity().getPassenger().getCustomName();

        /*
         * Death Handler for mobs that die in public islands
         * This checks if the plugin should spawn more mobs or not.
         */

        if (entityName.contains(Utils.chat("&cObsidian Defender"))) {
            e.getDrops().clear();
        } else if (entityName.contains(Utils.chat("&cZealot"))) {
            if (mo.getName().contains("Special Zealot")) return;
            double random = po.getZealotKills() > 420 ? 0.1 : 0.05; // 0.004761904761904762 : 0.002380952380952381;
            if (Math.random() > random) {
                po.addZealotKill();
            } else {
                plr.playSound(plr.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
                plr.sendMessage(Utils.chat("&aA special &5Zealot &ahas spawned nearby. &7(" + po.getZealotKills() + ")"));
                Location loc = e.getEntity().getLocation().add(Math.random() * 5, Math.random() * 2, Math.random() * 5);
                Enderman enderman = (Enderman) e.getEntity().getWorld().spawnEntity(loc, EntityType.ENDERMAN);
                SkyblockRemastered.mobManager.createMob(enderman, "Special Zealot");
                po.resetZealotKills();
            }
        } else if (e.getEntityType() == EntityType.ENDERMAN && entityName.contains(Utils.chat("Special Zealot"))) {
            plr.sendMessage(Utils.chat("&6&lRARE DROP!&r &5Summoning Eye&7!"));
            PacketUtils.sendTitle(plr, 0, 15, 1, Utils.chat("&cSpecial Zealot!"), "");
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), NMSUtil.addString(Miscs.SUMMONING_EYE, "UUID", UUID.randomUUID().toString()));
            plr.playSound(plr.getLocation(), Sound.SUCCESSFUL_HIT, 1F, .5F);
        }

        if (SkyblockRemastered.health_indicator.containsKey(e.getEntity())) {
            Entity armor_stand = SkyblockRemastered.health_indicator.get(e.getEntity());
            if (armor_stand != null) {
                SkyblockRemastered.health_indicator.remove(e.getEntity());
                armor_stand.remove();
            }
        } else if (e.getEntity().getPassenger() == null) e.getEntity().getPassenger().remove();

        MobManager.mobObjects.remove(e.getEntity());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setKeepInventory(true);
        e.setKeepLevel(true);
        e.setDroppedExp(0);
        e.setDeathMessage("");
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

        ItemStack thousand_coin = GUIHelper.addSkull(Utils.urlToBase64("d7c4f6630597a49ad223d12cf648af2283d34a65bf9dd057d198d2980779c34"));
        thousand_coin.setAmount(thousand_coins);
        ItemMeta meta = thousand_coin.getItemMeta();
        meta.setDisplayName(Utils.chat("Coin 1000"));
        thousand_coin.setItemMeta(meta);

        ItemStack hundred_coin = GUIHelper.addSkull(Utils.urlToBase64("1c0e914476e1b15da2a91f45696dd217669d4dac4fa621650929bace03de2254"));
        hundred_coin.setAmount(hundred_coins);
        ItemMeta meta1 = hundred_coin.getItemMeta();
        meta1.setDisplayName(Utils.chat("Coin 100"));
        hundred_coin.setItemMeta(meta1);

        ItemStack fifty_coin = GUIHelper.addSkull(Utils.urlToBase64("c43f12c8369f9c3888a45aaf6d7761578402b4241958f7d4ae4eceb56a867d2a"));
        fifty_coin.setAmount(fifty_coins);
        ItemMeta meta2 = fifty_coin.getItemMeta();
        meta2.setDisplayName(Utils.chat("Coin 50"));
        fifty_coin.setItemMeta(meta2);

        ItemStack one_coin = GUIHelper.addSkull(Utils.urlToBase64("740d6e362bc7eee4f911dbd0446307e7458d1050d09aee538ebcb0273cf75742"));
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
