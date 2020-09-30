package xyz.apollo30.skyblockremastered.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.io.IOUtils;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.tasks.LagPreventerTask;

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

    public static void checkRanking(Player plr, String uuid, String skill, int currentXP, FileConfiguration db) {

        ConfigurationSection section = db.getConfigurationSection(uuid + ".Skills." + skill);

    }

    public static String coinFormat(Double coins) {
        return String.format("%,.0f", coins);
    }

    public static String urlToBase64(String url) {

        URI actualUrl;
        try {
            actualUrl = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
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

    @SuppressWarnings({"unchecked"})
    public static ItemStack createItem(Inventory inv, String material, int amount, int invSlot, String displayName,
                                       String... loreString) {

        ItemStack item;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.getMaterial(material), amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);

        if (inv != null)
            inv.setItem(invSlot - 1, item);
        return item;
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    public static ItemStack createItemID(Inventory inv, int id, int amount, int invSlot, String displayName,
                                         String... loreString) {

        ItemStack item;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.getMaterial(id), amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);

        inv.setItem(invSlot - 1, item);
        return item;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static ItemStack createItemByte(Inventory inv, int id, int byteID, int amount, int invSlot,
                                           String displayName, String... loreString) {

        ItemStack item;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.getMaterial(id), amount, (short) byteID);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);

        item.setItemMeta(meta);

        inv.setItem(invSlot - 1, item);
        return item;
    }

    public static ItemStack createGlass(Inventory inv, String material, int byteID, int amount, int... slots) {

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

    public List recipeContains(ItemStack recipeFor) {
        Iterator<Recipe> recipes = Bukkit.getServer().recipeIterator();

        List<ItemStack> ingredients = null;

        while (recipes.hasNext()) {
            Recipe rec = recipes.next();

            if (rec != null && rec.getResult() != null
                    && rec.getResult().equals(recipeFor)) {

                if (rec instanceof ShapelessRecipe) {
                    ShapelessRecipe slRec = (ShapelessRecipe) rec;

                    ingredients = slRec.getIngredientList();
                } else if (rec instanceof ShapedRecipe) {
                    ShapedRecipe slRec = (ShapedRecipe) rec;

                    ingredients = new ArrayList<>(slRec
                            .getIngredientMap().values());
                }

                if (ingredients != null && !ingredients.isEmpty()) {
                    return ingredients;
                }
            }

            break;
        }
        return ingredients;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static ItemStack createInvisibleEnchantedItem(Inventory inv, int id, int amount, int invSlot,
                                                         String displayName, String... loreString) {

        ItemStack item;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.getMaterial(id), amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);

        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 69);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        inv.setItem(invSlot - 1, item);
        return item;
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static ItemStack createInvisibleEnchantedItemByte(Inventory inv, int id, int byteID, int amount, int invSlot,
                                                             String displayName, String... loreString) {

        ItemStack item;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.getMaterial(id), amount, (short) byteID);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        inv.setItem(invSlot - 1, item);
        return item;
    }

    @SuppressWarnings({"unchecked"})
    public static ItemStack createSkull(Inventory inv, String IGN, int amount, int invSlot, String displayName,
                                        String... loreString) {

        ItemStack skull;
        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        skull = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(IGN);
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            lore.add(Utils.chat(s));
        }

        meta.setLore(lore);
        skull.setItemMeta(meta);

        inv.setItem(invSlot - 1, skull);
        return skull;

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

        String location = "&7None";

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

    public static boolean deleteWorld(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    public static ItemStack addItem(Inventory inv, ItemStack item, int slot) {
        inv.setItem(slot - 1, item);
        return item;
    }

    public static Player failTrade(Player plr) {
        plr.sendMessage(chat("&cYou don't have the required items!"));
        plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
        return plr;
    }

    public static Player successTrade(Player plr, ItemStack need, ItemStack give, int amount) {

        for (int i = 0; i < amount; i++) {
            plr.getInventory().remove(need);
        }

        plr.getInventory().addItem(give);
        plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1F, 3F);
        return plr;
    }

    public static ItemStack getSkull(String url) {

        String value = url;
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", value));

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

    public static ItemStack getLeather(String type, Color color, int amount) {
        ItemStack piece = null;

        if (type.equalsIgnoreCase("helmet")) {
            piece = new ItemStack(Material.LEATHER_HELMET, amount);
            LeatherArmorMeta meta = (LeatherArmorMeta) piece.getItemMeta();
            meta.setColor(color);
            piece.setItemMeta(meta);
        } else if (type.equalsIgnoreCase("chestplate")) {
            piece = new ItemStack(Material.LEATHER_CHESTPLATE, amount);
            LeatherArmorMeta meta = (LeatherArmorMeta) piece.getItemMeta();
            meta.setColor(color);
            piece.setItemMeta(meta);
        } else if (type.equalsIgnoreCase("leggings")) {
            piece = new ItemStack(Material.LEATHER_LEGGINGS, amount);
            LeatherArmorMeta meta = (LeatherArmorMeta) piece.getItemMeta();
            meta.setColor(color);
            piece.setItemMeta(meta);
        } else if (type.equalsIgnoreCase("boots")) {
            piece = new ItemStack(Material.LEATHER_BOOTS, amount);
            LeatherArmorMeta meta = (LeatherArmorMeta) piece.getItemMeta();
            meta.setColor(color);
            piece.setItemMeta(meta);
        }
        return piece;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ItemStack generateMinion(String type, String block, String name, FileConfiguration db) {
        ItemStack head = null;
        List<String> lore = new ArrayList();
        ItemMeta meta = null;

        String total = String.format("%,d",
                db.isConfigurationSection(name) ? db.getConfigurationSection(name).getInt("totalresourcegenerated")
                        : 0);
        lore.add(Utils.chat("&7Place this minion and it will"));
        lore.add(Utils.chat("&7start generating and mining {block}!".replace("{block}", block)));
        lore.add(Utils.chat("&7Requires an open area to"));
        lore.add(Utils.chat("&7place {block}. Minions also work".replace("{block}", block)));
        lore.add(Utils.chat("&7when you are offline!"));
        lore.add(Utils.chat(" "));
        lore.add(Utils.chat("&7Time Between Actions: &a{time}".replace("{time}", Integer.toString(
                db.isConfigurationSection(name) ? db.getConfigurationSection(name).getInt("timeperiod") : 120))));
        lore.add(Utils.chat("&7Total Generated: &e" + total));

        if (type.equalsIgnoreCase("lapis")) {
            head = getSkull(
                    "http://textures.minecraft.net/texture/3654a0f8a85d131ac9e633776837d931f0a952dcf174c28972788fef343917ed");
            meta = head.getItemMeta();
            meta.setDisplayName(Utils.chat("&9Lapis Minion "
                    + intToRoman(db.isConfigurationSection(name) ? db.getConfigurationSection(name).getInt("level") : 1)));
            meta.setLore(lore);
        }

        head.setItemMeta(meta);
        return head;
    }

    public static void broadCast(String string) {
        for (Player plr : Bukkit.getOnlinePlayers()) {
            plr.sendMessage(chat(string));
        }
    }

    public static void damageIndicator(Entity entity, int damage, String type, SkyblockRemastered plugin) {

        if (entity == null || entity.getType() == EntityType.ARMOR_STAND) return;
        int x = new Random().nextInt(2 - -1) + -1;
        int y = (int) (new Random().nextInt((int) (4 - -.5)) + -.5);
        int z = new Random().nextInt(2 - -1) + -1;

        String color = type.equals("fire") ? "&6" + damage : type.equals("water") ? "&9" + damage : type.equals("normal") ? "&7" + damage : type.equals("wither") ? "&0" + damage : type.equals("poison") ? "&2" + damage : "&7" + damage;

        if (type.equals("crithit")) {
            color = "";
            String previous = "red";
            for (String letter : Integer.toString(damage).split("")) {
                color += previous.equals("red") ? "&f" + letter : previous.equals("white") ? "&e" + letter : previous.equals("yellow") ? "&6" + letter : previous.equals("orange") ? "&c" + letter : "&f" + letter;
                previous = previous.equals("red") ? "white" : previous.equals("white") ? "yellow" : previous.equals("yellow") ? "orange" : previous.equals("orange") ? "red" : "red";
            }
            color = "&f" + GuiUtils.getUnicode("crithit") + color + "&c" + GuiUtils.getUnicode("crithit");
        }

        ArmorStand armorStand = entity.getWorld().spawn(entity.getLocation().add(x, y, z), ArmorStand.class);
        armorStand.setGravity(false);
        armorStand.setCustomName(Utils.chat(color));
        armorStand.setCustomNameVisible(true);
        armorStand.setRemoveWhenFarAway(true);
        armorStand.setVisible(false);
        armorStand.setMarker(true);
        plugin.indicator.put(armorStand, new Date().getTime());
    }

    @SuppressWarnings("unchecked")
    public static ItemStack addLore(ItemStack item, String displayName, String... loreString) {

        @SuppressWarnings("rawtypes")
        List<String> lore = new ArrayList();

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s : loreString) {
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;

    }

    public static String convertMS(double time) {
        return "undefined";
    }

    public static double getRelativeCoord(int i) {
        double d = i;
        d = d < 0 ? d - .5 : d + .5;
        return d;
    }

    public static String getDisplayHP(int level, String mobname, int current_health, int max_health) {
        double percent = current_health / max_health;
        String color = percent < .30 ? "&c" : percent < .50 ? "&e" : "&a";
        return "&8[&7Lv" + level + "&8] &c" + mobname + " " + color + (current_health < 0 ? 0 : current_health) + "&f/&a" + max_health + "&c" + GuiUtils.getUnicode("heart");
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
        String res = "";
        for (Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
            int matches = level / entry.getValue();
            res += repeat(entry.getKey(), matches);
            level = level % entry.getValue();
        }
        return res;
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

    @SuppressWarnings("unused")
    private static List<String> addElement(List<String> list2, String element, int position) {
        list2.add(position - 1, element);
        return list2;
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
                num = 0;
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
