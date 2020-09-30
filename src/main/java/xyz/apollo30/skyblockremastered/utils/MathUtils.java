package xyz.apollo30.skyblockremastered.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.util.ArrayList;

public class MathUtils {
    public SkyblockRemastered plugin;

    public MathUtils(final SkyblockRemastered plugin) {
        this.plugin = plugin;
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

    public static Location getCenter(Location loc) {
        return new Location(loc.getWorld(), getRelativeCoord1(loc.getBlockX()), getRelativeCoord1(loc.getBlockY()),
                getRelativeCoord1(loc.getBlockZ()));
    }

    private static double getRelativeCoord1(int i) {
        double d = i;
        d = d < 0 ? d - .5 : d + .5;
        return d;
    }

    public static Vector getVectorForPoints(Location l1, Location l2) {
        double g = -0.08;
        double d = l2.distance(l1);
        double t = d;
        double vX = (1.0 + 0.07 * t) * (l2.getX() - l1.getX()) / t;
        double vY = (1.0 + 0.03 * t) * (l2.getY() - l1.getY()) / t - 0.5 * g * t;
        double vZ = (1.0 + 0.07 * t) * (l2.getZ() - l1.getZ()) / t;
        return new Vector(vX, vY, vZ);
    }

}
