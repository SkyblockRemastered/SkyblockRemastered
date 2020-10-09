package xyz.apollo30.skyblockremastered.listeners;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.constants.Constants;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiscEvents implements Listener {

    private final SkyblockRemastered plugin;

    public MiscEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        e.setMotd(Utils.chat("&6SkyblockRemastered &7- &8[&71.8&8]\n&b&k|&r &9Beta Release Coming Soon &b&k|&r"));
        e.setMaxPlayers(100);
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onUnloadChunk(ChunkUnloadEvent e) {
        for (Entity entity : e.getChunk().getEntities()) {
            if (entity.getPassenger() != null) entity.remove();
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(e.toWeatherState());
    }

//    @EventHandler
//    public void onPlayerItemHeld(PlayerItemHeldEvent e) {
//
//        Player plr = e.getPlayer();
//        PlayerObject po = plugin.playerManager.playerObjects.get(plr);
//
//        for (ItemStack item : plr.getInventory().getContents()) {
//            if (item == null) return;
////            addStats(item);
//            addRarity(item);
//        }
//    }
//
//    private void addRarity(ItemStack item) {
//
//        List<Integer> itemIds = new ArrayList<>(Arrays.asList(256, 257, 258, 259, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 283, 284, 285, 286, 290, 291, 292, 293, 294, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317));
//        if (itemIds.contains(item.getTypeId())) {
//            if (!item.hasItemMeta()) {
//                List<Integer> common = new ArrayList<>(Arrays.asList())
//            }
//            switch ()
//        }
//
//
//        if (!item.hasItemMeta()) {
//            Utils.addLore(item, "", "&7Damage: &c+20", "&7Strength: &c+30", "", "&b&lCELESTIAL");
//            ItemMeta meta = item.getItemMeta();
//            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_POTION_EFFECTS);
//        }
//
//
//    }

//
//    private void addStats(ItemStack item) {
//        Utils.addLore(item, "", "");
//    }
}
