package xyz.apollo30.skyblockremastered.managers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.GUIs.GUIs;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class MobManager {

    private SkyblockRemastered plugin;

    public MobManager(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    World world = Bukkit.getWorld("hub");

    public static HashMap<LivingEntity, MobObject> mobObjects = new HashMap<>();
    public HashSet<Location> zealotSpawnPoints = new HashSet<>();

    public void createMob(LivingEntity entity, String name) {

        MobObject mo = new MobObject();

        // Custom Mobs
        if (name.equalsIgnoreCase("zombie villager")) {
            mo.setMaxHealth(120);
            mo.setLocation("Graveyard");
            mo.setDamage(24);
            mo.setLevel(1);
            mo.setName("Zombie Villager");

            Zombie zombie = (Zombie) entity;
            zombie.setVillager(true);

            zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
            zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));

            zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1, true));

        } else if (name.equalsIgnoreCase("zombie")) {
            mo.setMaxHealth(100);
            mo.setLocation("Graveyard");
            mo.setDamage(20);
            mo.setLevel(1);
            mo.setName("Zombie");
        } else if (name.equalsIgnoreCase("skeleton")) {
            mo.setMaxHealth(200);
            mo.setLocation("Graveyard");
            mo.setDamage(50);
            mo.setLevel(1);
            mo.setName("Skeleton");
        } else if (name.equalsIgnoreCase("endermite")) {
            mo.setMaxHealth(200);
            mo.setLocation("Graveyard");
            mo.setDamage(475);
            mo.setLevel(1);
            mo.setName("Endermite");
        } else if (name.equalsIgnoreCase("slime")) {

            int damage;
            int size;
            int health;
            int level;
            String type;

            if (Math.random() > .5) {
                if (Math.random() > .5) {
                    damage = 150;
                    size = 10;
                    level = 15;
                    health = 250;
                    type = "Large";
                } else {
                    damage = 100;
                    size = 5;
                    level = 10;
                    health = 150;
                    type = "Medium";
                }
            } else {
                damage = 70;
                size = 10;
                level = 1;
                health = 80;
                type = "Small";
            }

            Slime slime = (Slime) entity;
            slime.setSize(size);
            mo.setMaxHealth(health);
            mo.setDamage(damage);
            mo.setLevel(level);
            mo.setName(type + " Slime");
        } else if (name.equalsIgnoreCase("lapis zombie")) {
            mo.setMaxHealth(200);
            mo.setLocation("Lapis Quarry");
            mo.setLocation("Graveyard");
            mo.setDamage(50);
            mo.setLevel(7);
            mo.setName("Lapis Zombie");

            ItemStack chestplate = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta meta1 = (LeatherArmorMeta) chestplate.getItemMeta();
            meta1.setColor(Color.fromRGB(51, 76, 178));
            chestplate.setItemMeta(meta1);

            ItemStack leggings = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta meta2 = (LeatherArmorMeta) leggings.getItemMeta();
            meta2.setColor(Color.fromRGB(51, 76, 178));
            leggings.setItemMeta(meta2);

            ItemStack boots = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta meta3 = (LeatherArmorMeta) boots.getItemMeta();
            meta3.setColor(Color.fromRGB(51, 76, 178));
            boots.setItemMeta(meta3);

            Zombie zombie = (Zombie) entity;
            zombie.setCanPickupItems(false);
            zombie.getEquipment().setHelmet(new ItemStack(Material.STAINED_GLASS, 1, (short) 11));
            zombie.getEquipment().setChestplate(chestplate);
            zombie.getEquipment().setLeggings(leggings);
            zombie.getEquipment().setBoots(boots);

            zombie.getEquipment().setBootsDropChance(0F);
            zombie.getEquipment().setHelmetDropChance(0F);
            zombie.getEquipment().setChestplateDropChance(0F);
            zombie.getEquipment().setLeggingsDropChance(0F);

        } else if (name.equalsIgnoreCase("diamond zombie")) {
            mo.setMaxHealth(200);
            mo.setLocation("Graveyard");
            mo.setDamage(50);
            mo.setLevel(7);
            mo.setName("Diamond Zombie");

            Zombie zombie = (Zombie) entity;
            zombie.setVillager(true);

            ItemStack helmet = new ItemStack(Material.DIAMOND_BLOCK);
            helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
            chestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

            ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
            leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
            boots.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

            zombie.getEquipment().setHelmet(helmet);
            zombie.getEquipment().setChestplate(chestplate);
            zombie.getEquipment().setLeggings(leggings);
            zombie.getEquipment().setBoots(boots);

            zombie.getEquipment().setBootsDropChance(0F);
            zombie.getEquipment().setHelmetDropChance(0F);
            zombie.getEquipment().setChestplateDropChance(0F);
            zombie.getEquipment().setLeggingsDropChance(0F);
        } else if (name.equalsIgnoreCase("zealot")) {
            mo.setMaxHealth(13000);
            mo.setLocation("The End");
            mo.setXpGain(10);
            mo.setDamage(1250);
            mo.setLevel(55);
            mo.setName("Zealot");
        } else if (name.equalsIgnoreCase("special zealot")) {
            mo.setMaxHealth(2000);
            mo.setLocation("The End");
            mo.setXpGain(10);
            mo.setDamage(1250);
            mo.setLevel(55);
            mo.setName("Special Zealot");
            Enderman enderman = (Enderman) entity;
            enderman.setCarriedMaterial(new MaterialData(Material.ENDER_PORTAL_FRAME, (byte) 6));
        } else if (name.equalsIgnoreCase("crypt ghoul")) {
            mo.setMaxHealth(2000);
            mo.setLocation("Graveyard");
            mo.setXpGain(2);
            mo.setDamage(350);
            mo.setLevel(30);
            mo.setName("Crypt Ghoul");
        } else if (name.equalsIgnoreCase("golden ghoul")) {

            Zombie zombie = (Zombie) entity;

            mo.setMaxHealth(45000);
            mo.setLocation("Graveyard");
            mo.setXpGain(7);
            mo.setDamage(800);
            mo.setLevel(50);
            mo.setName("Golden Ghoul");

            ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE);
            ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS);
            ItemStack boots = new ItemStack(Material.GOLD_BOOTS);

            zombie.getEquipment().setChestplate(chestplate);
            zombie.getEquipment().setLeggings(leggings);
            zombie.getEquipment().setBoots(boots);

            zombie.getEquipment().setBootsDropChance(0F);
            zombie.getEquipment().setChestplateDropChance(0F);
            zombie.getEquipment().setLeggingsDropChance(0F);

        } else if (name.equalsIgnoreCase("blaze")) {
            if (Math.random() > .5) {
                mo.setMaxHealth(500);
                mo.setLocation("Blazing Fortress");
                mo.setXpGain(8);
                mo.setDamage(120);
                mo.setLevel(12);
                mo.setName("Blaze");
            } else {
                mo.setMaxHealth(600);
                mo.setLocation("Blazing Fortress");
                mo.setXpGain(8);
                mo.setDamage(150);
                mo.setLevel(15);
                mo.setName("Blaze");
            }
        } else if (name.equalsIgnoreCase("wither skeleton")) {
            mo.setMaxHealth(250);
            mo.setLocation("Blazing Fortress");
            mo.setXpGain(10);
            mo.setDamage(160);
            mo.setLevel(10);
            mo.setName("Wither Skeleton");
        } else if (name.equalsIgnoreCase("zombie pigman")) {
            mo.setMaxHealth(240);
            mo.setLocation("Graveyard");
            mo.setXpGain(10);
            mo.setDamage(125);
            mo.setLevel(12);
            mo.setName("Wither Skeleton");
        } else if (name.equalsIgnoreCase("wolf")) {
            mo.setMaxHealth(250);
            mo.setLocation("Ruins");
            mo.setXpGain(10);
            mo.setDamage(90);
            mo.setLevel(15);
            mo.setName("Wolf");
        } else if (name.equalsIgnoreCase("old wolf")) {
            mo.setMaxHealth(15000);
            mo.setLocation("Ruins");
            mo.setXpGain(10);
            mo.setDamage(800);
            mo.setLevel(55);
            mo.setName("Old Wolf");
        }

        // Dragon
        else if (name.equalsIgnoreCase("celestial")) {
            mo.setMaxHealth(50000000);
            mo.setLevel(69420);
            mo.setName("Celestial Dragon");
            plugin.so.setEnderDragon(mo);
        } else if (name.equalsIgnoreCase("protector")) {
            mo.setMaxHealth(9000000);
            mo.setLevel(1000);
            mo.setName("Protector Dragon");
            plugin.so.setEnderDragon(mo);
        } else if (name.equalsIgnoreCase("old")) {
            mo.setMaxHealth(15000000);
            mo.setLevel(1000);
            mo.setName("Old Dragon");
            plugin.so.setEnderDragon(mo);
        } else if (name.equalsIgnoreCase("wise")) {
            mo.setMaxHealth(9000000);
            mo.setLevel(1000);
            mo.setName("Wise Dragon");
            plugin.so.setEnderDragon(mo);
        } else if (name.equalsIgnoreCase("unstable")) {
            mo.setMaxHealth(9000000);
            mo.setLevel(1000);
            mo.setName("Unstable Dragon");
            plugin.so.setEnderDragon(mo);
        } else if (name.equalsIgnoreCase("young")) {
            mo.setMaxHealth(7500000);
            mo.setLevel(1000);
            mo.setName("Young Dragon");
            plugin.so.setEnderDragon(mo);
        } else if (name.equalsIgnoreCase("strong")) {
            mo.setMaxHealth(9000000);
            mo.setLevel(1000);
            mo.setName("Strong Dragon");
            plugin.so.setEnderDragon(mo);
        } else if (name.equalsIgnoreCase("superior")) {
            mo.setMaxHealth(12000000);
            mo.setLevel(1000);
            mo.setName("Superior Dragon");
            plugin.so.setEnderDragon(mo);
        } else if (name.equalsIgnoreCase("holy")) {
            mo.setMaxHealth(20000000);
            mo.setLevel(1000);
            mo.setName("Holy Dragon");
            plugin.so.setEnderDragon(mo);
        } else if (name.equalsIgnoreCase("shitass")) {
            mo.setMaxHealth(1000000000);
            mo.setLevel(69420);
            mo.setName("shitass");
            plugin.so.setEnderDragon(mo);
        } else {
            mo.setName(entity.getName());
            mo.setLocation("???");
            mo.setMaxHealth(100);
            // mo.setLevel((int) Math.floor(Math.random() * 100000));
            mo.setLevel(2147483647);
        }


        ArmorStand armorStand = entity.getWorld().spawn(entity.getLocation(), ArmorStand.class);
        armorStand.setGravity(false);
        armorStand.setCustomName(Utils.chat("&8[&7Lv" + mo.getLevel() + "&8] &c" + mo.getName() + " &a" + mo.getHealth() + "&f/&a" + mo.getMaxHealth() + "&c" + GUIs.getUnicode("heart")));
        armorStand.setCustomNameVisible(true);
        armorStand.setRemoveWhenFarAway(true);
        armorStand.setVisible(false);
        armorStand.setMarker(true);
        entity.setPassenger(armorStand);
        plugin.health_indicator.put(entity, armorStand);
        mobObjects.put(entity, mo);
    }

    public void initBloccCheck() {
        // The End
        // -780, 255, -399
        // -466, 0, -131

        for (int i = -780; i < -466; i++) {
            for (int j = 0; j < 255; j++) {
                for (int k = -399; k < -131; k++) {
                    Block blocc = world.getBlockAt(i, j, k);
                    if (blocc.getType() == Material.CARPET && blocc.getData() == 5) {
                        // blocc.setType(Material.AIR);
                        zealotSpawnPoints.add(blocc.getLocation());

                        // ConfigurationSection config = plugin.db.getSpawns().getConfigurationSection();
                    }
                }
            }
        }
    }

    public void initTimer() {

        plugin.db.getSpawns();

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            List<Location> list = new ArrayList<>(zealotSpawnPoints);
            for (Location loc : list) {
                Block block = world.getBlockAt(loc);
                if (Arrays.stream(block.getChunk().getEntities()).filter(e -> e.getPassenger() != null && e.getPassenger().getCustomName().contains("Zealot")).collect(Collectors.toList()).size() > 3)
                    return;

                Enderman enderman = (Enderman) Bukkit.getWorld("hub").spawnEntity(loc, EntityType.ENDERMAN);
                plugin.mobManager.createMob(enderman, "Zealot");
            }
        }, 240L, 240L);
    }
}

// Was it wrong that Akhilleus shamed Hector's body just because he killed his best friend.