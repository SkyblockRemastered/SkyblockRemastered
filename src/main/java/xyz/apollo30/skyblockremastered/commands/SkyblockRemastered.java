package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.ResponsesUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class SkyblockRemastered implements CommandExecutor {

    private final xyz.apollo30.skyblockremastered.SkyblockRemastered plugin;

    public SkyblockRemastered(xyz.apollo30.skyblockremastered.SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("skyblockremastered").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {

            if (args[0].equalsIgnoreCase("announce")) {
                String announcement = "";
                for (int i = 1; i < args.length; i++) {
                    announcement += args[i] + " ";
                }
                Utils.broadCast(Utils.chat(announcement));
            } else if (args[0].equalsIgnoreCase("plrannounce")) {
                String uuid = Utils.getUuid(args[1]);
                Player plr = Bukkit.getPlayer(uuid);
                if (plr == null) return false;
                String announcement = "";
                for (int i = 2; i < args.length; i++) {
                    announcement += args[i] + " ";
                }
                plr.sendMessage(Utils.chat(announcement));
            }
            return false;
        }

        Player plr = (Player) sender;

        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);
        //if (!plr.isOp()) return false;
        String prefix = "&6Skyblock &8» &7";

        if (args[0].equalsIgnoreCase("set")) {
            if (args[1].equalsIgnoreCase("speed")) {
                player_data.setSpeed(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Speed set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("health")) {
                player_data.setMaxHealth(Integer.parseInt(args[2]));
                player_data.resetHealth();
                plr.sendMessage(Utils.chat(prefix + "Health set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("defense")) {
                player_data.setDefense(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Defense set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("cd")) {
                player_data.setCritDamage(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Crit Damage set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("cc")) {
                player_data.setCritChance(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Crit Chance set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("intel")) {
                player_data.setMaxIntelligence(Integer.parseInt(args[2]));
                player_data.resetIntelligence();
                plr.sendMessage(Utils.chat(prefix + "Intelligence set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("mf")) {
                player_data.setMagicFind(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Magic Find set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("str")) {
                player_data.setStrength(Integer.parseInt(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Strength set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("purse")) {
                player_data.setPurse(Double.parseDouble(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Purse set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("bank")) {
                player_data.setPurse(Double.parseDouble(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Bank set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("gems")) {
                player_data.setGems(Double.parseDouble(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Gems set to " + args[2]));
            } else if (args[1].equalsIgnoreCase("addcoins")) {
                player_data.setCoins_gained(Double.parseDouble(args[2]));
                plr.sendMessage(Utils.chat(prefix + "Added " + args[2] + " coins."));
            }
        } else if (args[0].equalsIgnoreCase("test")) {
            if (!plr.isOp()) return false;
            ItemStack item = new ItemStack(Material.MONSTER_EGG, 1, (short) 0);
            plr.getInventory().addItem(item);
        } else if (args[0].equalsIgnoreCase("bank")) {
            GuiUtils.bankMenu(plr, plr.getUniqueId().toString(), plugin);
        } else if (args[0].equalsIgnoreCase("dialogs")) {
            if (args[1].equalsIgnoreCase("clerk")) {
                ResponsesUtils.villagerDialog(plr, plugin, "&e[NPC] Clerk Seraphine:", "&fOh hello! You're here for the mayor elections?", "&fWell, this server ain't pay to win, &c&lGET OUTTA HERE!");
            } else if (args[1].equalsIgnoreCase("hub")) {
                ResponsesUtils.witherDialog(plr, plugin, "&e[NPC] Apollo30:", "&fWelcome to the server! In here this server is just a remake of Hypixel Skyblock!", "&fAs of now, this server is still in beta mode.", "&fWhen the remake is done, most things in the server will be changed.", "&fThis is so I do not get sued.");
            }
        } else if (args[0].equalsIgnoreCase("template-island")) {
            if (!plr.isOp()) return false;
            World island = Bukkit.getServer().createWorld(new WorldCreator("private_island_template"));
            Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
            plr.teleport(loc);
        } else if (args[0].equalsIgnoreCase("hub2")) {
            if (!plr.isOp()) return false;
            World island = Bukkit.getServer().createWorld(new WorldCreator("hub2"));
            Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
            plr.teleport(loc);
        }


        return false;
    }
}
