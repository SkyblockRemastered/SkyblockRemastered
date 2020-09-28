package xyz.apollo30.skyblockremastered.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.World;

@Getter
@Setter
public class BlockObject {

    int x;
    int y;
    int z;

    long date;
    Material material;
    World world;
    byte byteID;

}
