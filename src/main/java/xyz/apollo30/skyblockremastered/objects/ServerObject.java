package xyz.apollo30.skyblockremastered.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

@Getter
@Setter
public class ServerObject {

    // Dragon
    int placedSummoningEye = 0;
    boolean isDragonFight = false;
    MobObject enderDragon;
    Player lastDragonHit = null;
    String dragonName = null;
    String riggedDragon = null;
    BukkitTask movementManager = null;
    boolean pvp = false;
}
