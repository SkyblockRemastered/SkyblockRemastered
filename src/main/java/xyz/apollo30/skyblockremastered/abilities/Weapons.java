package xyz.apollo30.skyblockremastered.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.util.HashSet;
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

        Set<Material> whitelisted = new HashSet<>();
        whitelisted.add(Material.AIR);
        whitelisted.add(Material.WATER);
        whitelisted.add(Material.STATIONARY_WATER);
        whitelisted.add(Material.SIGN);
        whitelisted.add(Material.LAVA);
        whitelisted.add(Material.STATIONARY_LAVA);

        Block block = null;
        for (int i = 8; i > 0; i--) {
            if (block != null && whitelisted.contains(block.getType())) break;
            block = plr.getTargetBlock(whitelisted, i);
        }

        if (block == null || !whitelisted.contains(block.getType())) {
            plr.sendMessage(Utils.chat("&cThere are blocks in the way!"));
            return;
        }

        int usedMana = 50;
        po.setIntelligence(po.getIntelligence() - usedMana);

        plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);

        Location location = block.getLocation();

        location.setPitch(plr.getLocation().getPitch());
        location.setYaw(plr.getLocation().getYaw());

        plr.teleport(location);
        plr.sendMessage(Utils.chat("&aUsed &6Instant Transmission &b(" + usedMana + " Mana)"));
    }

}
