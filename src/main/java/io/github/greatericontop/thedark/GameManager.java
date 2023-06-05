package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.player.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameManager {

    private final TheDark plugin;
    // TODO: for testing
    public final Map<UUID, PlayerProfile> playerProfiles = new HashMap<>();
    public final List<BaseEnemy> activeEnemies = new ArrayList<>();

    public GameManager(TheDark plugin) {
        this.plugin = plugin;
    }

    public void tick() {
        // tick players
        for (PlayerProfile profile : playerProfiles.values()) {
            profile.getPlayer().sendActionBar(Component.text(String.format("§6Coins: %,d", profile.coins)));
        }
        // tick enemies
        activeEnemies.removeIf(BaseEnemy::isDead);
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
