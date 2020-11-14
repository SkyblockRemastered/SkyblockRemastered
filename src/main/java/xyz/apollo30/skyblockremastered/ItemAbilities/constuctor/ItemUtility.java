package xyz.apollo30.skyblockremastered.ItemAbilities.constuctor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ItemUtility {

    private Player creator;
    private ItemStack item;
    private UUID itemUUID;

    public ItemUtility(Player plr, ItemStack item) {
        this.creator = plr;
        this.item = item;
    }

    public Player getCreator() {
        return creator;
    }

    public void setCreator(Player creator) {
        this.creator = creator;
    }


    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return this.item;
    }

}
