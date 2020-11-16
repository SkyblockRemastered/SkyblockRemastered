package xyz.apollo30.skyblockremastered.events.dragonHandler.lootTable;

import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.items.*;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class YoungDragon extends DragLootStructure {

    public YoungDragon(Player p, int w, int e) {
        f = "Young";
        plr = p;
        placedEyes = e;
        weight = w;
    }

    @Override
    public void getItem() {
        double chance = Math.random() * 100;

        if (weight >= 550 && chance < 10) {
            plr.getInventory().addItem(Accessories.YOUNG_BONE);
            broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &9Young Bone&e!");
        } else if (weight >= 450) {
            if (chance < 0.3) {
                double dragChance = Math.random() * 100;
                if (dragChance < 20) {
                    plr.getInventory().addItem(Pets.ENDER_DRAGON_LEGENDARY);
                    broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &7[Lvl 1] &6Ender Dragon&e!");
                } else {
                    plr.getInventory().addItem(Pets.ENDER_DRAGON_EPIC);
                    broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &7[Lvl 1] &5Ender Dragon&e!"); }
            } else if (chance < 25) {
                plr.getInventory().addItem(Stones.DRAGON_CLAW);
                broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &9Dragon Claw&e!");
            } else if (chance < 50) {
                plr.getInventory().addItem(Stones.DRAGON_SCALE);
                broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &9Dragon Scale&e!"); }
            else {
                if (placedEyes < 1) return;
                if (chance < placedEyes * 5) {
                    plr.getInventory().addItem(Weapons.ASPECT_OF_THE_DRAGONS);
                    broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained an &6Aspect of the Dragons&e!");
                }
            }
        } else if (weight >= 400 && chance < 50) {
            plr.getInventory().addItem(Armor.YOUNG_DRAGON_CHESTPLATE);
            broadcastPiece(plr, "Chestplate", f);
        } else if (weight >= 350 && chance < 50) {
            plr.getInventory().addItem(Armor.YOUNG_DRAGON_LEGGINGS);
            broadcastPiece(plr, "Leggings", f);
        } else if (weight >= 325 && chance < 50) {
            plr.getInventory().addItem(Armor.YOUNG_DRAGON_HELMET);
            broadcastPiece(plr, "Helmet", f);
        } else if (weight >= 300 && chance < 50) {
            plr.getInventory().addItem(Armor.YOUNG_DRAGON_BOOTS);
            broadcastPiece(plr, "Boots", f);
        }
    }

    private void broadcastWorld(Player plr, String msg) {
        for (Player player : plr.getWorld().getPlayers()) {
            player.sendMessage(Utils.chat(msg));
        }
    }

    private void broadcastPiece(Player plr, String piece, String f) {
        broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &6" + f.toUpperCase() + " Dragon " + piece + "&e!");
    }
}