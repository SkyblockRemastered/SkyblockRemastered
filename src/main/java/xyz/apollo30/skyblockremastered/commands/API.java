package xyz.apollo30.skyblockremastered.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.templates.PlayerTemplate;
import xyz.apollo30.skyblockremastered.utils.MongoUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class API implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public API(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("api").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = plugin.so.getPrefix();
        Player player;
        if (sender instanceof Player) player = (Player) sender;
        else {
            if (args.length < 1) {
                sender.sendMessage("You must specify a player.");
                return false;
            } else {
                player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage("That is not a valid player");
                    return false;
                }
            }
        }

        String key = UUID.randomUUID().toString().replaceAll("-", "");
        PlayerTemplate po = PlayerManager.playerObjects.get(player);
        int minuteLimit = player.isOp() ? 120 : 60;
        List<String> permissions = new ArrayList<String>();
        if (player.isOp()) permissions.add("all");
        else {
            permissions.add("query_player");
            permissions.add("query_own_key");
        }
        MongoUtils.insertOrReplace(MongoUtils.getInstance().getKeyCollection(),
                new Document("owner_uuid", player.getUniqueId().toString()),
                new Document("owner_uuid", player.getUniqueId().toString())
        .append("key", key)
        .append("permissions", permissions)
        .append("limit", minuteLimit)
        .append("uses_minute", 0)
        .append("total_requests", 0)
        .append("last_req", 0));

        TextComponent comp = new TextComponent(Utils.chat("&2New API key generated: &8" + key + " &2[hover for info]"));
        comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click this to paste your key into chat to be copied").create()));
        comp.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, key));
        player.spigot().sendMessage(comp);
        if (!(sender instanceof Player)) sender.sendMessage("Created a new API key for " + player.getName());
        return false;

    }
}
