package xyz.apollo30.skyblockremastered.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;

import java.util.Map;

public class Helper {

    public static String getRank(Player plr) {
        if (plr.hasPermission("groups.admin")) return "&c[ADMIN] " + plr.getName();
        else if (plr.hasPermission("groups.mod")) return "&2[MOD] " + plr.getName();
        else if (plr.hasPermission("groups.helper")) return "&9[HELPER] " + plr.getName();
        else if (plr.hasPermission("groups.builder")) return "&d[BUILDER] " + plr.getName();
        else if (plr.hasPermission("groups.tester")) return "&9[BETA] " + plr.getName();
        else if (plr.hasPermission("groups.youtuber")) return "&c[&fYOUTUBE&c] " + plr.getName();
        else if (plr.hasPermission("groups.mvp++")) return "&e»» &6[MVP&0++&6] " + plr.getName();
        else if (plr.hasPermission("groups.mvp+")) return "&b[MVP&0+&b] " + plr.getName();
        else if (plr.hasPermission("groups.mvp")) return "&b[MVP] " + plr.getName();
        else if (plr.hasPermission("groups.vip+")) return "&a[VIP&6+&a] " + plr.getName();
        else if (plr.hasPermission("groups.vip")) return "&a[VIP] " + plr.getName();
        else return "&7" + plr.getName();
    }

    public static void removeItem(Player plr, Material material) {
        for(int i = 0; i < plr.getInventory().getSize(); i++){
            ItemStack item = plr.getInventory().getItem(i);
            if(item != null && item.getType().equals(material)) {
                int amt = item.getAmount() - 1;
                item.setAmount(amt);
                plr.getInventory().setItem(i, amt > 0 ? item : null);
                plr.updateInventory();
                break;
            }
        }
    }

    public static ItemStack setLeatherColor(ItemStack leatherArmor, int r, int b, int g) {
        LeatherArmorMeta meta = (LeatherArmorMeta) leatherArmor.getItemMeta();
        meta.setColor(Color.fromRGB(r, b, g));
        leatherArmor.setItemMeta(meta);
        return leatherArmor;
    }

    public static boolean consumeItem(Player player, int count, Material mat) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found)
            return false;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed)
                player.getInventory().setItem(index, null);
            else
                stack.setAmount(stack.getAmount() - removed);

            if (count <= 0)
                break;
        }

        player.updateInventory();
        return true;
    }

}
