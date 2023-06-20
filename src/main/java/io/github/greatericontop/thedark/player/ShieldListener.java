package io.github.greatericontop.thedark.player;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ShieldListener implements Listener {

    private final TheDark plugin;
    public ShieldListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player))  return;
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null)  return;

        // TODO

    }

}
