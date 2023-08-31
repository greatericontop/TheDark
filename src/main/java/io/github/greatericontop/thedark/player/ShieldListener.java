package io.github.greatericontop.thedark.player;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class ShieldListener implements Listener {

    private final TheDark plugin;
    public ShieldListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player))  return;
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null)  return;

        if (player.isBlocking()) {
            //double blockingModifier = event.getOriginalDamage(EntityDamageEvent.DamageModifier.BLOCKING);
            event.setDamage(EntityDamageEvent.DamageModifier.BLOCKING, 0.0);
            event.setDamage(event.getDamage() * 0.6);
        }

        player.sendMessage("§fmod of armor " + event.getOriginalDamage(EntityDamageEvent.DamageModifier.ARMOR));
        player.sendMessage("§ffinalDamage " + event.getFinalDamage());

    }

}
