package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.NMSUtil;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.util.UUID;

public class SpawnEgg implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public SpawnEgg(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("spawnegg").setExecutor(this);
    }

    public ItemStack Draconis;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player plr = (Player) sender;
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        //plr.getInventory().setHelmet(plr.getItemInHand());

        switch (args[0].toLowerCase()) {
            case "zealot":
                ItemStack egg = new ItemStack(Material.MONSTER_EGG, 1, (byte) 0);
                plr.getInventory().addItem(Utils.addLore(egg, "&5Zealot Spawn Egg", "&7Spawns a Zealot"));
                break;
            case "specialzealot":
                ItemStack egg1 = new ItemStack(Material.MONSTER_EGG, 1, (byte) 0);
                plr.getInventory().addItem(Utils.addLore(egg1, "&5Special Zealot Spawn Egg", "&7Spawns a Special Zealot"));
                break;
            case "midas":
                ItemStack midas = new ItemStack(Material.GOLD_SWORD, 1);
                plr.getInventory().addItem(
                        Utils.addLore(
                                midas,
                                "&dGilded Midas' Sword",
                                "&7Damage: &c+400",
                                "&7Strength: &c+240",
                                "&7Crit Damage: &c+60%",
                                "",
                                "&7Item Ability: Greed",
                                "&7The strength and damage bonus of",
                                "&7this item is dependent on the",
                                "&7price paid for it at the &5Dark Auction&7!",
                                "&7The Maximum bonus of this item",
                                "&7is &c120&7 if the bid was",
                                "&650,000,000 Coins&7 or higher!",
                                "",
                                "&7Price Paid: &61 Coin",
                                "&7Strength Bonus: &c0",
                                "&7Damage Bonus: &c0",
                                "",
                                "&9Bryon's Compassion &8(Gilded)",
                                "&7Upon killing an enemy, you have",
                                "&7a rare chance to grant coins to",
                                "&7a player around you.",
                                "",
                                "&8This item can be reforged!",
                                "&d&kL&r &d&lMYTHIC SWORD &r&d&kL&r"));
                break;
            case "dragon":
                for (int i = 0; i < 8; i++) {
                    plr.getInventory().addItem(Utils.addLore(
                            Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ=="),
                            "&5Summoning Eye",
                            "&7Use this at the &5Ender Altar",
                            "&7in the &5Dragon's Nest&7 to",
                            "&7summon Ender Dragons!",
                            "",
                            "&5&lEPIC"));
                }
                for (int i = -674; i < -668; i++) {
                    for (int j = 8; j < 15; j++) {
                        for (int k = -279; k < -273; k++) {
                            Block blocc = plr.getWorld().getBlockAt(i, j, k);
                            if (blocc.getData() == 6) blocc.setData((byte) 0);
                        }
                    }
                }
                break;
            case "batphone":
                plr.getInventory().addItem(Utils.addLore(
                        Utils.getSkull(Utils.urlToBase64("https://textures.minecraft.net/texture/9336d7cc95cbf6689f5e8c954294ec8d1efc494a4031325bb427bc81d56a484d")),
                        "&aMaddox Batphone",
                        "&6Item Ability: Whassup? &e&lRIGHT CLICK",
                        "&7Lets you call &dMaddox&7, when",
                        "&7he's not busy.",
                        "",
                        "&a&lUNCOMMON"));
                break;
            case "inkwand":
                Utils.createInvisibleEnchantedItemByte(plr.getInventory(), 280, 0, 1, 1,
                        "&5Ink Wand",
                        "&7Damage: &c+130",
                        "&7Sterngth: &c+122",
                        "&7Crit Damage: &c+27%",
                        "&7Bonus Attack Speed: &c+7%",
                        "",
                        "&7Item Ability: Ink Bomb &e&lRIGHT CLICK",
                        "&7Shoot an ink bomb in front of",
                        "&7you dealing, &c" + Utils.coinFormat((double) (po.getIntelligence() * 100)) + " &7damage",
                        "&7and giving blindness!",
                        "&8Mana Cost:& &360",
                        "&8Cooldown: &a30s",
                        "",
                        "&5&lEPIC SWORD");
            case "draconis":
                Draconis = new ItemStack(Material.MONSTER_EGG, 1, (short) 53);
                ItemMeta meta = Draconis.getItemMeta();

                meta.setDisplayName(Utils.chat("&c&lDraconis Spawn Egg"));
                Draconis.setItemMeta(meta);
                plr.getInventory().addItem(Draconis);
            case "enderdragon":
                plr.getInventory().addItem(NMSUtil.addString(plugin.pets.enderDragonLegendary, "UUID", UUID.randomUUID().toString()));
        }

        return false;
    }
}
