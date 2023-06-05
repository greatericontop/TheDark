package io.github.greatericontop.thedark.player;

import org.bukkit.entity.Player;

public class PlayerProfile {
    private Player player;
    public int coins;

    public PlayerProfile(Player player) {
        this.player = player;
        coins = 0;
    }

    public Player getPlayer() {
        return player;
    }

}
