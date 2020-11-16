package xyz.apollo30.skyblockremastered.guis.constructors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuUtility {

    private Player owner;
    private ItemStack item;

    public MenuUtility(Player plr) {
        this.owner = plr;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return this.item;
    }

}
