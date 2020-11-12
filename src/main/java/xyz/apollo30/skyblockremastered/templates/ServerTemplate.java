package xyz.apollo30.skyblockremastered.templates;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.utils.Utils;

@Getter
@Setter
public class ServerTemplate {

    // Database
    String prefix = Utils.chat("&6SBR &8» ");
    boolean pvp = false;
    boolean hypixelip = false;

    // Dragon
    int placedSummoningEye = 0;
    boolean isDragonFight = false;
    MobTemplate enderDragon;
    Player lastDragonHit = null;
    String dragonName = null;
    String riggedDragon = null;
}
