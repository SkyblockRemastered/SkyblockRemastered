package xyz.apollo30.skyblockremastered.utils;

import org.apache.commons.io.IOUtils;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.guis.GUIs;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class Utils {

    private static World world;

    public SkyblockRemastered plugin;

    public Utils(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String doubleFormat(Double coins) {
        return String.format("%,.0f", coins);
    }

    public static Object getValueOf(Object clazz, String lookingForValue) throws Exception {
        Field field = clazz.getClass().getField(lookingForValue);
        Class clazzType = field.getType();
        if (clazzType.toString().equals("double"))
            return field.getDouble(clazz);
        else if (clazzType.toString().equals("int"))
            return field.getInt(clazz);
        return field.get(clazz);
    }

    public static String urlToBase64(String url) {

        if (url.isEmpty()) return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmM4ZWExZjUxZjI1M2ZmNTE0MmNhMTFhZTQ1MTkzYTRhZDhjM2FiNWU5YzZlZWM4YmE3YTRmY2I3YmFjNDAifX19";
        else if (!url.startsWith("https://textures.minecraft.net/")) url = "https://textures.minecraft.net/" + url;

        URI actualUrl;
        try {
            actualUrl = new URI(url);
        } catch (URISyntaxException e) {
            return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmM4ZWExZjUxZjI1M2ZmNTE0MmNhMTFhZTQ1MTkzYTRhZDhjM2FiNWU5YzZlZWM4YmE3YTRmY2I3YmFjNDAifX19";
        }
        String toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl.toString() + "\"}}}";
        return Base64.getEncoder().encodeToString(toEncode.getBytes());
    }

    public static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    private static void copyFileStructure(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists())
                        if (!target.mkdirs())
                            throw new IOException("Couldn't create world directory!");
                    String[] files = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyFileStructure(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyWorld(World originalWorld, String newWorldName) {
        File copiedFile = new File(Bukkit.getWorldContainer(), newWorldName);
        copyFileStructure(originalWorld.getWorldFolder(), copiedFile);
        World island = new WorldCreator(newWorldName).createWorld();
        island.setSpawnLocation(0, 100, 0);
    }

    public static String getUuid(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        try {
            String UUIDJson = IOUtils.toString(new URL(url));
            if (UUIDJson.isEmpty()) return UUID.randomUUID().toString();
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            return insertDashUUID(UUIDObject.get("id").toString());
        } catch (IOException | org.json.simple.parser.ParseException e) {
            return UUID.randomUUID().toString();
        }
    }

    public static String getLocation(Player plr) {

        World world = plr.getWorld();
        Location loc = plr.getLocation();
        String location = "&7None";

        if (world.getName().equals("playerislands/" + plr.getUniqueId().toString())) location = "&aYour Island";
        else if (world.getName().startsWith("playerislands/") && !world.getName().equals("playerislands/" + plr.getUniqueId().toString())) {
            String islandOwner = Utils.getName(world.getName().replace("playerislands/", ""));
            location = "&a" + islandOwner + "'s Island";
        } else if (world.getName().equals("hub")) {
//            if (Utils.isInZone(loc, new Location(loc.getWorld(), 27, 81, -59), new Location(plr.getWorld(), 35, 69, -52))) location = "&6Bank";
//            else if (Utils.isInZone(loc, new Location(loc.getWorld(), -44, 86, -74), new Location(plr.getWorld(), -22, 65, - 67))) location = "&eBazaar Alley";
//            else if (Utils.isInZone(loc, new Location(loc.getWorld(), -41, 77, -66), new Location(plr.getWorld(), -24, 68, -55))) location = "&bAuction House";
//            else if (Utils.isInZone(loc, new Location(loc.getWorld(), 33, 78, -98), new Location(loc.getWorld(), 50, 68, -78))) location = "&dCommunity Center";
//            else if (Utils.isInZone(loc, new Location(loc.getWorld(), -38, 72, -133), new Location(plr.getWorld(), -23, 65, -121))) location = "&7Blacksmith";
//            else if (Utils.isInZone(loc, new Location(loc.getWorld(), -40, 76, -115), new Location(plr.getWorld(), -28, 63, -105))) location = "&cRedstone House";
//            else if (Utils.isInZone(loc, new Location(loc.getWorld(), 9, 104, -190), new Location(plr.getWorld(), 80, 55, -116))) location = "&6Farm";
//            else if (Utils.isInZone(loc, new Location(loc.getWorld(), -80, 255, -152), new Location(plr.getWorld(), 95, 0, -26))) location = "&bVillage";
            if (Utils.isInZone(loc, new Location(loc.getWorld(), -64, 101, -68), new Location(plr.getWorld(), 72, 1, 67)))
                location = "&5Dragon's Nest";
            else if (Utils.isInZone(loc, new Location(loc.getWorld(), -112, 255, -107), new Location(plr.getWorld(), 213, 29, 127)))
                location = "&dThe End";
        }

        return location;
    }

    public static String insertDashUUID(String uuid) {
        StringBuilder sb = new StringBuilder(uuid);
        sb.insert(8, "-");
        sb = new StringBuilder(sb.toString());
        sb.insert(13, "-");
        sb = new StringBuilder(sb.toString());
        sb.insert(18, "-");
        sb = new StringBuilder(sb.toString());
        sb.insert(23, "-");

        return sb.toString();
    }

    public static String getName(String uuid) {
        String url = "https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names";
        try {
            String nameJson = IOUtils.toString(new URL(url));
            JSONArray nameValue = (JSONArray) JSONValue.parseWithException(nameJson);
            String playerSlot = nameValue.get(nameValue.size() - 1).toString();
            JSONObject nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);
            return nameObject.get("name").toString();
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static void failTrade(Player plr) {
        plr.sendMessage(chat("&cYou don't have the required items!"));
        plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
    }

    public static void successTrade(Player plr, ItemStack item) {
        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1F, 3F);
        plr.getInventory().addItem(item);
    }

    public static void broadCast(String string) {
        for (Player plr : Bukkit.getOnlinePlayers()) {
            plr.sendMessage(chat(string));
        }
    }

    public static void damageIndicator(Entity entity, int damage, String type) {
        if (entity == null || entity.getType() == EntityType.ARMOR_STAND) return;
        int x = (int) (new Random().nextInt(2 - -1) + -Math.random() * 1);
        int y = (int) (new Random().nextInt((int) (4 - -.5)) + -Math.random() * .5);
        int z = (int) (new Random().nextInt(2 - -1) + -Math.random() * 1);
        StringBuilder color = new StringBuilder(type.equals("fire") ? "&6" + damage : type.equals("water") ? "&9" + damage : type.equals("normal") ? "&7" + damage : type.equals("wither") ? "&0" + damage : type.equals("poison") ? "&2" + damage : "&7" + damage);
        if (type.equals("crithit")) {
            color = new StringBuilder();
            String previous = "red";
            for (String letter : Integer.toString(damage).split("")) {
                color.append(previous.equals("red") ? "&f" + letter : previous.equals("white") ? "&e" + letter : previous.equals("yellow") ? "&6" + letter : "&c" + letter);
                previous = previous.equals("red") ? "white" : previous.equals("white") ? "yellow" : previous.equals("yellow") ? "orange" : "red";
            }
            color = new StringBuilder("&f" + GUIs.getUnicode("crithit") + color + "&c" + GUIs.getUnicode("crithit"));
        }

        ArmorStand armorStand = entity.getWorld().spawn(entity.getLocation().add(x, y, z), ArmorStand.class);
        armorStand.setGravity(false);
        armorStand.setCustomName(Utils.chat(color.toString()));
        armorStand.setCustomNameVisible(true);
        armorStand.setRemoveWhenFarAway(true);
        armorStand.setVisible(false);
        armorStand.setMarker(true);
        SkyblockRemastered.indicator.put(armorStand, new Date().getTime());
    }

    public static String getDisplayHP(int level, String mobname, int current_health, int max_health) {
        double percent = current_health / (double) max_health;
        String color = percent < .30 ? "&c" : percent < .50 ? "&e" : "&a";
        return "&8[&7Lv" + level + "&8] &c" + mobname + " " + color + (Math.max(current_health, 0)) + "&f/&a" + max_health + "&c" + GUIs.getUnicode("heart");
    }

    public static String intToRoman(int level) {
        LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
        roman_numerals.put("M", 1000);
        roman_numerals.put("CM", 900);
        roman_numerals.put("D", 500);
        roman_numerals.put("CD", 400);
        roman_numerals.put("C", 100);
        roman_numerals.put("XC", 90);
        roman_numerals.put("L", 50);
        roman_numerals.put("XL", 40);
        roman_numerals.put("X", 10);
        roman_numerals.put("IX", 9);
        roman_numerals.put("V", 5);
        roman_numerals.put("IV", 4);
        roman_numerals.put("I", 1);
        StringBuilder res = new StringBuilder();
        for (Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
            int matches = level / entry.getValue();
            res.append(repeat(entry.getKey(), matches));
            level = level % entry.getValue();
        }
        return res.toString();
    }

    public static int romanToInt(String s) {
        if (s == null) {
            return 0;
        }

        int length = s.length();
        int sum = 0;
        int pre = 0;

        for (int i = length - 1; i >= 0; i--) {
            int cur = romanTable(s.charAt(i));

            if (i == length - 1) {
                sum = sum + cur;
            } else {
                if (cur < pre) {
                    sum = sum - cur;
                } else {
                    sum = sum + cur;
                }
            }
            pre = cur;
        }

        return sum;
    }

    public static boolean isInZone(Location location, Location loc1, Location loc2) {
        return (location.getX() > loc1.getX() && location.getZ() > loc1.getZ()) && (location.getX() < loc2.getX() && location.getZ() < loc2.getZ());
    }

    private static List<String> addElement(List<String> list, String element, int position) {
        list.add(position - 1, element);
        return list;
    }

    public static int romanTable(char c) {
        int num = 0;
        switch (c) {
            case 'I':
                num = 1;
                break;
            case 'V':
                num = 5;
                break;
            case 'X':
                num = 10;
                break;
            case 'L':
                num = 50;
                break;
            case 'C':
                num = 100;
                break;
            case 'D':
                num = 500;
                break;
            case 'M':
                num = 1000;
                break;
            default:
                break;
        }
        return num;
    }

    public static String repeat(String s, int n) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
