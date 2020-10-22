package xyz.apollo30.skyblockremastered.utils;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NMSUtil {

    public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass) {
        try {

            List<Map<?, ?>> dataMap = new ArrayList<Map<?, ?>>();
            for (Field field : EntityTypes.class.getDeclaredFields()) {
                if (field.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    field.setAccessible(true);
                    dataMap.add((Map<?, ?>) field.get(null));
                }
            }

            if (dataMap.get(2).containsKey(id)) {
                dataMap.get(0).remove(name);
                dataMap.get(2).remove(id);
            }

            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);

        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private static NBTTagCompound getTag(org.bukkit.inventory.ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
        if (itemNms.hasTag()) tag = itemNms.getTag();
        else tag = new NBTTagCompound();
        return tag;
    }

    private static org.bukkit.inventory.ItemStack setTag(org.bukkit.inventory.ItemStack item, NBTTagCompound tag) {
        net.minecraft.server.v1_8_R3.ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        itemNms.setTag(tag);
        return CraftItemStack.asBukkitCopy(itemNms);
    }

    public static org.bukkit.inventory.ItemStack addString(org.bukkit.inventory.ItemStack item, String name, String value) {
        NBTTagCompound tag = NMSUtil.getTag(item);
        tag.setString(name, value);
        return NMSUtil.setTag(item, tag);
    }

    public static boolean hasString(org.bukkit.inventory.ItemStack item, String name) {
        NBTTagCompound tag = NMSUtil.getTag(item);
        return tag.hasKey(name);
    }

    public static String getString(org.bukkit.inventory.ItemStack item, String name) {
        NBTTagCompound tag = NMSUtil.getTag(item);
        return tag.getString(name);
    }


}
