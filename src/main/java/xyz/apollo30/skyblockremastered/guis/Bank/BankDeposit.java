package xyz.apollo30.skyblockremastered.guis.Bank;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.guis.constructors.Menu;
import xyz.apollo30.skyblockremastered.guis.constructors.MenuUtility;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class BankDeposit extends Menu {

    private final Player plr;

    public BankDeposit(MenuUtility menuUtility, Player p) {
        super(menuUtility);
        plr = p;
    }

    @Override
    public String getMenuName() {
        return "Bank Deposit";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        String title = e.getInventory().getTitle();
        ItemStack item = e.getCurrentItem();

        if (title.equals("Bank Deposit")) {
            String itemName = Helper.getCustomName(item);
            PlayerObject po = PlayerManager.playerObjects.get(plr);
            if (po == null) {
                plr.sendMessage(Utils.chat("&cError when loading the player's data, please try again later."));
            } else if (itemName.equalsIgnoreCase(Utils.chat("&aYour whole purse"))) {

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
                new BankDeposit(SkyblockRemastered.getMenuUtility(plr), plr).open();
            } else if (itemName.equalsIgnoreCase(Utils.chat("&aHalf your purse"))) {

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
                new BankDeposit(SkyblockRemastered.getMenuUtility(plr), plr).open();
            }
        }
    }

    @Override
    public void setMenuItems() {
        PlayerObject po = PlayerManager.playerObjects.get(plr);
        double purse = po.getPurse();
        double bank = po.getBank();
        Inventory inv = Bukkit.createInventory(plr, 36, Utils.chat("Bank Deposit"));
        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 33, 34, 35, 36);
        GUIHelper.addItem(inv, 54, 64, 12, "&aYour whole purse", "&8Bank deposit", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount to deposit: &6" + String.format("%,.1f", purse), "", "&eClick to deposit coins!");
        GUIHelper.addItem(inv, 54, 32, 14, "&aHalf your purse", "&8Bank deposit", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount to deposit: &6" + String.format("%,.1f", purse / 2), "", "&eClick to deposit coins!");
        GUIHelper.addItem(inv, 323, 0, 1, 16, "&aSpecific amount", "&7Current balance: &6" + String.format("%,.1f", bank), "", "&eClick to deposit coins!");
        GUIHelper.addItem(inv, 166, 1, 32, "&cClose");
        plr.openInventory(inv);
    }
}
