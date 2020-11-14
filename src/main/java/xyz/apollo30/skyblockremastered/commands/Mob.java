package xyz.apollo30.skyblockremastered.commands;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.util.HashSet;

public class Mob implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public Mob(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("mob").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        String prefix = "&6Skyblock &8» &7";
        Player plr = (Player) sender;

        Location mobLocation = plr.getTargetBlock((HashSet<Byte>) null, 100).getLocation().add(0, 3, 0);
        int amount;

        if (args.length == 0) {
            plr.sendMessage(Utils.chat(prefix + "&cMissing MOB_TYPE"));
            return true;
        }

        if ((args.length == 1)) amount = 1;
        else amount = Integer.parseInt(args[1]);

        String customName = null;
        String type = args[0];

        if (args[0].split("\\.").length == 2) {
            customName = WordUtils.capitalizeFully(args[0].split("\\.")[1].toLowerCase().replaceAll("_", " "));
            type = args[0].split("\\.")[0];
        }

        type = type.toUpperCase();

        try {
            for (int i = 0; i < amount; i++) {
                LivingEntity entity = (LivingEntity) plr.getWorld().spawnEntity(mobLocation, EntityType.valueOf(type));
                plugin.mobManager.createMob(entity, customName != null ? customName : args.length == 1 ? WordUtils.capitalizeFully(args[0].replaceAll("_", "").toLowerCase()) : args[2]);
            }
            plr.sendMessage(Utils.chat(prefix + "&aSpawned " + amount + " " + type + (customName != null ? " with the name of " + customName : ".")));
        } catch (Exception e) {
            plr.sendMessage(Utils.chat(prefix + "&cInvalid Mob Type: " + type));
            plr.sendMessage(Utils.chat(prefix + "&cERROR: " + e.toString()));
        }

        return false;
    }

}
