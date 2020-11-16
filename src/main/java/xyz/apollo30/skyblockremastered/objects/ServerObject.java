package xyz.apollo30.skyblockremastered.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.utils.Utils;

@Getter
@Setter
public class ServerObject {

    // Database
    String prefix = Utils.chat("&6SBR &8» ");
    boolean pvp = false;
    boolean hypixelip = false;

    // Dragon
    int placedSummoningEye = 0;
    boolean isDragonFight = false;
    MobObject enderDragon;
    Player lastDragonHit = null;
    String dragonName = null;
    String riggedDragon = null;
}
