package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.constants.Constants;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.List;


public class PlayerPickup implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerPickup(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent e) {

        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);
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

    public void checkPickupRarity(PlayerPickupItemEvent e, List<String> lores, Constants.Rarities selected_rarity) {

        Constants.Rarities item_rarity = Constants.Rarities.NONE;

        for (String lore : lores) {
            item_rarity = lore.contains("COMMON") ? Constants.Rarities.COMMON :
                            lore.contains("UNCOMMON") ? Constants.Rarities.UNCOMMON :
                                    lore.contains("RARE") ? Constants.Rarities.RARE :
                                            lore.contains("EPIC") ? Constants.Rarities.EPIC : lore.equals("LEGENDARY") ? Constants.Rarities.LEGENDARY :
                                                    lore.contains("SPECIAL") ? Constants.Rarities.SPECIAL :
                                                            lore.contains("VERY SPECIAL") ? Constants.Rarities.VERY_SPECIAL :
                                                                    Constants.Rarities.CELESTIAL;
        }

        Utils.broadCast(item_rarity.toString());

        switch (selected_rarity) {

            // item_rarity is the item rarity
            // selected_rarity is the players rarity selection

            case CELESTIAL:
                if (item_rarity == Constants.Rarities.COMMON ||
                        item_rarity == Constants.Rarities.UNCOMMON ||
                        item_rarity == Constants.Rarities.RARE ||
                        item_rarity == Constants.Rarities.EPIC ||
                        item_rarity == Constants.Rarities.LEGENDARY) e.setCancelled(true);
                break;
            case LEGENDARY:
                if (item_rarity == Constants.Rarities.COMMON ||
                        item_rarity == Constants.Rarities.UNCOMMON ||
                        item_rarity == Constants.Rarities.RARE ||
                        item_rarity == Constants.Rarities.EPIC) e.setCancelled(true);
                break;
            case EPIC:
                if (item_rarity == Constants.Rarities.COMMON ||
                        item_rarity == Constants.Rarities.UNCOMMON ||
                        item_rarity == Constants.Rarities.RARE) e.setCancelled(true);

                break;
            case RARE:
                if (item_rarity == Constants.Rarities.COMMON || item_rarity == Constants.Rarities.UNCOMMON)
                    e.setCancelled(true);
                break;
            case UNCOMMON:
                if (item_rarity == Constants.Rarities.COMMON) e.setCancelled(true);
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
