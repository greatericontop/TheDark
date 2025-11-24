package io.github.greatericontop.thedark.enemy;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.miscmechanic.CashGeneration;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class EnemyListener implements Listener {
    public static final NamespacedKey NO_DROP_ITEMS = new NamespacedKey("thedark", "no_drop_items");

    private final TheDark plugin;
    public EnemyListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            event.getEntity().getKiller().sendMessage("entity died: "+event.getEntity().getType().name());
        }
        if (event.getEntity().getPersistentDataContainer().has(NO_DROP_ITEMS, PersistentDataType.INTEGER)) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }

    @EventHandler()
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))  return;
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null)  return;
        if (!(event.getEntity() instanceof LivingEntity target))  return;
        double effectiveDamage = Math.min(event.getFinalDamage(), target.getHealth());
        CashGeneration.rewardCoinsOnDamage(profile, effectiveDamage, plugin);
    }

}
