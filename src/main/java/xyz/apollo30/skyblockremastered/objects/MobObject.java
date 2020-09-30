package xyz.apollo30.skyblockremastered.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class MobObject {

    int health = 100;
    int maxHealth = 100;
    int defense = 0;
    int damage = 20;
    int level = (int) Math.floor(Math.random() * 24239485);
    String location = "";
    String name = "";

    int xpGain = 10;

    ItemStack helmet;
    ItemStack chestplate;
    ItemStack leggings;
    ItemStack boots;
    ItemStack sword;

    public int subtractHealth(int i) {
        health -= i;
        return health;
    }

    public void setMaxHealth(int i) {
        health = i;
        maxHealth = i;
    }

}
