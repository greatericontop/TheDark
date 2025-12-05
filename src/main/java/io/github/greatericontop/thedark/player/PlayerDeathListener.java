package io.github.greatericontop.thedark.player;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final TheDark plugin;
    public PlayerDeathListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onPlayerDeath(PlayerDeathEvent event) {
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(event.getEntity().getUniqueId());
        if (profile != null) {
            profile.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

}
