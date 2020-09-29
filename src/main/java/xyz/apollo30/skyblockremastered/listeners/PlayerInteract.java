package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.InventoryManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.ResponsesUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.*;

public class PlayerInteract implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerInteract(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Maddox Batphone Cooldown
    public HashMap<UUID, Integer> bcd = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player plr = e.getPlayer();

        if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL)
            e.setCancelled(true);

        if (e.getItem() == null || e.getAction() == null)
            return;

        String action = e.getAction().toString();
        String item = e.getItem().getItemMeta().getDisplayName();

        if (item == null || action == null)
            return;

        if (action.contains("LEFT_CLICK")) {
            if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
                InventoryManager.skyblockMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers(), plugin);
                e.setCancelled(true);
            }
        } else if (action.contains("RIGHT_CLICK")) {
            if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
                InventoryManager.skyblockMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers(), plugin);
                e.setCancelled(true);
            } else if (item.contains(Utils.chat("&9Aspect of The End"))) {

                PlayerObject po = plugin.playerManager.playerObjects.get(plr);
                if (po.getIntelligence() - 50 <= 0 || po.getIntelligence() <= 0) {
                    plr.sendMessage(Utils.chat("&cYou do not have enough mana"));
                    return;
                }
                po.setIntelligence(po.getIntelligence() - 50);

                plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);

                Set<Material> whitelisted = new HashSet<>();
                whitelisted.add(Material.AIR);
                whitelisted.add(Material.WATER);
                whitelisted.add(Material.STATIONARY_WATER);
                whitelisted.add(Material.SIGN);
                whitelisted.add(Material.LAVA);
                whitelisted.add(Material.STATIONARY_LAVA);

                List<Block> blocks = plr.getLineOfSight(whitelisted, 8);
                Collections.reverse(blocks);

                Block block = null;
                for (Block blocc : blocks) {
                    if (whitelisted.contains(blocc.getType())) {
                        block = blocc;
                        break;
                    }
                }

                if (block == null || !whitelisted.contains(block.getType())) {
                    plr.sendMessage(Utils.chat("&cThere are blocks in the way!"));
                    return;
                }

                Location location = block.getLocation();
                location = Utils.getCenter(location);
                location.setYaw(plr.getLocation().getYaw());
                location.setPitch(plr.getLocation().getPitch());
                plr.teleport(location);
            } else if (item.equals(Utils.chat("&aMaddox Batphone"))) {
                // on cooldown
                if (bcd.containsKey(plr.getUniqueId())) {
                    // more than 3 attempts
                    if (bcd.get(plr.getUniqueId()).equals(3)) {
                        plr.sendMessage(Utils.chat("&c✆ HEY IT'S NOT PICKING UP STOP TRYING!"));
                        return;
                    }
                    // on cooldown, less than 3 attempts
                    else {
                        plr.sendMessage(ResponsesUtils.callFailed());
                        int failNum = bcd.get(plr.getUniqueId());
                        ++failNum;
                        return;
                    }
                }
                // not on cooldown, add to cooldown list.
                else {
                    plr.sendMessage(ResponsesUtils.callSuccess());
                    // open GUI (will be done tmr, also please give me a list of slayers and stuff so i can do this)
                    bcd.put(plr.getUniqueId(), 0);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> bcd.remove(plr.getUniqueId()), 600);
                    return;
                }
            }
        }
    }
}
