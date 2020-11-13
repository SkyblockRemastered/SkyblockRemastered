package xyz.apollo30.skyblockremastered.GUIs;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.GUIs.constructor.Menu;
import xyz.apollo30.skyblockremastered.GUIs.constructor.MenuUtility;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.events.playerHandler.TradeMenu;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.templates.PlayerTemplate;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class ProfileViewer extends Menu {

    private final Player target;
    private final Player plr;

    public ProfileViewer(MenuUtility menuUtility, Player plr, Player target) {
        super(menuUtility);
        this.plr = plr;
        this.target = target;
    }

    @Override
    public String getMenuName() {
        return target.getName() + "'s Profile";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getInventory().getName().equals(target.getName() + "'s Profile")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat("&aTrade"))) {
                TradeMenu.openTradeMenu(plr, target);
                plr.closeInventory();
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat("&aVisit Island"))) {
                plr.sendMessage(Utils.chat("&7Sending you to " + target.getName() + "'s island."));
                World island = Bukkit.getServer().createWorld(new WorldCreator("playerislands/" + target.getUniqueId().toString()));
                Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
                plr.teleport(loc);
                plr.closeInventory();
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat("&aEnderchest"))) {
                new EnderChestViewer(SkyblockRemastered.getMenuUtility(plr), target).open();
            }
        }
    }

    @Override
    public void setMenuItems() {

        PlayerTemplate po = PlayerManager.playerObjects.get(target);
        if (po == null) {
            plr.sendMessage(Utils.chat("&cCouldn't load " + target.getName() + "'s Profile! Please try again later!"));
            return;
        }

        int health = po.getMaxHealth();
        int defense = po.getDefense();
        int strength = po.getStrength();
        int speed = po.getSpeed();
        int critdamage = po.getCritDamage();
        int critchance = po.getCritChance();
        int atkspeed = po.getAtkSpeed();
        int intel = po.getMaxIntelligence();
        int seacreaturechance = po.getSeaCreatureChance();
        int magicfind = po.getMagicFind();
        int petluck = po.getPetLuck();
        int ferocity = po.getFerocity();

        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 18, 19, 22, 24, 27, 28, 31, 32, 33, 36, 37, 39, 40, 41, 42, 45, 46, 47, 48, 49, 51, 52, 53, 54);

        GUIHelper.addItem(inv, 166, 1, 1, 50, "&cClose");
        GUIHelper.addItem(inv, 130, 1, 1, 17, "&aEnderchest");
        GUIHelper.addItem(inv, GUIHelper.addLore(
                GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTk4ZGY0MmY0NzdmMjEzZmY1ZTlkN2ZhNWE0Y2M0YTY5ZjIwZDljZWYyYjkwYzRhZTRmMjliZDE3Mjg3YjUifX19"),
                "&aBank",
                "&fPurse: &6" + Utils.doubleFormat(po.getPurse()),
                "&fBank: &6" + Utils.doubleFormat(po.getBank()),
                "&fBits: &b" + Utils.doubleFormat(po.getBits())),
                16);
        GUIHelper.addItem(inv, 276, 1, 25, "&aSkills");
        GUIHelper.addItem(inv, 388, 1, 26, "&aTrade");
        GUIHelper.addItem(inv, 321, 1, 34, "&aCollections");
        GUIHelper.addItem(inv, 288, 1, 35, "&aVisit Island");
        GUIHelper.addItem(inv, 299, 1, 43, "&aWardrobe");
        GUIHelper.addItem(inv, 38, 0, 1, 44, "&aInvite to Island");
        GUIHelper.addSkull(inv, target.getName(), 1, 23, "&aYour Skyblock Profile",
                "  " + (po.getAbsorptionHealth() > 0 ? "&e" : "&c") + GUIHelper.getUnicode("heart") + " Health " + (po.getAbsorptionHealth() > 0 ? "" : "&f") + (health + po.getExtraHealth() + po.getAbsorptionHealth()) + " HP",
                "  &a" + GUIHelper.getUnicode("defense") + " Defense &f" + defense,
                po.getTrueDefense() > 0 ? "  &f" + GUIHelper.getUnicode("td") + " True Defense " + po.getTrueDefense() : "",
                "  &c" + GUIHelper.getUnicode("strength") + " Strength &f" + strength,
                "  &f" + GUIHelper.getUnicode("speed") + " Speed " + speed,
                "  &9" + GUIHelper.getUnicode("cc") + " Crit Chance &f" + critchance + "%",
                "  &9" + GUIHelper.getUnicode("cd") + " Crit Damage &f" + critdamage + "%",
                "  &e" + GUIHelper.getUnicode("atkspeed") + " Bonus Attack Speed &f" + atkspeed + "%",
                "  &b" + GUIHelper.getUnicode("intel") + " Intelligence &f" + (intel + po.getExtraIntelligence()),
                "  &3" + GUIHelper.getUnicode("seacreature") + "&3 Sea Creature Chance &f" + seacreaturechance + "%",
                "  &b" + GUIHelper.getUnicode("mf") + " Magic Find &f" + magicfind,
                "  &d" + GUIHelper.getUnicode("petluck") + " Pet Luck &f" + petluck,
                "  &c" + GUIHelper.getUnicode("ferocity") + " Ferocity &f" + ferocity,
                " ", "&eClick to view your profile!");

        // Armor
        EntityEquipment equipment = target.getEquipment();
        ItemStack helmet = equipment.getHelmet();
        ItemStack chestplate = equipment.getChestplate();
        ItemStack leggings = equipment.getLeggings();
        ItemStack boots = equipment.getBoots();
        ItemStack heldItem = target.getItemInHand();
        ItemStack pet = null;

        if (helmet != null) GUIHelper.addItem(inv, helmet, 11);
        else GUIHelper.addItem(inv, 160, 8, 1, 11, "&7Helmet &8[&cEMPTY&8]");

        if (chestplate != null) GUIHelper.addItem(inv, chestplate, 20);
        else GUIHelper.addItem(inv, 160, 8, 1, 20, "&7Chestplate &8[&cEMPTY&8]");

        if (leggings != null) GUIHelper.addItem(inv, leggings, 29);
        else GUIHelper.addItem(inv, 160, 8, 1, 29, "&7Leggings &8[&cEMPTY&8]");

        if (boots != null) GUIHelper.addItem(inv, boots, 38);
        else GUIHelper.addItem(inv, 160, 8, 1, 38, "&7Boots &8[&cEMPTY&8]");

        if (heldItem != null && heldItem.getType() != Material.AIR) GUIHelper.addItem(inv, heldItem, 21);
        else GUIHelper.addItem(inv, 160, 8, 1, 21, "&7Held Item &8[&cEMPTY&8]");

        if (pet != null) GUIHelper.addItem(inv, pet, 30);
        else GUIHelper.addItem(inv, 160, 8, 1, 30, "&7Pet &8[&cEMPTY&8]");
    }

}
