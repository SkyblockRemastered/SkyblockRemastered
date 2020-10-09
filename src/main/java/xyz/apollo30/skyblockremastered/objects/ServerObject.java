package xyz.apollo30.skyblockremastered.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@Getter
@Setter
public class ServerObject {

    // Dragon
    int placedSummoningEye = 0;
    boolean isDragonFight = false;
    MobObject enderDragon;
    Player lastDragonHit = null;
    String dragonName = null;
}
