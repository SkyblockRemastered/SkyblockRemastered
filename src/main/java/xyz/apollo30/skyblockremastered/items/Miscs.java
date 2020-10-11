package xyz.apollo30.skyblockremastered.items;

import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.NMSUtil;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.UUID;

public class Miscs {

    private final SkyblockRemastered plugin;

    public Miscs(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public ItemStack youtube = Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI3Njg4ZGE0NjU4NmI4NTlhMWNkZTQwY2FlMWNkYmMxNWFiZTM1NjE1YzRiYzUyOTZmYWQwOTM5NDEwNWQwIn19fQ==");

    public ItemStack discord = Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM1OWU1YzdiMDczOGI1NzlmM2I0NDRjMTNhNDdiZWQ0OTZiMzA4MzhiMmVlMmIxMjdjYzU5Y2Q3OThhZWU3NyJ9fX0=");

    public ItemStack buycraft = Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM2ZTk0ZjZjMzRhMzU0NjVmY2U0YTkwZjJlMjU5NzYzODllYjk3MDlhMTIyNzM1NzRmZjcwZmQ0ZGFhNjg1MiJ9fX0=");

    public ItemStack skyblockMenu = Utils.createItem(null, "NETHER_STAR", 1, 9, "&aSkyBlock Menu &7(Right Click)",
            "&7View all of your SkyBlock", "&7progress, including your Skills,",
            "&7Collections, Recipes, and more!");

    public ItemStack sleepingEye = Utils.addLore(
            Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTdmZjIwYTRlYjQ0YWVlYWUwOTVhZTRmMzBhNGI0ZTk3NWQ1MGEzMTY1YzVlNGE0YTMyMmUzN2Q1Y2NiNTg2YyJ9fX0="),
            "&5Sleeping Eye",
            "&7Keep this item is in your",
            "&7inventory to recover your placed",
            "&7Summoning Eye when you leave or",
            "&7when you click the Ender Altar.",
            "&7This item becomes imbued with",
            "&7the magic of the Dragon when it",
            "&7spawns, turning it into a",
            "",
            "&5&lEPIC");

    public ItemStack summoningEye = Utils.addLore(
            Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ=="),
            "&5Summoning Eye",
            "&7Use this at the &5Ender Altar",
            "&7in the &5Dragon's Nest&7 to",
            "&7summon Ender Dragons!",
            "",
            "&5&lEPIC");
}
