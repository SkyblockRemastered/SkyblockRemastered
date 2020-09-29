package xyz.apollo30.skyblockremastered.tasks;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.util.*;

public class FarmOrb extends BukkitRunnable {

    private final SkyblockRemastered plugin;
    private boolean upDown = false;
    private int currentTickRotCount = 0;

    ArrayList<Block> farmLand = new ArrayList<>();

    HashMap<UUID, ArrayList<Block>> farmLandAreas = new HashMap<>();

    public FarmOrb(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    private ArrayList<ArmorStand> stands = new ArrayList<>();

    private void setupSubTasks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (ArmorStand stand : stands) {
                    spawnArialParticles(stand.getLocation());
                }
            }
        }.runTaskTimer(this.plugin, 0L, 5L);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (ArmorStand stand : stands) {
                    if (farmLand.size() == 0) farmLandAvailable(stand.getUniqueId(), stand.getLocation(), 100);

                    ArrayList<Block> blocks = farmNeededUpdate(stand.getUniqueId());

                    Block selectedBlock = blocks.get(0);
                    plantWheat(stand.getEyeLocation(), selectedBlock.getLocation());
                }
            }
        }.runTaskTimer(this.plugin, 0L, 20L);
    }

    private void setupOrbs() {

        List<Block> blocks = new ArrayList<>();
        blocks.add(this.plugin.getServer().getWorld("hub").getBlockAt(28, 82, -141));
        blocks.add(this.plugin.getServer().getWorld("hub").getBlockAt(44, 82, -123));
        blocks.add(this.plugin.getServer().getWorld("hub").getBlockAt(63, 82, -134));
        blocks.add(this.plugin.getServer().getWorld("hub").getBlockAt(74, 82, -161));
        blocks.add(this.plugin.getServer().getWorld("hub").getBlockAt(59, 82, -183));
        blocks.add(this.plugin.getServer().getWorld("hub").getBlockAt(24, 82, -178));

        for (Block block : blocks) {
            block.setType(Material.AIR);
            Location location = block.getLocation();
            ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

            armorStand.setGravity(false);
            armorStand.setVisible(false);
            armorStand.setSmall(true);

            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner("robloxrocks");
            skull.setItemMeta(meta);

            armorStand.setHelmet(skull);

            stands.add(armorStand);
        }

        this.setupSubTasks();
    }

    private int normalizeYaw(int yaw)
    {
        int newYaw = yaw;
        while (newYaw <= -180) newYaw += 360;
        while (newYaw > 180) newYaw -= 360;
        return newYaw;
    }

    private void spawnArialParticles(Location location) {
        Random random = new Random();
        int randomNum = random.nextInt((10 - 3) + 1) + 3;

        ArrayList<PacketPlayOutWorldParticles> particles = new ArrayList<>();

        for (int i = 0; i < randomNum; i++) {
            float randomPos = (float) (.1 + random.nextFloat() * (1.75f - .1));
            float randomPos1 = (float) (.1 + random.nextFloat() * (1.5f - .1));
            float randomPos2 = (float) (.1 + random.nextFloat() * (1.75f - .1));

            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FIREWORKS_SPARK,true, (float) location.getX() - randomPos + 1f, (float) location.getY() + randomPos1, (float) location.getZ() - randomPos2  + 1f, 0, 0, 0, 0, 1);

            particles.add(packet);
        }

        for(Player online : Bukkit.getOnlinePlayers()) {
            for (PacketPlayOutWorldParticles packetPlayOut : particles) {
                ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packetPlayOut);
            }
        }
    }

    private double getSelectedYaw(Location loc1, Location loc2) {
        double z = loc1.getZ() - loc2.getZ();
        double x = loc1.getX() - loc2.getX();

        double angleTangent = Math.toDegrees(Math.atan(z/x)) + 90;

        if ((int) x >= 0) {
            if (angleTangent < 180 && angleTangent > -180) {
                return angleTangent;
            }
        } else {
            if (angleTangent < 180 && angleTangent > -180) {
                return -179 + angleTangent;
            }
        }

        return 0;
    }

    private double getSelectedPitch(Location loc1, Location loc2) {
        double y = loc1.getY() - loc2.getY();

        double v0 = Math.pow(loc2.getX() - loc1.getX(), 2);
        double v1 = Math.pow(loc2.getZ() - loc1.getZ(), 2);
        double distanceTotal = v1 + v0;
        double linearDistance = Math.sqrt(distanceTotal);

        return Math.toDegrees(Math.atan(y/linearDistance));
    }

    private Vector getPosition(Vector origin, Vector direction, double dist) {
        return origin.clone().add(direction.clone().multiply(dist));
    }

    private ArrayList<Vector> traverse(Vector origin, Vector direction, double dist) {
        ArrayList<Vector> positions = new ArrayList<>();
        for (double d = 0; d <= dist; d += .5) {
            positions.add(this.getPosition(origin, direction, d));
        }

        return positions;
    }

    private void plantWheat(Location armorStandLoc, Location blockLoc) {
        Block block = armorStandLoc.getWorld().getBlockAt(blockLoc);
        block.setType(Material.CROPS);
        block.setData((byte) 7);

        Location newLocVec = armorStandLoc.clone();

        newLocVec.setYaw((float) this.getSelectedYaw(armorStandLoc, blockLoc.add(0.5, 0, 0.5)));
        newLocVec.setPitch((float) this.getSelectedPitch(armorStandLoc, blockLoc.add(0.5, 0, 0.5)));

        for (Vector clonedLoc : this.traverse(armorStandLoc.toVector(), newLocVec.getDirection(), 30.0)) {
            PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.FIREWORKS_SPARK,true, (float) clonedLoc.getX(), (float) clonedLoc.getY(), (float) clonedLoc.getZ(), 0, 0, 0, 0, 1);

            for (Player online : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer)online).getHandle().playerConnection.sendPacket(particle);
            }
        }
    }

    public void farmLandAvailable(UUID uuid, Location origin, int range) {
        ArrayList<Block> farmBlocks = new ArrayList<>();

        for (int x = origin.getBlockX() - range; x <= origin.getBlockX() + range; x++) {
            for (int y = origin.getBlockY() - range; y <= origin.getBlockY() + range; y++) {
                for (int z = origin.getBlockZ() - range; z <= origin.getBlockZ() + range; z++) {
                    if (origin.getWorld().getBlockAt(x, y, z).getType() == Material.SOIL) {
                        farmBlocks.add(origin.getWorld().getBlockAt(x, y, z));
                    }
                }
            }
        }

        farmLandAreas.put(uuid, farmBlocks);
    }

    private ArrayList<Block> farmNeededUpdate(UUID uuid) {
        ArrayList<Block> farm = new ArrayList<>();

        for (Block block : farmLandAreas.get(uuid)) {
            if (block.getLocation().getWorld().getBlockAt(block.getLocation().add(0, 1, 0)).getType() != Material.CROPS) {
                farm.add(block.getLocation().getWorld().getBlockAt(block.getLocation().add(0, 1, 0)));
            }
        }

        return farm;
    }

    @Override
    public void run() {
        if (stands.size() == 0) this.setupOrbs();

        for (ArmorStand stand : stands) {
            if (!upDown) {
                stand.setVelocity(new Vector(0, .05, 0));

                if (stand.getLocation().getY() > 84.6) {
                    upDown = true;
                }
            } else {
                stand.setVelocity(new Vector(0, -.05, 0));

                if (stand.getLocation().getY() < 83) {
                    upDown = false;
                }
            }

            Location newLoc = stand.getLocation();

            currentTickRotCount = currentTickRotCount + 1;
            if (currentTickRotCount == 4) currentTickRotCount = 0;

            int yaw = currentTickRotCount * 90;
            yaw = this.normalizeYaw(yaw);

            newLoc.setYaw(yaw);
            stand.teleport(newLoc);
        }
    }
}
