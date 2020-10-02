package xyz.apollo30.skyblockremastered.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class ResponsesUtils {

    public static String callSuccess(){
        String[] responses = {"✆ Hello?", "✆ Someone answers!", "✆ How does a lobster answer? Shello!", "✆ Hey what you do you need?", "✆ You hear the line pick up...", "✆ You again? What do you want this time?"};
        return Utils.chat("&a" + responses[(int) Math.floor(Math.random() * responses.length)]);
    }

    public static String callFailed(){
        String[] responses = {"✆ Please leave your message after the beep.", "✆ How can you tell if a bee is on the phone? You get a buzzy signal!", "✆ The phone keeps ringing, is it broken?", "✆ The phone picks up but it immediately hangs up!", "✆ What did the cat say on the phone? Can you hear meow?", "✆ No answer.", "✆ Seems like it's not picking up!", "✆ \"Your call is important to us, please stay on the line\", so you hang up."};
        return Utils.chat("&c" + responses[(int) Math.floor(Math.random() * responses.length)]);
    }

    public static void villagerDialog(Player plr, SkyblockRemastered plugin, String prefix, String... dialog) {

        for (int i = 0; i < dialog.length; i++) {
            int finalI = i;
            plugin.getServer().getScheduler()
                    .scheduleSyncDelayedTask(plugin, () -> {
                        plr.sendMessage(Utils.chat(prefix + " " + dialog[finalI]));
                        plr.playSound(plr.getLocation(), Sound.VILLAGER_YES, 1F, 2F);
                    }, 30L * i);
        }

    }

    public static void witherDialog(Player plr, SkyblockRemastered plugin, String prefix, String... dialog) {

        for (int i = 0; i < dialog.length; i++) {
            int finalI = i;
            plugin.getServer().getScheduler()
                    .scheduleSyncDelayedTask(plugin, () -> {
                        plr.sendMessage(Utils.chat(prefix + " " + dialog[finalI]));
                        plr.playSound(plr.getLocation(), Sound.WITHER_IDLE, .5F, 1.5F);
                    }, 30L * i);
        }

    }

}
