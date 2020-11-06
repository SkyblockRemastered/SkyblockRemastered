package xyz.apollo30.skyblockremastered.events.dragonHandler;

import org.bukkit.entity.Player;

public abstract class DragLootStructure {
    String f;
    Player plr;
    Integer placedEyes = 0;
    Integer weight = 0;
    public abstract void getItem();
}