package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.GUIs.CreditsMenu;
import xyz.apollo30.skyblockremastered.GUIs.GUIHelper;
import xyz.apollo30.skyblockremastered.GUIs.SkyblockMenu;
import xyz.apollo30.skyblockremastered.GUIs.constructor.Menu;
import xyz.apollo30.skyblockremastered.GUIs.GUIs;
import xyz.apollo30.skyblockremastered.constants.Constants;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.templates.PlayerTemplate;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.util.ArrayList;
import java.util.List;

public class InventoryEvents implements Listener {

    private final SkyblockRemastered plugin;

    public InventoryEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent e) {

        if (e.getInventory().getHolder() instanceof Menu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }
            // Since we know our inventoryholder is a menu, get the Menu Object representing
            // the menu we clicked on
            Menu menu = (Menu) e.getInventory().getHolder();
            // Call the handleMenu object which takes the event and processes it
            menu.handleMenu(e);
        }

        if (e.getCurrentItem() == null) return;

        /**
         * Checks if the player shifts click a skull
         * Then checks if the item name has the word "Helmet"
         * Then it puts it on for them.
         */
        if (e.getInventory().getName().equalsIgnoreCase("container.crafting") && e.getClick().isShiftClick() && e.getCurrentItem().hasItemMeta() && !e.getCurrentItem().getItemMeta().getDisplayName().isEmpty() && e.getCurrentItem().getItemMeta().getDisplayName().contains("Helmet")) {
            if (e.getWhoClicked().getInventory().getHelmet() == null) {
                e.getWhoClicked().getInventory().setHelmet(new ItemStack(e.getCurrentItem()));
                e.setCurrentItem(null);
            }
        }

        Player plr = (Player) e.getView().getPlayer();
        String title = e.getInventory().getTitle();
        String item = e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() ? e.getCurrentItem().getItemMeta().getDisplayName() : null;
        String clickType = e.getClick().toString();

        PlayerTemplate po = plugin.playerManager.getPlayerData(plr);

        if (title == null || item == null)
            return;

        if (item.equals(Utils.chat("&aCredits"))) {
            new CreditsMenu(SkyblockRemastered.getMenuUtility(plr)).open();
        } else if (item.equals(Utils.chat("&aPotion Bag"))) {
            if (po.getPotionBag() == null) {
                Inventory inv = Bukkit.createInventory(plr, 18, "Potion Bag");
                GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 10, 11, 12, 15, 16, 17, 18);
                GUIHelper.addItem(inv, 262, 1, 13, "&aGo Back");
                GUIHelper.addItem(inv, 166, 1, 14, "&cClose");
                po.setPotionBag(Helper.inventoryToString(inv));
            }
            Inventory inv = Helper.stringToInventory(po.getPotionBag());
            plr.openInventory(inv);
        } else if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
            SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), plugin);
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&8Quiver Arrow"))) {
            SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), plugin);
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&aPersonal Vault"))) {
            plr.openInventory(plr.getEnderChest());
            plr.playSound(plr.getLocation(), Sound.CHEST_OPEN, 1F, 0.3F);
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&aCrafting Table"))) {
            GUIs.craftingMenu(plr, plr.getUniqueId().toString(), "empty");
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&aTrades"))) {
            GUIs.tradeMenu(plr, plr.getUniqueId().toString());
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&cClose"))) {
            plr.closeInventory();
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&aPersonal Bank"))) {
            GUIs.bankMenu(plr, plr.getUniqueId().toString(), plugin);
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&bFast Travel"))) {
            if (clickType.equals("RIGHT")) {
                if (plr.getWorld().getName().equals("playerislands/" + plr.getUniqueId().toString())) {
                    plr.sendMessage(Utils.chat("&cYou are already at your island"));
                } else {
                    plr.sendMessage(Utils.chat("&7Sending you to your island."));
                    World island = Bukkit.getServer().createWorld(new WorldCreator("playerislands/" + plr.getUniqueId().toString()));
                    Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
                    plr.teleport(loc);
                }
                e.setCancelled(true);
            } else if (clickType.equals("LEFT")) {
                if (plr.getWorld().getName().equals("hub")) {
                    plr.sendMessage(Utils.chat("&cYou are already at the hub."));
                    e.setCancelled(true);
                } else {
                    plr.sendMessage(Utils.chat("&7Sending you to the hub."));
                    World island = Bukkit.getServer().createWorld(new WorldCreator("hub"));
                    Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
                    plr.teleport(loc);
                    e.setCancelled(true);
                }
            }
        } else if (item.equals(Utils.chat("&aYour Skills"))) {
            GUIs.skillMenu(plr, plr.getUniqueId().toString());
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&aGo Back"))) {
            SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), plugin);
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&aPlugin Developer Menu"))) {
            GUIs.adminPanel(plr, plr.getUniqueId().toString());
            e.setCancelled(true);
        }

        // Trade Menu
        switch (title) {
            case "SkyBlock Menu":
                if (e.isShiftClick() || e.isRightClick() || e.isLeftClick())
                    e.setCancelled(true);
            case "Trades":
                e.setCancelled(true);
                if (item.equals(Utils.chat("&fCoal &8x2"))) {
                    ItemStack need = new ItemStack(Material.LOG, 1, (short) 0);
                    ItemStack give = new ItemStack(Material.COAL);
                    if (Helper.consumeItem(plr, 1, need)) {
                        Utils.successTrade(plr, give);
                    } else Utils.failTrade(plr);
                } else if (item.equals(Utils.chat("&fGrass &8x4"))) {
                    ItemStack need = new ItemStack(Material.DIRT);
                    ItemStack give = new ItemStack(Material.GRASS, 4);
                    if (Helper.consumeItem(plr, 4, need)) {
                        Utils.successTrade(plr, give);
                    } else Utils.failTrade(plr);
                }
                break;
            case "Bank":
                e.setCancelled(true);
                if (item.equalsIgnoreCase(Utils.chat("&aDeposit Coins"))) {
                    GUIs.bankDeposit(plr, plr.getUniqueId().toString(), plugin);
                } else if (item.equalsIgnoreCase(Utils.chat("&aWithdraw Coins"))) {
                    GUIs.bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
                }
                // Bank Dep
                break;
            case "Bank Deposit":
                e.setCancelled(true);
                if (item.equalsIgnoreCase(Utils.chat("&aYour whole purse"))) {

                    if (po.getPurse() <= 0) {
                        plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                        plr.sendMessage(Utils.chat("&cYou cannot deposit this little!"));
                        return;
                    }

                    plr.sendMessage(Utils.chat("&8Depositing coins..."));

                    plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    plr.sendMessage(Utils.chat("&aYou have deposited &6" + String.format("%,.1f", po.getPurse()) + " coins&a! You now have &6" + String.format("%,.1f", po.getBank() + po.getPurse()) + " coins &ain your account!"));

                    po.setBank(po.getBank() + po.getPurse());
                    po.setPurse(0);

                    // Refreshing the Page
                    GUIs.bankDeposit(plr, plr.getUniqueId().toString(), plugin);
                } else if (item.equalsIgnoreCase(Utils.chat("&aHalf your purse"))) {

                    if (po.getPurse() <= 0) {
                        plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                        plr.sendMessage(Utils.chat("&cYou cannot deposit this little!"));
                        return;
                    }

                    plr.sendMessage(Utils.chat("&8Depositing coins..."));

                    plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    plr.sendMessage(Utils.chat("&aYou have deposited &6" + String.format("%,.1f", po.getPurse() / 2) + " coins&a! You now have &6" + String.format("%,.1f", po.getBank() + po.getPurse() / 2) + " coins &ain your account!"));

                    po.setBank(po.getBank() + po.getPurse() / 2);
                    po.setPurse(po.getPurse() / 2);

                    // Refreshing the Page
                    GUIs.bankDeposit(plr, plr.getUniqueId().toString(), plugin);
                }
                // Bank Withdrawal
                break;
            case "Bank Withdrawal":
                e.setCancelled(true);
                if (item.equalsIgnoreCase(Utils.chat("&aEverything in your account"))) {

                    if (po.getBank() <= 0) {
                        plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                        plr.sendMessage(Utils.chat("&cYou cannot withdraw this little!"));
                        return;
                    }

                    plr.sendMessage(Utils.chat("&8Withdrawing coins..."));

                    plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    plr.sendMessage(Utils.chat("&aYou have withdrawn &6" + String.format("%,.1f", po.getBank()) + " coins&a! You now have &60 coins &ain your account!"));

                    po.setPurse(po.getPurse() + po.getBank());
                    po.setBank(0);

                    // Refreshing the Page
                    GUIs.bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
                } else if (item.equalsIgnoreCase(Utils.chat("&aHalf your account"))) {

                    if (po.getBank() <= 1) {
                        plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                        plr.sendMessage(Utils.chat("&cYou cannot withdraw this little!"));
                        return;
                    }

                    plr.sendMessage(Utils.chat("&8Withdrawing coins..."));

                    plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    plr.sendMessage(Utils.chat("&aYou have withdrawn &6" + String.format("%,.1f", po.getBank() / 2) + " coins&a! You now have &6" + String.format("%,.1f", po.getBank() - po.getBank() / 2) + " coins &ain your account!"));

                    po.setPurse(po.getPurse() + po.getBank() / 2);
                    po.setBank(po.getBank() - po.getBank() / 2);

                    // Refreshing the Page
                    GUIs.bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
                } else if (item.equalsIgnoreCase(Utils.chat("&a20% of your account"))) {

                    if (po.getBank() <= 1) {
                        plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                        plr.sendMessage(Utils.chat("&cYou cannot withdraw this little!"));
                        return;
                    }

                    plr.sendMessage(Utils.chat("&8Withdrawing coins..."));

                    plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    plr.sendMessage(Utils.chat("&aYou have withdrawn &6" + String.format("%,.1f", po.getBank() * .2) + " coins&a! You now have &6" + String.format("%,.1f", po.getBank() - po.getBank() * .2) + " coins &ain your account!"));

                    po.setPurse(po.getPurse() + (int) (po.getBank() * .2));
                    po.setBank((int) (po.getBank() - (po.getBank() * .2)));

                    // Refreshing the Page
                    GUIs.bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
                }
                break;
        }
        // trade menu
        if (title.startsWith("You   ")) {
            e.setCancelled(true);

        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        // Fetching the player's data
        Player plr = (Player) e.getPlayer();
        PlayerTemplate po = PlayerManager.playerObjects.get(plr);

        // If the player is in their island or not.
        if (!plr.getWorld().getName().equals("playerislands/" + plr.getUniqueId().toString())) {
            // Do a little checcing
            List<String> containers = new ArrayList<>();
            containers.add("container.dropper");
            containers.add("container.chest");
            containers.add("container.dispenser");

            if (!po.isBlockBreak() && containers.contains(e.getInventory().getName())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDrag(InventoryDragEvent e) {
        Player plr = (Player) e.getView().getPlayer();
        String title = e.getInventory().getTitle();
        if (e.getCursor() == null) return;
        String item = e.getCursor().getItemMeta().getDisplayName();

        if (title == null || item == null)
            return;

        if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
            SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), plugin);
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&8Quiver Arrow"))) {
            SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), plugin);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        Player plr = e.getPlayer();
        String item = e.getItemDrop().getItemStack().getItemMeta().getDisplayName();
        if (item == null)
            return;
        if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
            SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), plugin);
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&8Quiver Arrow"))) {
            SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), plugin);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent e) {
        Player plr = e.getPlayer();
        PlayerTemplate po = PlayerManager.playerObjects.get(plr);
        ItemStack item = e.getItem().getItemStack();

        if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().startsWith("Coin")) {
            int coin = Integer.parseInt(item.getItemMeta().getDisplayName().split(" ")[item.getItemMeta().getDisplayName().split(" ").length - 1]);
            po.setCoins_gained(po.getCoins_gained() + item.getAmount() * coin);
            e.setCancelled(true);
            e.getItem().remove();
            plr.getInventory().remove(item);
        }

        assert item != null;
        checkPickupRarity(e, item.getItemMeta().getLore(), po.getSelectedRarity());
    }

    private void checkPickupRarity(PlayerPickupItemEvent e, List<String> lores, Constants.Rarities selected_rarity) {

        if (lores == null || lores.size() <= 0) return;

        String item_rarity;

        item_rarity = Helper.getRarity(lores);

        switch (selected_rarity) {

            // item_rarity is the item rarity
            // selected_rarity is the players rarity selection

            case CELESTIAL:
                if (item_rarity.equals("common") ||
                        item_rarity.equals("uncommon") ||
                        item_rarity.equals("rare") ||
                        item_rarity.equals("epic") ||
                        item_rarity.equals("legendary")) e.setCancelled(true);
                break;
            case LEGENDARY:
                if (item_rarity.equals("common") ||
                        item_rarity.equals("uncommon") ||
                        item_rarity.equals("rare") ||
                        item_rarity.equals("epic")) e.setCancelled(true);
                break;
            case EPIC:
                if (item_rarity.equals("common") ||
                        item_rarity.equals("uncommon") ||
                        item_rarity.equals("rare")) e.setCancelled(true);

                break;
            case RARE:
                if (item_rarity.equals("common") || item_rarity.equals("uncommon"))
                    e.setCancelled(true);
                break;
            case UNCOMMON:
                if (item_rarity.equals("common")) e.setCancelled(true);
                break;
            case COMMON:
                e.setCancelled(false);
                break;
            case NONE:
                e.setCancelled(true);
                break;
        }

    }

}
