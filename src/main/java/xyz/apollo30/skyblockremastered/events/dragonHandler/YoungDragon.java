package xyz.apollo30.skyblockremastered.events.dragonHandler;

import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Helper;

public class YoungDragon extends DragLootStructure {
    private final SkyblockRemastered plugin;

    YoungDragon(SkyblockRemastered plugin) {
        this.plugin = plugin;
        f = "Young";

    }

    @Override
    public void getItem() {
        double weight = Math.random(); // change
        int eyesPlaced = (int) Math.random(); // change
        double chance = Math.random() * 100;

        if (weight >= 550 && chance < 10) {
            plr.getInventory().addItem(plugin.miscs.YOUNG_BONE);
            Masterclass.broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained &9Young Bone&e!");
        } else if (weight >= 450) {
            if (chance < 0.3) {
                double dragChance = Math.random() * 100;
                if (dragChance < 20) {
                    plr.getInventory().addItem(plugin.pets.ENDER_DRAGON_LEGENDARY);
                    Masterclass.broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained &7[Lvl 1] &6Ender Dragon&e!");
                } else {
                    plr.getInventory().addItem(plugin.pets.ENDER_DRAGON_EPIC);
                    Masterclass.broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained &7[Lvl 1] &5Ender Dragon&e!"); }
            } else if (chance < 25) {
                plr.getInventory().addItem(plugin.stones.DRAGON_CLAW);
                Masterclass.broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained &9Dragon Claw&e!");
            } else if (chance < 50) {
                plr.getInventory().addItem(plugin.stones.DRAGON_SCALE);
                Masterclass.broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained &9Dragon Scale&e!"); }
            else {
                if (eyesPlaced < 1) return;
                if (chance < eyesPlaced * 5) {
                    plr.getInventory().addItem(plugin.weapons.ASPECT_OF_THE_DRAGONS);
                    Masterclass.broadcastWorld(plr, Helper.getRank(plr, true) + " &ehas obtained &6Aspect of the Dragons&e!");
                }
            }
        } else if (weight >= 400 && chance < 50) {
            plr.getInventory().addItem(plugin.armor.YOUNG_DRAGON_CHESTPLATE);
            Masterclass.broadcastPiece(plr, "Chestplate", f);
        } else if (weight >= 350 && chance < 50) {
            plr.getInventory().addItem(plugin.armor.YOUNG_DRAGON_LEGGINGS);
            Masterclass.broadcastPiece(plr, "Leggings", f);
        } else if (weight >= 325 && chance < 50) {
            plr.getInventory().addItem(plugin.armor.YOUNG_DRAGON_HELMET);
            Masterclass.broadcastPiece(plr, "Helmet", f);
        } else if (weight >= 300 && chance < 50) {
            plr.getInventory().addItem(plugin.armor.YOUNG_DRAGON_BOOTS);
            Masterclass.broadcastPiece(plr, "Boots", f);
        }
    }
}