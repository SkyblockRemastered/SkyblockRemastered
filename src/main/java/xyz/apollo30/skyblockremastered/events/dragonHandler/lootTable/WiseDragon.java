package xyz.apollo30.skyblockremastered.events.dragonHandler.lootTable;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.items.*;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class WiseDragon extends DragLootStructure {

    public WiseDragon(Player p, int w, int e) {
        plr = p;
        weight = w;
        placedEyes = e;
        f = "Wise";

    }

    @Override
    public void getItem() {
        double chance = Math.random() * 100;

        if (weight >= 550 && chance < 10) {
            giveFrags(weight - 550, Fragments.WISE_FRAGMENT);
            plr.getInventory().addItem(Weapons.MAGE_BEAM);
            broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &6Mage Beam&e!");
        } else if (weight >= 450) {
            if (chance < 0.3) {
                double dragChance = Math.random() * 100;
                giveFrags(weight - 450, Fragments.WISE_FRAGMENT);
                if (dragChance < 20) {
                    plr.getInventory().addItem(Pets.ENDER_DRAGON_LEGENDARY);
                    broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &7[Lvl 1] &6Ender Dragon&e!");
                } else {
                    plr.getInventory().addItem(Pets.ENDER_DRAGON_EPIC);
                    broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &7[Lvl 1] &5Ender Dragon&e!");
                }
            } else if (chance < 25) {
                giveFrags(weight, Fragments.WISE_FRAGMENT);
                plr.getInventory().addItem(Stones.DRAGON_CLAW);
                broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &9Dragon Claw&e!");
            } else if (chance < 50) {
                giveFrags(weight, Fragments.WISE_FRAGMENT);
                plr.getInventory().addItem(Stones.DRAGON_SCALE);
                broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &9Dragon Scale&e!");
            } else {
                if (placedEyes < 1) return;
                if (chance < placedEyes * 5) {
                    giveFrags(weight - 450, Fragments.WISE_FRAGMENT);
                    plr.getInventory().addItem(Weapons.ASPECT_OF_THE_DRAGONS);
                    broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained an &6Aspect of the Dragons&e!");
                }
            }
        } else if (weight >= 400 && chance < 50) {
            giveFrags(weight - 400, Fragments.WISE_FRAGMENT);
            plr.getInventory().addItem(Armor.WISE_DRAGON_CHESTPLATE);
            broadcastPiece(plr, "Chestplate", f);
        } else if (weight >= 350 && chance < 50) {
            giveFrags(weight - 350, Fragments.WISE_FRAGMENT);
            plr.getInventory().addItem(Armor.WISE_DRAGON_LEGGINGS);
            broadcastPiece(plr, "Leggings", f);
        } else if (weight >= 325 && chance < 50) {
            plr.getInventory().addItem(Armor.WISE_DRAGON_HELMET);
            broadcastPiece(plr, "Helmet", f);
        } else if (weight >= 300 && chance < 50) {
            giveFrags(weight - 300, Fragments.WISE_FRAGMENT);
            plr.getInventory().addItem(Armor.WISE_DRAGON_BOOTS);
            broadcastPiece(plr, "Boots", f);
        } else giveFrags(weight, Fragments.WISE_FRAGMENT);
    }

    private void giveFrags(int weight, ItemStack frags) {
        if (weight <= 0) return;
        frags.setAmount(weight >= 66 ? 3 : (int) Math.floor(weight / 22.0D));
        plr.getInventory().addItem(frags);
    }

    private void broadcastWorld(Player plr, String msg) {
        for (Player player : plr.getWorld().getPlayers()) {
            player.sendMessage(Utils.chat(msg));
        }
    }

    private void broadcastPiece(Player plr, String piece, String f) {
        broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained a &6" + f + " Dragon " + piece + "&e!");
    }
}