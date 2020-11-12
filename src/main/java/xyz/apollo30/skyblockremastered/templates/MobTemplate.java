package xyz.apollo30.skyblockremastered.templates;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class MobTemplate {

    int health = 100;
    int maxHealth = 100;
    int defense = 0;
    int damage = 20;
    int level = 0;
    String location = "";
    String name = "";

    int xpGain = 10;

    ItemStack helmet;
    ItemStack chestplate;
    ItemStack leggings;
    ItemStack boots;
    ItemStack sword;

    public void subtractHealth(int i) {
        health -= i;
    }

    public void setMaxHealth(int i) {
        health = i;
        maxHealth = i;
    }

}
