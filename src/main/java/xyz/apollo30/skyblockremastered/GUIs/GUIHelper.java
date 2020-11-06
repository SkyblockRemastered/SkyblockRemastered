package xyz.apollo30.skyblockremastered.GUIs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GUIHelper {

    public static ItemStack addItem(Inventory inv, ItemStack item, int slot) {
        inv.setItem(slot - 1, item);
        return item;
    }

    public static ItemStack addSkull(String url) {
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", url));

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public static ItemStack addLore(ItemStack item, String displayName, String... loreString) {
        ArrayList<String> lore = new ArrayList<>();

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            if (s.length() != 0) lore.add(Utils.chat(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @SuppressWarnings({"unchecked"})
    public static ItemStack addItem(Inventory inv, String material, int amount, int invSlot, String displayName,
                                       String... loreString) {

        ItemStack item;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.getMaterial(material), amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            if (s.length() != 0) lore.add(Utils.chat(s));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);

        if (inv != null)
            inv.setItem(invSlot - 1, item);
        return item;
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    public static ItemStack addItem(Inventory inv, int id, int amount, int invSlot, String displayName,
                                         String... loreString) {

        ItemStack item;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.getMaterial(id), amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            if (s.length() != 0) lore.add(Utils.chat(s));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);

        inv.setItem(invSlot - 1, item);
        return item;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static ItemStack addItem(Inventory inv, int id, int byteID, int amount, int invSlot,
                                           String displayName, String... loreString) {

        ItemStack item;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.getMaterial(id), amount, (short) byteID);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            if (s.length() != 0) lore.add(Utils.chat(s));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);

        inv.setItem(invSlot - 1, item);
        return item;
    }

    public static ItemStack addGlass(Inventory inv, String material, int byteID, int amount, int... slots) {

        ItemStack item;

        item = new ItemStack(Material.getMaterial(material), amount, (short) byteID);

        ItemMeta meta = item.getItemMeta();
        for (int i : slots) {
            meta.setDisplayName(Utils.chat(Integer.toString(i)));
            item.setItemMeta(meta);
            inv.setItem(i - 1, item);
        }

        return item;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static ItemStack addEnchantedItem(Inventory inv, int id, int amount, int invSlot,
                                                         String displayName, String... loreString) {

        ItemStack item;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.getMaterial(id), amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            if (s.length() != 0) lore.add(Utils.chat(s));
        }
        meta.setLore(lore);

        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 69);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        inv.setItem(invSlot - 1, item);
        return item;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static ItemStack addEnchantedItem(Inventory inv, int id, int byteID, int amount, int invSlot,
                                                             String displayName, String... loreString) {

        ItemStack item;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.getMaterial(id), amount, (short) byteID);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            if (s.length() != 0) lore.add(Utils.chat(s));
        }
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        inv.setItem(invSlot - 1, item);
        return item;
    }

    @SuppressWarnings({"unchecked"})
    public static ItemStack addSkull(Inventory inv, String IGN, int amount, int invSlot, String displayName,
                                        String... loreString) {

        ItemStack skull;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        skull = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(IGN);
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            if (s.length() != 0) lore.add(Utils.chat(s));
        }

        meta.setLore(lore);
        skull.setItemMeta(meta);

        inv.setItem(invSlot - 1, skull);
        return skull;

    }

}
