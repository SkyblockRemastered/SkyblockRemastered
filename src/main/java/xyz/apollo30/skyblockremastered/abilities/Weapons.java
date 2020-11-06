package xyz.apollo30.skyblockremastered.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
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
        PlayerObject po = PlayerManager.playerObjects.get(plr);
        if (po.getIntelligence() - 50 < 0 || po.getIntelligence() <= 0) {
            plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, .5F);
            plr.sendMessage(Utils.chat("&cYou do not have enough mana"));
            return;
        }

        int usedMana = 50;
        po.setIntelligence(po.getIntelligence() - usedMana);

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

        if (plr.getLocation().getYaw() >= 0 && plr.getLocation().getYaw() <= -180) {
            location.setX(location.getX() > 0 ? location.getX() + .5 : location.getX() - .5);
            location.setZ(location.getX() > 0 ? location.getZ() - .5 : location.getZ() + .5);

            location.setYaw(plr.getLocation().getYaw());
            location.setPitch(plr.getLocation().getPitch());
        } else {
            location.setX(location.getX() > 0 ? location.getX() - .5 + 1 : location.getX() + .5 + 1);
            location.setZ(location.getX() > 0 ? location.getZ() + .5 : location.getZ() - .5);

            location.setYaw(plr.getLocation().getYaw());
            location.setPitch(plr.getLocation().getPitch());
        }

        plr.teleport(location);
        plr.sendMessage(Utils.chat("&aUsed &6Instant Transmission &b(" + usedMana + " Mana)"));
    }

    public void inkWand(Player plr) {

    }

}
