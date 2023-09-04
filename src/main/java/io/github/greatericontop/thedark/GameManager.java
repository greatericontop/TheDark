package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.enemy.other.EmeraldVindicator;
import io.github.greatericontop.thedark.guns.GunClassification;
import io.github.greatericontop.thedark.guns.GunUtil;
import io.github.greatericontop.thedark.player.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
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
            profile.getPlayer().sendActionBar(profile.getActionBar());
            profile.updateInventory();
        }
        // tick enemies
        for (BaseEnemy enemy : activeEnemies) {
            if (!enemy.isDead())  continue;
            Player killer = enemy.getEntity().getKiller();
            if (killer == null)  continue;
            PlayerProfile profile = getPlayerProfile(killer);
            if (profile == null)  continue;

            int coins = enemy.coinsToAwardOnDeath();
            if (GunUtil.getHeldGunClassification(killer) == GunClassification.MIDAS_PISTOL) {
                coins *= 3;
            }
            profile.coins += coins;
            killer.sendMessage(Component.text(String.format("§6+%d coins (kill)", coins)));

            if (enemy.getClass() == EmeraldVindicator.class) {
                profile.emeralds += 1;
                killer.sendMessage(Component.text("§2+1 Emerald"));
                killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
            }
        }
        // remove dead enemies AFTER to avoid junk with iterators & ConcurrentModificationException
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
