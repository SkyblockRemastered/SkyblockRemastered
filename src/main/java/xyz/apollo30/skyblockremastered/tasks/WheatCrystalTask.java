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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.*;

public class WheatCrystalTask extends BukkitRunnable {

    private final SkyblockRemastered plugin;
    private int currentTickRotCount = 0;

    HashMap<UUID, ArrayList<Block>> farmLandAreas = new HashMap<>();
    HashMap<UUID, Integer> ranges = new HashMap<>();
    HashMap<Block, Integer> blocks = new HashMap<>();
    HashMap<UUID, Boolean> motion = new HashMap<>();

    public WheatCrystalTask(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    private final HashMap<UUID, ArrayList> stands = new HashMap<>();
    //ArmorStand, Location
    private void setupSubTasks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<UUID, ArrayList> stand : stands.entrySet()) {
                    ArmorStand armorStand = (ArmorStand) stand.getValue().get(0);

                    spawnArialParticles(armorStand.getLocation());
                }
            }
        }.runTaskTimer(this.plugin, 0L, 10L);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<UUID, ArrayList> stand : stands.entrySet()) {
                    ArmorStand armorStand = (ArmorStand) stand.getValue().get(0);

                    if (farmLandAreas.get(stand.getKey()) == null) farmLandAvailable(stand.getKey(), armorStand.getLocation(), ranges.get(stand.getKey()));

                    ArrayList<Block> blocks = farmNeededUpdate(stand.getKey());
                    if (blocks.size() > 0) {
                        Block selectedBlock = blocks.get((int) Math.floor(Math.random() * blocks.size()));
                        plantWheat(armorStand.getEyeLocation(), selectedBlock.getLocation());
                    }
                }
            }
        }.runTaskTimer(this.plugin, 0L, 20L);
    }

    private void setupOrbs() {
        blocks.put(this.plugin.getServer().getWorld("hub").getBlockAt(28, 75, -141), 13);
        blocks.put(this.plugin.getServer().getWorld("hub").getBlockAt(44, 75, -123), 10);
        blocks.put(this.plugin.getServer().getWorld("hub").getBlockAt(63, 76, -134), 10);
        blocks.put(this.plugin.getServer().getWorld("hub").getBlockAt(74, 77, -161), 10);
        blocks.put(this.plugin.getServer().getWorld("hub").getBlockAt(59, 78, -183), 10);
        blocks.put(this.plugin.getServer().getWorld("hub").getBlockAt(24, 77, -178), 10);

        for (Map.Entry<Block, Integer> block : blocks.entrySet()) {
            Location location = block.getKey().getLocation();
            ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

            armorStand.setGravity(true);
            armorStand.setVisible(false);
            armorStand.setSmall(true);

            ItemStack skull = Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJjYjQ2ODM2Y2NjY2JhYjQyNGRhZDEzNTU3ZjIwOTYyM2Y5ZDZlYzVjNjY1ZjdiZTc2OWJlYmVjNGMxNTdmYSJ9fX0");

            armorStand.setHelmet(skull);

            UUID uuid = UUID.randomUUID();

            ArrayList<Object> list = new ArrayList<>();

            list.add(armorStand);
            list.add(armorStand.getLocation().getBlockY());

            stands.put(uuid, list);
            ranges.put(uuid, block.getValue());
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

        for (Map.Entry<UUID, ArrayList> entry : stands.entrySet()) {
            int originalLoc = (int) entry.getValue().get(1);

            ArmorStand stand = (ArmorStand) entry.getValue().get(0);

            motion.putIfAbsent(entry.getKey(), false);
            boolean motionUpDown = motion.get(entry.getKey());

            if (!motionUpDown) {
                stand.setVelocity(new Vector(0, .05, 0));

                if ((stand.getLocation().getY() - originalLoc) >= 1) {
                    motion.put(entry.getKey(), true);
                }
            } else {
                stand.setVelocity(new Vector(0, -.05, 0));

                if ((stand.getLocation().getY() - originalLoc) < -0.5) {
                    motion.put(entry.getKey(), false);
                }
            }

            Location newLoc = stand.getLocation();

            currentTickRotCount = currentTickRotCount + 1;
            if (currentTickRotCount == 8) currentTickRotCount = 0;

            int yaw = currentTickRotCount * 45;
            yaw = this.normalizeYaw(yaw);

            newLoc.setYaw(yaw);
            stand.teleport(newLoc);
        }
    }
}
