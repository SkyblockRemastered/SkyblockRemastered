package xyz.apollo30.skyblockremastered.dragons;

import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.items.*;
import xyz.apollo30.skyblockremastered.utils.Helper;

import java.util.HashMap;

public class ProtectorDragon extends Dragon {
     private final SkyblockRemastered plugin;
    private final HashMap<Player, Integer> placedPlayerEyes = new HashMap<>();
    ProtectorDragon(SkyblockRemastered plugin){
        this.plugin = plugin;
        f = "Protector";

    }
    @Override
    public void getItem() {
        Armor a = new Armor(plugin);
        Pets p = new Pets(plugin);
        Stones s = new Stones(plugin);
        Miscs m = new Miscs(plugin);
        Weapons w = new Weapons(plugin);
        int weight = (int) Math.random();
        int damage = (int) Math.random();
        int placedEyes = (int) Math.random();
        double chance = Math.random() * 100;

        if (weight <=0 || damage <= 0) { Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " has 0 weight L");
        } else if (weight >= 550 && chance < 15) {
            Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &9Resistant Scale&e!");
            plr.getInventory().addItem(m.RESISTANT_SCALE);
        } else if (weight >= 450) {
            // edrag chance
            if (chance < 0.3) {
                // got edrag
                double dragChance = Math.random() * 100;
                if (dragChance < 20) {
                    plr.getInventory().addItem(p.ENDER_DRAGON_LEGENDARY);
                    Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &7[Lvl 1] &6Ender Dragon&e!");
                }
                // got epic
                else {
                    plr.getInventory().addItem(p.ENDER_DRAGON_EPIC);
                    Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &7[Lvl 1] &5Ender Dragon&e!");
                }
            }
            else if (chance < 15) {
                plr.getInventory().addItem(w.ASPECT_OF_THE_DRAGONS);
                Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &6Aspect of the Dragons&e!");
            }
            else if (chance < 25) {
                plr.getInventory().addItem(s.DRAGON_CLAW);
                Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &9Dragon Claw&e!");
            }
            else if (chance < 50) {
                plr.getInventory().addItem(s.DRAGON_SCALE);
                Masterclass.broadcastWorld(plr, Helper.getRank(plr) + " &ehas obtained &9Dragon Scale&e!");
            }
        }
        else if (weight >= 400 && chance < 50) {
            plr.getInventory().addItem(a.PROTECTOR_DRAGON_CHESTPLATE);
            Masterclass.broadcastPiece(plr, "Chestplate", f);
        }
        else if (weight >= 350 && chance < 50) {
            plr.getInventory().addItem(a.PROTECTOR_DRAGON_LEGGINGS);
            Masterclass.broadcastPiece(plr, "Leggings", f);
        }
        else if (weight >= 325 && chance < 50) {
            plr.getInventory().addItem(a.PROTECTOR_DRAGON_HELMET);
            Masterclass.broadcastPiece(plr, "Helmet", f);
        }
        else if (weight >= 300 && chance < 50) {
            plr.getInventory().addItem(a.PROTECTOR_DRAGON_BOOTS);
            Masterclass.broadcastPiece(plr, "Boots", f);
        }
    }
}

