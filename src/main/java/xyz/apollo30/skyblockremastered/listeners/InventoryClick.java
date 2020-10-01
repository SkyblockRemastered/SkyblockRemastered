package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class InventoryClick implements Listener {

    private final SkyblockRemastered plugin;

    public InventoryClick(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent e) {

        if (e.getCurrentItem() == null)
            return;
        Player plr = (Player) e.getView().getPlayer();
        String title = e.getInventory().getTitle();
        String item = e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() ? e.getCurrentItem().getItemMeta().getDisplayName() : null;
        ItemStack itemObj = e.getCurrentItem();

        if (title == null || item == null)
            return;

        // Skyblock Menu
        if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
            GuiUtils.skyblockMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers(), plugin);
            e.setCancelled(true);
        // Ender Chest
        } else if (item.equals(Utils.chat("&aEnder Chest"))) {
            plr.openInventory(plr.getEnderChest());
            plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 0.1F, 1F);
            e.setCancelled(true);
        // Crafting Table
        } else if (item.equals(Utils.chat("&aCrafting Table"))) {
            GuiUtils.craftingMenu(plr, plr.getUniqueId().toString(), "empty");
            e.setCancelled(true);
        // Trade Menu
        } else if (item.equals(Utils.chat("&aTrades"))) {
            GuiUtils.tradeMenu(plr, plr.getUniqueId().toString());
            e.setCancelled(true);
        // Close Button
        } else if (item.equals(Utils.chat("&cClose"))) {
            plr.closeInventory();
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&aYour Skills"))) {
            GuiUtils.skillMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers());
            e.setCancelled(true);
        } else if (item.equals(Utils.chat("&aGo Back"))) {
            GuiUtils.skyblockMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers(), plugin);
            e.setCancelled(true);
        }

        // Skyblock Menu
        if (title.equals("SkyBlock Menu")) {
            e.setCancelled(true);
            if (item.equals(Utils.chat("&aPlugin Developer Menu"))) {
                GuiUtils.adminPanel(plr, plr.getUniqueId().toString());
            }
        // Trade Menu
        } else if (title.equals("Trades")) {
            e.setCancelled(true);
            Inventory inv = plr.getInventory();
            if (item.equals(Utils.chat("&fCoal &8x2"))) {
                ItemStack need = new ItemStack(Material.LOG, 1, (short) 0);
                ItemStack give = new ItemStack(Material.COAL, 2);
                if (inv.containsAtLeast(need, 1))
                    Utils.successTrade(plr, need, give, 1);
                else
                    Utils.failTrade(plr);
            } else if (item.equals(Utils.chat("&fGrass &8x4"))) {
                ItemStack need = new ItemStack(Material.DIRT, 4);
                ItemStack give = new ItemStack(Material.GRASS, 4);
                if (inv.containsAtLeast(need, 4))
                    Utils.successTrade(plr, need, give, 4);
                else
                    Utils.failTrade(plr);
            }
        // Bank
        } else if (title.equals("Bank")) {
            e.setCancelled(true);
            if (item.equalsIgnoreCase(Utils.chat("&aDeposit Coins"))) {
                GuiUtils.bankDeposit(plr, plr.getUniqueId().toString(), plugin);
            } else if (item.equalsIgnoreCase(Utils.chat("&aWithdraw Coins"))) {
                GuiUtils.bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
            }
        // Bank Dep
        } else if (title.equals("Bank Deposit")) {
            e.setCancelled(true);
            if (item.equalsIgnoreCase(Utils.chat("&aYour whole purse"))) {

                PlayerObject po = plugin.playerManager.playerObjects.get(plr);

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
                GuiUtils.bankDeposit(plr, plr.getUniqueId().toString(), plugin);
            } else if (item.equalsIgnoreCase(Utils.chat("&aHalf your purse"))) {
                PlayerObject po = plugin.playerManager.playerObjects.get(plr);

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
                GuiUtils.bankDeposit(plr, plr.getUniqueId().toString(), plugin);
            }
        // Bank Withdrawal
        } else if (title.equals("Bank Withdrawal")) {
            e.setCancelled(true);
            if (item.equalsIgnoreCase(Utils.chat("&aEverything in your account"))) {

                PlayerObject po = plugin.playerManager.playerObjects.get(plr);

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
                GuiUtils.bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
            } else if (item.equalsIgnoreCase(Utils.chat("&aHalf your account"))) {
                PlayerObject po = plugin.playerManager.playerObjects.get(plr);

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
                GuiUtils.bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
            } else if (item.equalsIgnoreCase(Utils.chat("&a20% of your account"))) {
                PlayerObject po = plugin.playerManager.playerObjects.get(plr);

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
                GuiUtils.bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
            }
        }

    }

}
