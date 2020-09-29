package xyz.apollo30.skyblockremastered.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Weapons {

    private final SkyblockRemastered plugin;

    public Weapons(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public void aspect_of_the_end(Player plr) {
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);
        if (po.getIntelligence() - 50 < 0 || po.getIntelligence() <= 0) {
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
        location.setYaw(plr.getLocation().getYaw());
        location.setPitch(plr.getLocation().getPitch());
        plr.teleport(location);
    }

}
