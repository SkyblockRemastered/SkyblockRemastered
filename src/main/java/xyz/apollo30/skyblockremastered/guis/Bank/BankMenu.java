package xyz.apollo30.skyblockremastered.guis.Bank;

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

public class BankMenu extends Menu {

    private final Player plr;

    public BankMenu(MenuUtility menuUtility, Player p) {
        super(menuUtility);
        plr = p;
    }

    @Override
    public String getMenuName() {
        return "Bank";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        String title = e.getInventory().getTitle();
        ItemStack item = e.getCurrentItem();

        if (title.equals("Bank")) {
            if (Helper.hasCustomName(item)) {
                String itemName = Helper.getCustomName(item);
                if (itemName.equalsIgnoreCase(Utils.chat("&aDeposit Coins"))) {
                    new BankDeposit(SkyblockRemastered.getMenuUtility(plr), plr).open();
                } else if (itemName.equalsIgnoreCase(Utils.chat("&aWithdraw Coins"))) {
                    new BankWithdraw(SkyblockRemastered.getMenuUtility(plr), plr).open();
                } else if (itemName.equalsIgnoreCase(Utils.chat("&cClose"))) {
                    plr.closeInventory();
                }
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
        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 34, 35, 36);
        GUIHelper.addItem(inv, 54, 1, 12, "&aDeposit Coins", "&7Current balance: &6" + String.format("%,.0f", bank), "", "&7Store coins in the bank to", "&7keep them safe while you go", "&7on adventures!", "", "&7You will earn &b2%&7 interest every", "&7season for your first &610 million", "&7banked coins.", "", "&7Until Interest: &b00h00m00s", "", "&eClick to make a deposit!");
        GUIHelper.addItem(inv, 158, 1, 14, "&aWithdraw Coins", "&7Current balance: &6" + String.format("%,.0f", bank), "", "&7Take your coins out of the", "&7bank in order to spend", "&7them.", "", "&eClick to withdraw coins!");
        GUIHelper.addItem(inv, 358, 0, 1, 16, "&aRecent transactions", "", "&7&oYou know, I am lazy to code this", "&7&oIt's kinda useless tbh if theres no co-ops.");
        GUIHelper.addItem(inv, 166, 1, 32, "&cClose");
        GUIHelper.addItem(inv, 76, 1, 33, "&aInformation", "&7Keep your coins safe in the bank!", "&7You lose half the coins in your", "&7purse when dying in combat.", "", "&7Balance limit: &650 Million", "", "&7The banker rewards you every 31", "&7hours with &binterest&7 for the", "&7coins in your bank balance", "", "&7Interest in: &b00h00m00s", "&7Last Interest: &60 coins", "&7Projected: ^60 coins &b(0%)");
        plr.openInventory(inv);
    }
}
