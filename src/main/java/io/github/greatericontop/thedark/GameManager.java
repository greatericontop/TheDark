package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.player.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GameManager {

    private final TheDark plugin;
    // TODO: for testing
    public final Map<UUID, PlayerProfile> playerProfiles = new HashMap<>();
    public final Set<BaseEnemy> activeEnemies = new HashSet<>();

    public GameManager(TheDark plugin) {
        this.plugin = plugin;
    }

    public PlayerProfile getPlayerProfile(Player player) {
        return playerProfiles.get(player.getUniqueId());
    }
    public PlayerProfile getPlayerProfile(UUID uuid) {
        return playerProfiles.get(uuid);
    }

    public void tick() {
        // tick players
        for (PlayerProfile profile : playerProfiles.values()) {
            profile.getPlayer().sendActionBar(Component.text(String.format("§6Coins: %,d", profile.coins)));
            profile.updateInventory();
        }
        // tick enemies
        for (BaseEnemy enemy : activeEnemies) {
            if (enemy.isDead()) {
                Player killer = enemy.getEntity().getKiller();
                if (killer != null) {
                    PlayerProfile profile = getPlayerProfile(killer);
                    if (profile != null) {
                        int coins = enemy.coinsToAwardOnDeath();
                        profile.coins += coins;
                        killer.sendMessage(Component.text(String.format("§6+%d coins (kill)", coins)));
                    }
                }
                activeEnemies.remove(enemy);
            }
        }
    }

    public void spawnEnemy(Class<? extends BaseEnemy> enemyClass, Location loc) {
        BaseEnemy enemy;
        try {
            enemy = enemyClass.getConstructor(Location.class).newInstance(loc);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            plugin.getLogger().severe("Reflection error (this should never happen)");
            e.printStackTrace();
            return;
        }
        activeEnemies.add(enemy);
    }

}
