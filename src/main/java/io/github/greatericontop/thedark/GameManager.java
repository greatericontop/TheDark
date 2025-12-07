package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.miscmechanic.GameDifficulty;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.rounds.RoundSpawner;
import io.github.greatericontop.thedark.rounds.data.RoundData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
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

    private int tickNum;
    private GameDifficulty difficulty;

    private final TheDark plugin;
    public GameManager(TheDark plugin) {
        this.plugin = plugin;
        this.tickNum = 0;
        this.difficulty = GameDifficulty.GOLD;
    }

    public PlayerProfile getPlayerProfile(Player player) {
        return playerProfiles.get(player.getUniqueId());
    }
    public PlayerProfile getPlayerProfile(UUID uuid) {
        return playerProfiles.get(uuid);
    }
    public GameDifficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void startNewGame() {
        Location[] locations = RoundSpawner.getSpawnableLocations(plugin);
        if (locations == null) {
            plugin.getLogger().warning("No valid spawn locations!");
            return;
        }
        locations[0].getWorld().setGameRule(GameRule.NATURAL_REGENERATION, false);
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerProfile profile = new PlayerProfile(p);
            profile.coins = 500;
            playerProfiles.put(p.getUniqueId(), profile);
            p.setGameMode(GameMode.ADVENTURE);
            p.teleport(locations[(int) (Math.random() * locations.length)]);
        }
        plugin.getRoundManager().startGame();
    }

    public void tick() {
        tickNum++;
        // rounds
        plugin.getRoundManager().tick();
        // tick players
        boolean atLeastOnePlayerAlive = false;
        for (PlayerProfile profile : playerProfiles.values()) {
            profile.getPlayer().sendActionBar(profile.getActionBar());
            profile.updateInventory();
            if (profile.getPlayer().getGameMode() != GameMode.SPECTATOR) {
                atLeastOnePlayerAlive = true;
            }
            if (tickNum % 50 == 0) {
                profile.getPlayer().setFoodLevel(20);
                double maxHealth = profile.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                if (maxHealth != difficulty.getPlayerMaxHealth()) {
                    profile.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(difficulty.getPlayerMaxHealth());
                }
                if (difficulty.hasNaturalRegeneration()) {
                    profile.getPlayer().setHealth(Math.min(profile.getPlayer().getHealth()+1.0, maxHealth));
                }

            }
        }
        if (!atLeastOnePlayerAlive) {
            gameOverLose();
            return;
        }
        if (plugin.getRoundManager().getCurrentRound() == RoundData.ROUNDS.length && !plugin.getRoundManager().victoryScreenAppeared) {
            gameOverWin();
            return;
        }
        // tick enemies & remove dead ones
        List<BaseEnemy> newActiveEnemies = new ArrayList<>(activeEnemies);
        for (BaseEnemy enemy : newActiveEnemies) {
            if (!enemy.isDead()) {
                enemy.tick(plugin);
                continue;
            }
            Player killer = enemy.getEntity().getKiller();
            if (killer == null)  continue;
            PlayerProfile profile = getPlayerProfile(killer);
            if (profile == null)  continue;

            enemy.extraDeathEvent(plugin, profile);
        }
        // (deleting afterward is easier & faster)
        activeEnemies.removeIf(BaseEnemy::isDead);
    }

    public void gameOverWin() {
        for (PlayerProfile profile : playerProfiles.values()) {
            Player p = profile.getPlayer();
            p.showTitle(Title.title(Component.text("§aVictory!"), Component.text("")));
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1.0F, 1.0F);
        }
        plugin.getRoundManager().victoryScreenAppeared = true;
    }

    public void gameOverLose() {
        for (PlayerProfile profile : playerProfiles.values()) {
            Player p = profile.getPlayer();
            p.showTitle(Title.title(Component.text("§cGame Over!"), Component.text("§7You survived until round " + plugin.getRoundManager().getCurrentRound())));
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1.0F, 1.0F);
            p.setGameMode(GameMode.SPECTATOR);
        }
        playerProfiles.clear();
        plugin.getRoundManager().reset();
        activeEnemies.clear();
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
