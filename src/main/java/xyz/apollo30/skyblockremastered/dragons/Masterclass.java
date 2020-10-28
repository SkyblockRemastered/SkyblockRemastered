package xyz.apollo30.skyblockremastered.dragons;

import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.HashMap;

public class Masterclass {
    private final SkyblockRemastered plugin;
    public static HashMap<String, Dragon> dragonMap;
    private final HashMap<Player, Integer> placedPlayerEyes = new HashMap<>();

    public Masterclass(SkyblockRemastered plugin) {
        this.plugin = plugin;
        dragonMap = new HashMap<String, Dragon>() {
            {
                put("CELESTIAL", new CelestialDragon(plugin));
                put("SUPERIOR", new SuperiorDragon());
                put("STRONG", new StrongDragon());
                put("YOUNG", new YoungDragon());
                put("WISE", new WiseDragon());
                put("UNSTABLE", new UnstableDragon());
                put("OLD", new OldDragon());
                put("PROTECTOR", new ProtectorDragon(plugin));
            }
        };
    }
    public static void broadcastWorld(Player plr, String msg) {
        for (Player player : plr.getWorld().getPlayers()) {
            player.sendMessage(Utils.chat(msg));
        }
    }
    public static void broadcastPiece(Player plr, String piece, String f) {
        broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained &6" + f.toUpperCase() + " Dragon " + piece + "&e!");
    }
}

