package xyz.apollo30.skyblockremastered.managers;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.BlockObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlockManager {

    private final SkyblockRemastered plugin;

    public BlockManager(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    List<BlockObject> blockObjects = new ArrayList<>();

    public void addBlock(Block blocc, long date) {
        BlockObject bo = new BlockObject();
        bo.setX(blocc.getX());
        bo.setY(blocc.getY());
        bo.setZ(blocc.getZ());
        bo.setMaterial(blocc.getType());
        bo.setDate(date);
        bo.setWorld(blocc.getWorld());
        bo.setByteID(blocc.getData());
        blockObjects.add(bo);
    }

    public void checkBlock() {
        try {
            for (BlockObject bo : this.blockObjects) {
                if (bo.getDate() < new Date().getTime()) {
                    Block blocc = bo.getWorld().getBlockAt(bo.getX(), bo.getY(), bo.getZ());
                    blocc.setType(bo.getMaterial());
                    blocc.setData(bo.getByteID());
                    blockObjects.remove(bo);
                }
            }
        } catch (Exception err) {

        }
    }

    public void initTimer() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::checkBlock, 1L, 1L);
    }
}
