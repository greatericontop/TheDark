package io.github.greatericontop.thedark.enemy;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class EnemyDeathListener implements Listener {
    public static final NamespacedKey NO_DROP_ITEMS = new NamespacedKey("thedark", "no_drop_items");

    private final TheDark plugin;
    public EnemyDeathListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getPersistentDataContainer().has(NO_DROP_ITEMS, PersistentDataType.INTEGER)) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }

}
