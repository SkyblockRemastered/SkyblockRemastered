package xyz.apollo30.skyblockremastered.guis.Admin;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.guis.Bank.BankDeposit;
import xyz.apollo30.skyblockremastered.guis.Bank.BankWithdraw;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.guis.SkyblockMenu;
import xyz.apollo30.skyblockremastered.guis.constructors.Menu;
import xyz.apollo30.skyblockremastered.guis.constructors.MenuUtility;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class StatsMenu extends Menu {

    private final Player plr;
    private final Player target;

    public StatsMenu(MenuUtility menuUtility, Player p, Player t) {
        super(menuUtility);
        plr = p;
        target = t;
    }

    @Override
    public String getMenuName() {
        return "Stats Editor";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        String title = e.getInventory().getTitle();
        ItemStack item = e.getCurrentItem();
        ClickType type = e.getClick();

        PlayerObject po = PlayerManager.playerObjects.get(target);

        if (title.equals("Stats Editor")) {
            if (Helper.hasCustomName(item)) {
                String itemName = Helper.getCustomName(item);
                if (itemName.equalsIgnoreCase(Utils.chat("&aGo Back"))) {
                    new SkyblockMenu(SkyblockRemastered.getMenuUtility(plr), plr).open();
                } else if (itemName.equalsIgnoreCase(Utils.chat("&cClose"))) {
                    plr.closeInventory();
                } else if (itemName.contains("Health")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBaseHealth() - 100;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseHealth(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseHealth(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBaseHealth() + 100;
                        if (newAmount >= 2000) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 2000."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseHealth(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseHealth(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                } else if (itemName.contains("Defense")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBaseDefense() - 100;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseDefense(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseDefense(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBaseDefense() + 100;
                        if (newAmount >= 5000) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 5000."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseDefense(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseDefense(newAmount);
                            setMenuItems();
                        }
                    }
                    plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                } else if (itemName.contains("Strength")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBaseStrength() - 100;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseStrength(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseStrength(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBaseStrength() + 100;
                        if (newAmount >= 1000) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 1000."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseStrength(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseStrength(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                } else if (itemName.contains("Speed")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBaseSpeed() - 50;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseSpeed(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseSpeed(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBaseSpeed() + 50;
                        if (newAmount >= 500) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 500."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseSpeed(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseSpeed(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                } else if (itemName.contains("Crit Damage")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBaseCritDamage() - 100;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseCritDamage(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseCritDamage(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBaseCritDamage() + 100;
                        if (newAmount >= 1000) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 1000."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseCritDamage(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseCritDamage(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                } else if (itemName.contains("Crit Chance")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBaseCritChance() - 2;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseCritChance(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseCritChance(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBaseCritChance() + 2;
                        if (newAmount >= 100) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 100."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseCritChance(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseCritChance(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                } else if (itemName.contains("Bonus Attack Speed")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getAtkSpeed() - 100;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setAtkSpeed(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setAtkSpeed(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getAtkSpeed() + 100;
                        if (newAmount >= 100) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 100."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setAtkSpeed(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setAtkSpeed(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                } else if (itemName.contains("Intelligence")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBaseIntelligence() - 100;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseIntelligence(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseIntelligence(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBaseIntelligence() + 100;
                        if (newAmount >= 3000) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 3000."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseIntelligence(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseIntelligence(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                } else if (itemName.contains("Sea Creature Chance")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBaseSeaCreatureChance() - 100;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseSeaCreatureChance(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseSeaCreatureChance(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBaseSeaCreatureChance() + 100;
                        if (newAmount >= 100) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 100."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseSeaCreatureChance(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseSeaCreatureChance(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                } else if (itemName.contains("Magic Find")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBaseMagicFind() - 100;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseMagicFind(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseMagicFind(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBaseMagicFind() + 100;
                        if (newAmount >= 500) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 500."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseMagicFind(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseMagicFind(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                } else if (itemName.contains("Pet Luck")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBasePetLuck() - 5;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBasePetLuck(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBasePetLuck(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBasePetLuck() + 5;
                        if (newAmount >= 200) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 200."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBasePetLuck(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBasePetLuck(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                } else if (itemName.contains("Ferocity")) {
                    if (type.isLeftClick()) {
                        int newAmount = po.getBaseFerocity() - 5;
                        if (newAmount <= 0) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go below the number 0."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseFerocity(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseFerocity(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    } else if (type.isRightClick()) {
                        int newAmount = po.getBaseFerocity() + 5;
                        if (newAmount >= 100) {
                            if (!plr.isOp()) {
                                plr.sendMessage(Utils.chat("&cYou cannot go over 100."));
                                plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 100F, 1F);
                                return;
                            } else {
                                po.setBaseFerocity(newAmount);
                                setMenuItems();
                            }
                        } else {
                            po.setBaseFerocity(newAmount);
                            setMenuItems();
                        }
                        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 100F, 1F);
                    }
                }
            }
        }
    }

    @Override
    public void setMenuItems() {

        PlayerObject po = PlayerManager.playerObjects.get(target);
        if (po == null) {
            plr.sendMessage(Utils.chat("&cError when loading the player's data, please try again later."));
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

        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 27, 28, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 51, 52, 53, 54);
        GUIHelper.addSkull(inv, plr.getName(), 1, 14, "&aYour Skyblock Profile",
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
                "  &c" + GUIHelper.getUnicode("ferocity") + " Ferocity &f" + ferocity);
        GUIHelper.addItem(inv, 260, 1, 20, "&c" + GUIHelper.getUnicode("heart") + " Health &f" + po.getBaseHealth(), " ", "&bRight-click to increase by 100", "&eLeft-click to decrease by 100");
        GUIHelper.addItem(inv, 307, 1, 21, "&a" + GUIHelper.getUnicode("defense") + " Defense &f" + po.getBaseDefense(), " ", "&bRight-click to increase by 100", "&eLeft-click to decrease by 100");
        GUIHelper.addItem(inv, 377, 1, 22, "&c" + GUIHelper.getUnicode("strength") + " Strength &f" + po.getBaseStrength(), " ", "&bRight-click to increase by 100", "&eLeft-click to decrease by 100");
        GUIHelper.addItem(inv, 353, 1, 23, "&f" + GUIHelper.getUnicode("speed") + " Speed &f" + po.getBaseSpeed(), " ", "&bRight-click to increase by 50", "&eLeft-click to decrease by 50");
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(Utils.urlToBase64("964e1c3e315c8d8fffc37985b6681c5bd16a6f97ffd07199e8a05efbef103793")), "&9" + GUIHelper.getUnicode("cc") + " Crit Chance &f" + po.getBaseCritChance(), " ", "&bRight-click to increase by 2", "&eLeft-click to decrease by 2"), 24);
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(Utils.urlToBase64("964e1c3e315c8d8fffc37985b6681c5bd16a6f97ffd07199e8a05efbef103793")), "&9" + GUIHelper.getUnicode("cd") + " Crit Damage &f" + po.getBaseCritDamage(), " ", "&bRight-click to increase by 100", "&eLeft-click to decrease by 100"), 25);
        GUIHelper.addItem(inv, 283, 1, 26, "&e" + GUIHelper.getUnicode("atkspeed") + " Bonus Attack Speed &f" + po.getBaseAtkSpeed(), " ", "&bRight-click to increase by 100", "&eLeft-click to decrease by 100");
        GUIHelper.addItem(inv, GUIHelper.addLore(new ItemStack(Material.ENCHANTED_BOOK), "&b" + GUIHelper.getUnicode("intel") + " Intelligence &f" + po.getBaseIntelligence(), " ", "&bRight-click to increase by 100", "&eLeft-click to decrease by 100"), 29);
        GUIHelper.addItem(inv, 409, 1, 30, "&3" + GUIHelper.getUnicode("seacreature") + " Sea Creature Chance &f" + po.getBaseSeaCreatureChance(), " ", "&bRight-click to increase by 100", "&eLeft-click to decrease by 100");
        GUIHelper.addItem(inv, 264, 1, 31, "&b" + GUIHelper.getUnicode("mf") + " Magic Find &f" + po.getBaseMagicFind(), " ", "&bRight-click to increase by 100", "&eLeft-click to decrease by 100");
        GUIHelper.addItem(inv, 351, 9, 1, 32, "&d" + GUIHelper.getUnicode("petluck") + " Pet Luck &f" + po.getBasePetLuck(), " ", "&bRight-click to increase by 5", "&eLeft-click to decrease by 5");
        GUIHelper.addItem(inv, 385, 1, 33, "&c" + GUIHelper.getUnicode("ferocity") + " Ferocity &f" + po.getBaseFerocity(), " ", "&bRight-click to increase by 5", "&eLeft-click to decrease by 5");

        GUIHelper.addItem(inv, 262, 1, 49, "&aGo Back", "&7Back to Skyblock Menu");
        GUIHelper.addItem(inv, 166, 1, 50, "&cClose");
    }
}
