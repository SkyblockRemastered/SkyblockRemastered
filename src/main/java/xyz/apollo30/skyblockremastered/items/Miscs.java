package xyz.apollo30.skyblockremastered.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class Miscs {

    private final SkyblockRemastered plugin;

    public Miscs(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    // public static ItemStack youtube = GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI3Njg4ZGE0NjU4NmI4NTlhMWNkZTQwY2FlMWNkYmMxNWFiZTM1NjE1YzRiYzUyOTZmYWQwOTM5NDEwNWQwIn19fQ==");

    // public static ItemStack discord = GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM1OWU1YzdiMDczOGI1NzlmM2I0NDRjMTNhNDdiZWQ0OTZiMzA4MzhiMmVlMmIxMjdjYzU5Y2Q3OThhZWU3NyJ9fX0=");

    // public static ItemStack buycraft = GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM2ZTk0ZjZjMzRhMzU0NjVmY2U0YTkwZjJlMjU5NzYzODllYjk3MDlhMTIyNzM1NzRmZjcwZmQ0ZGFhNjg1MiJ9fX0=");

    public static ItemStack SKYBLOCK_MENU = GUIHelper.addItem(null, "NETHER_STAR", 1, 9, "&aSkyBlock Menu &7(Right Click)",
            "&7View all of your SkyBlock", "&7progress, including your Skills,",
            "&7Collections, Recipes, and more!");

    public static ItemStack REMNANT_OF_THE_EYE = GUIHelper.addLore(
            GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTdmZjIwYTRlYjQ0YWVlYWUwOTVhZTRmMzBhNGI0ZTk3NWQ1MGEzMTY1YzVlNGE0YTMyMmUzN2Q1Y2NiNTg2YyJ9fX0="),
            "&5Remnant of the Eye",
            "&7Keep this item is in your",
            "&7inventory to recover your placed",
            "&7Summoning Eye when you leave or",
            "&7when you click the Ender Altar.",
            "",
            "&7This item can also save you from certain death,",
            "&7granting you a second life",
            "&cConsumed on use",
            "&cThis item only works on the End Island",
            "",
            "&5&lEPIC");

    public static ItemStack SUMMONING_EYE = GUIHelper.addLore(
            GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ=="),
            "&5Summoning Eye",
            "&7Use this at the &5Ender Altar",
            "&7in the &5Dragon's Nest&7 to",
            "&7summon Ender Dragons!",
            "",
            "&5&lEPIC");

    public static ItemStack GRAPPLING_HOOK = GUIHelper.addLore(new ItemStack(Material.FISHING_ROD), "&aGrappling Hook", "&7Travel around in style using", "&7this Grappling Hook.", "&82 Second Cooldown", " ", "&a&lUNCOMMON");

    public static ItemStack CRYSTAL_FRAGMENT = Helper.addEnchantGlow(GUIHelper.addLore(new ItemStack(Material.QUARTZ), "&5Crystal Fragment", "&eRight-click to view recipes!", " ", "&5&lEPIC"));

    public static ItemStack WEIRD_TUBA = GUIHelper.addLore(new ItemStack(Material.HOPPER), "&9Weird Tuba", "&6Item Ability: Howl &e&lRight Click", "&7You and 4 nearby players gain:", "&c+30❁ Strength", "&f+30✦ Speed", "&7for &a 20 &7seconds.", "&8Effects doesn't stack.", "&8Mana Cost: &3150", "&8Cooldown: &a20s", " ", "&4☠ &cRequires &5Wolf Slayer 5", "&9&lRARE");

    public static ItemStack PLASMAFLUX_POWER_ORB = GUIHelper.addLore(GUIHelper.addSkull("ewogICJ0aW1lc3RhbXAiIDogMTYwMzIzMDc4NTkzNiwKICAicHJvZmlsZUlkIiA6ICI0MWQzYWJjMmQ3NDk0MDBjOTA5MGQ1NDM0ZDAzODMxYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJNZWdha2xvb24iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODNlZDRjZTIzOTMzZTY2ZTA0ZGYxNjA3MDY0NGY3NTk5ZWViNTUzMDdmN2VhZmU4ZDkyZjQwZmIzNTIwODYzYyIKICAgIH0KICB9Cn0="), "&6Plasmaflux Power Orb", "&6Item Ability: Deploy", "&7Place an orb for &a60s &7buffing", "&7up to &b5 &7player within &a20", "&7blocks.", "&8Costs 50% of max mana.", "&8Only one orb applies per player.", " ", "&6Orb Buff: Plasmaflux", "&7Grants &b+125% &7base mana regen.", "&7Heals &c3% &7of max &c❤ &7per second.", "&7Increases all heals by &a+7.5%&7.", "&7Grants &c+35❁ Strength&7", " ", "&4☠ &cRequires &5Wolf Slayer 7", "&6&lLEGENDARY");

    public static ItemStack OVERFLUX_POWER_ORB = GUIHelper.addLore(GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ4NTlkMGFkZmM5M2JlMTliYjQ0MWU2ZWRmZDQzZjZiZmU2OTEyNzIzMDMzZjk2M2QwMDlhMTFjNDgyNDUxMCJ9fX0"), "&5Overflux Power Orb", "&6Item Ability: Deploy", "&7Place an orb for &a30s &7buffing", "&7up to &b5 &7players within &a18", "&7blocks", "&8Costs 50% of max mana.", "&8Only one orb applies per player.", " ", "&aOrb Buff: Overflux", "&7Grants &b+100% &7base mana regen.", "&7Heals &c2.5% &7of max &c❤&7 per second.", "&7Increases all heals by &a+5%&7.", "&7Grants &c+25❁ Strength&7.", " ", "&4☠ &cRequires &5Wolf Slayer 7", "&5&lEPIC");

    public static ItemStack MANA_FLUX_POWER_ORB = GUIHelper.addLore(GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODJhZGExYzdmY2M4Y2YzNWRlZmViOTQ0YTRmOGZmYTlhOWQyNjA1NjBmYzdmNWY1ODI2ZGU4MDg1NDM1OTY3YyJ9fX0="), "&9Mana Flux Power Orb", "&6Item Ability: Deploy", "&7Place an orb for &a30s &7buffing", "&7up to &b5 &7players within &a18", "&7blocks", "&8Costs 50% of max mana.", "&8Only one orb applies per player.", " ", "&aOrb Buff: Mana Flux", "&7Grants &b+50% &7base mana regen.", "&7Heals &c2% &7of max &c❤&7 per second.", "&7Grants &c+10❁ Strength&7.", " ", "&4☠ &cRequires &5Wolf Slayer 6", "&9&lRARE");

    public static ItemStack RADIANT_POWER_ORB = GUIHelper.addLore(GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2FiNGM0ZDZlZTY5YmMyNGJiYTJiOGZhZjY3YjlmNzA0YTA2YjAxYWE5M2YzZWZhNmFlZjdhOTY5NmM0ZmVlZiJ9fX0="), "&aRadiant Power Orb", "&6Item Ability: Deploy", "&7Place an orb for &a30s &7buffing", "&7up to &b5 &7players within &a18", "&7blocks", "&8Costs 50% of max mana.", "&8Only one orb applies per player.", " ", "&aOrb Buff: Radiant", "&7Heals &c1% &7of max &c❤&7 per second.", " ", "&4☠ &cRequires &5Wolf Slayer 2", "&a&lUNCOMMON");

}

