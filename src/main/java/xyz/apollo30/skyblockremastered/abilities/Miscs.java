package xyz.apollo30.skyblockremastered.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.ResponsesUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class Miscs {

    // Maddox Batphone Cooldown
    public static HashMap<UUID, Integer> bcd = new HashMap<>();

    private final SkyblockRemastered plugin;

    public Miscs(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public void maddox_batphone(Player plr) {
        plr.sendMessage(Utils.chat("&e✆Ring..."));
        // gonna add delay here 1s
        plr.sendMessage(Utils.chat("&✆Ring... Ring..."));
        // gonna add delay here 1s
        plr.sendMessage(Utils.chat("&e✆Ring... Ring... Ring..."));
        // gonna add delay here 1s
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
