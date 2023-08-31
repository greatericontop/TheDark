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
            double blockingModifier = event.getOriginalDamage(EntityDamageEvent.DamageModifier.BLOCKING);
            player.sendMessage("§7[D] Blocking modifier: " + blockingModifier);
            // blockingModifier is negative 100% of the damage, so setting it to 0.4x reduces damage by 0.4x (verify?)
            event.setDamage(EntityDamageEvent.DamageModifier.BLOCKING, blockingModifier * 0.4);
            player.sendMessage("§7[D] new value " + (blockingModifier*0.4));
        }

    }

}
