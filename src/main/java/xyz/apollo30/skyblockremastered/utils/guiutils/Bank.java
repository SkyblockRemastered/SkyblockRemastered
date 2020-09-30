package xyz.apollo30.skyblockremastered.utils.guiutils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import org.bukkit.event.Listener;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Bank implements Listener {
    public SkyblockRemastered plugin;

    public Bank(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static void mainMenu(Player plr, String uuid, SkyblockRemastered plugin) {

        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);

        double bank = player_data.getBank();

        Inventory inv = Bukkit.createInventory(plr, 36, Utils.chat("Bank"));

        Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 34, 35, 36);
        Utils.createItemID(inv, 54, 1, 12, "&aDeposit Coins", "&7Current balance: &6" + String.format("%,.0f", bank), "", "&7Store coins in the bank to", "&7keep them safe while you go", "&7on adventures!", "", "&7You will earn &b2%&7 interest every", "&7season for your first &610 million", "&7banked coins.", "", "&7Until Interest: &b00h00m00s", "", "&eClick to make a deposit!");
        Utils.createItemID(inv, 158, 1, 14, "&aWithdraw Coins", "&7Current balance: &6" + String.format("%,.0f", bank), "", "&7Take your coins out of the", "&7bank in order to spend", "&7them.", "", "&eClick to withdraw coins!");
        Utils.createItemByte(inv, 358, 0, 1, 16, "&aRecent transactions", "", "&7&oYou know, I am lazy to code this", "&7&oIt's kinda useless tbh if theres no co-ops.");
        Utils.createItemID(inv, 166, 1, 32, "&cClose");
        Utils.createItemID(inv, 76, 1, 33, "&aInformation", "&7Keep your coins safe in the bank!", "&7You lose half the coins in your", "&7purse when dying in combat.", "", "&7Balance limit: &650 Million", "", "&7The banker rewards you every 31", "&7hours with &binterest&7 for the", "&7coins in your bank balance", "", "&7Interest in: &b00h00m00s", "&7Last Interest: &60 coins", "&7Projected: ^60 coins &b(0%)");

        plr.openInventory(inv);
    }

    public static void bankDeposit(Player plr, String uuid, SkyblockRemastered plugin) {

        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);

        double purse = player_data.getPurse();
        double bank = player_data.getBank();

        Inventory inv = Bukkit.createInventory(plr, 36, Utils.chat("Bank Deposit"));

        Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 33, 34, 35, 36);
        Utils.createItemID(inv, 54, 64, 12, "&aYour whole purse", "&8Bank deposit", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount to deposit: &6" + String.format("%,.1f", purse), "", "&eClick to deposit coins!");
        Utils.createItemID(inv, 54, 32, 14, "&aHalf your purse", "&8Bank deposit", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount to deposit: &6" + String.format("%,.1f", purse / 2), "", "&eClick to deposit coins!");
        Utils.createItemByte(inv, 323, 0, 1, 16, "&aSpecific amount", "&7Current balance: &6" + String.format("%,.1f", bank), "", "&eClick to deposit coins!");
        Utils.createItemID(inv, 166, 1, 32, "&cClose");
        plr.openInventory(inv);

    }

    public static void bankWithdrawal(Player plr, String uuid, SkyblockRemastered plugin) {

        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);

        double bank = player_data.getBank();

        Inventory inv = Bukkit.createInventory(plr, 36, Utils.chat("Bank Withdrawal"));

        Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 16, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 33, 34, 35, 36);
        Utils.createItemID(inv, 158, 1, 11, "&aEverything in your account", "&8Bank withdrawal", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount of withdraw: &6" + String.format("%,.1f", bank), "", "&eClick to withdraw coins!");
        Utils.createItemID(inv, 158, 1, 13, "&aHalf your account", "&8Bank withdrawal", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount of withdraw: &6" + String.format("%,.1f", bank / 2), "", "&eClick to withdraw coins!");
        Utils.createItemID(inv, 158, 1, 15, "&a20% of your account", "&8Bank withdrawal", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount of withdraw: &6" + String.format("%,.1f", bank * .2), "", "&eClick to withdraw coins!");
        Utils.createItemByte(inv, 323, 0, 1, 17, "&aSpecific amount", "&7Current balance: &6" + String.format("%,.0f", bank), "", "&eClick to deposit coins!");
        Utils.createItemID(inv, 166, 1, 32, "&cClose");
        plr.openInventory(inv);

    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player plr = (Player) e.getWhoClicked();
        String item = e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() ? e.getCurrentItem().getItemMeta().getDisplayName() : null;
        String invName = e.getInventory().getTitle();
        // bank
        if (invName.equals("Bank")) {
            e.setCancelled(true);
            // dep
            if (invName.equalsIgnoreCase(Utils.chat("&aDeposit Coins"))) {
                bankDeposit(plr, plr.getUniqueId().toString(), plugin);
            } else if (item.equalsIgnoreCase(Utils.chat("&aWithdraw Coins"))) {
                bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
            }
        } else if (invName.equals("Bank Deposit")) {
            e.setCancelled(true);
            assert item != null;
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
                bankDeposit(plr, plr.getUniqueId().toString(), plugin);
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
                bankDeposit(plr, plr.getUniqueId().toString(), plugin);
            }
        } else if (invName.equals("Bank Withdrawal")) {
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
            bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
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
                bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
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
                bankWithdrawal(plr, plr.getUniqueId().toString(), plugin);
            }
        }
    }
}
