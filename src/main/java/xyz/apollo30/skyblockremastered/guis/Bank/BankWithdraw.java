package xyz.apollo30.skyblockremastered.guis.Bank;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.guis.constructors.Menu;
import xyz.apollo30.skyblockremastered.guis.constructors.MenuUtility;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class BankWithdraw extends Menu {

    private final Player plr;

    public BankWithdraw(MenuUtility menuUtility, Player p) {
        super(menuUtility);
        plr = p;
    }

    @Override
    public String getMenuName() {
        return "Bank Withdrawal";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        String title = e.getInventory().getTitle();
        ItemStack item = e.getCurrentItem();

        if (title.equals("Bank Withdrawal")) {
            String itemName = Helper.getCustomName(item);
            PlayerObject po = PlayerManager.playerObjects.get(plr);
            if (po == null) {
                plr.sendMessage(Utils.chat("&cError when loading the player's data, please try again later."));
            } else if (itemName.equalsIgnoreCase(Utils.chat("&aEverything in your account"))) {

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
                new BankWithdraw(SkyblockRemastered.getMenuUtility(plr), plr).open();
            } else if (itemName.equalsIgnoreCase(Utils.chat("&aHalf your account"))) {

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
                new BankWithdraw(SkyblockRemastered.getMenuUtility(plr), plr).open();
            } else if (itemName.equalsIgnoreCase(Utils.chat("&a20% of your account"))) {

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
                new BankWithdraw(SkyblockRemastered.getMenuUtility(plr), plr).open();
            } else if (itemName.equalsIgnoreCase(Utils.chat("&cClose"))) {
                plr.closeInventory();
            }
        }
    }

    @Override
    public void setMenuItems() {
        PlayerObject po = PlayerManager.playerObjects.get(plr);
        if (po == null) {
            plr.sendMessage(Utils.chat("&cError when loading the player's data, please try again later."));
            return;
        }

        double bank = po.getBank();
        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 16, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 33, 34, 35, 36);
        GUIHelper.addItem(inv, 158, 1, 11, "&aEverything in your account", "&8Bank withdrawal", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount of withdraw: &6" + String.format("%,.1f", bank), "", "&eClick to withdraw coins!");
        GUIHelper.addItem(inv, 158, 1, 13, "&aHalf your account", "&8Bank withdrawal", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount of withdraw: &6" + String.format("%,.1f", bank / 2), "", "&eClick to withdraw coins!");
        GUIHelper.addItem(inv, 158, 1, 15, "&a20% of your account", "&8Bank withdrawal", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount of withdraw: &6" + String.format("%,.1f", bank * .2), "", "&eClick to withdraw coins!");
        GUIHelper.addItem(inv, 323, 0, 1, 17, "&aSpecific amount", "&7Current balance: &6" + String.format("%,.0f", bank), "", "&eClick to deposit coins!");
        GUIHelper.addItem(inv, 166, 1, 32, "&cClose");
        plr.openInventory(inv);
    }
}
