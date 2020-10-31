package xyz.apollo30.skyblockremastered.dragons;

import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.HashMap;

public class Masterclass {
    public static HashMap<String, Dragon> dragonMap;
    private final HashMap<Player, Integer> placedPlayerEyes = new HashMap<>();

    public Masterclass(SkyblockRemastered plugin) {
        dragonMap = new HashMap<String, Dragon>() {
            {
                put("CELESTIAL", new CelestialDragon(plugin));
                put("SUPERIOR", new SuperiorDragon(plugin));
                put("STRONG", new StrongDragon(plugin));
                put("YOUNG", new YoungDragon(plugin));
                put("WISE", new WiseDragon(plugin));
                put("UNSTABLE", new UnstableDragon(plugin));
                put("OLD", new OldDragon(plugin));
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

