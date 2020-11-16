package xyz.apollo30.skyblockremastered.events.dragonHandler.lootTable;

import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.items.Armor;
import xyz.apollo30.skyblockremastered.items.Pets;
import xyz.apollo30.skyblockremastered.items.Stones;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class CelestialDragon extends DragLootStructure {

    public CelestialDragon(Player p, int w) {
        plr = p;
        weight = w;
        f = "Celestial";
    }

    @Override
    public void getItem() {
        double chance = Math.random() * 100;

        if (weight >= 550 && chance < 10) {
            plr.getInventory().addItem(Stones.EMPOWERED_STONE);
            broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained an &6Empowered Stone&e!");
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
        } else if (weight >= 400 && chance < 50) {
            plr.getInventory().addItem(Armor.CELESTIAL_DRAGON_CHESTPLATE);
            broadcastPiece(plr, "Chestplate", f);
        } else if (weight >= 350 && chance < 50) {
            plr.getInventory().addItem(Armor.CELESTIAL_DRAGON_LEGGINGS);
            broadcastPiece(plr, "Leggings", f);
        } else if (weight >= 325 && chance < 50) {
            plr.getInventory().addItem(Armor.CELESTIAL_DRAGON_HELMET);
            broadcastPiece(plr, "Helmet", f);
        } else if (weight >= 300 && chance < 50) {
            plr.getInventory().addItem(Armor.CELESTIAL_DRAGON_BOOTS);
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
