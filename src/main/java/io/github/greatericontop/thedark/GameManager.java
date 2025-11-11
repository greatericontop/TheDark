package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.miscmechanic.CashGeneration;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GameManager {
    public final Map<UUID, PlayerProfile> playerProfiles = new HashMap<>();
    public final Set<BaseEnemy> activeEnemies = new HashSet<>();

    private final TheDark plugin;
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
            profile.getPlayer().sendActionBar(profile.getActionBar());
            profile.updateInventory();
        }
        // tick (dead) enemies
        List<BaseEnemy> newActiveEnemies = new ArrayList<>(activeEnemies);
        for (BaseEnemy enemy : newActiveEnemies) {
            if (!enemy.isDead())  continue;
            Player killer = enemy.getEntity().getKiller();
            if (killer == null)  continue;
            PlayerProfile profile = getPlayerProfile(killer);
            if (profile == null)  continue;

            enemy.extraDeathEvent(plugin, profile);
            CashGeneration.rewardCoinsOnDeath(profile, enemy);
        }
        // (deleting afterward is easier & faster)
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
