package io.github.greatericontop.thedark.rounds;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.rounds.data.RoundData;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class RoundManager {
    private int currentRound;
    private int ticksUntilCurrentRoundCanEnd;

    public int getCurrentRound() {
        return currentRound;
    }

    private final TheDark plugin;
    public RoundManager(TheDark plugin) {
        this.plugin = plugin;
        this.currentRound = 0;
        this.ticksUntilCurrentRoundCanEnd = 0;
    }

    public void startGame() {
        if (currentRound != 0)  throw new RuntimeException("game should only be started while round is zero");
        startNextRound();
    }
    public void startGameAt(int round) {
        currentRound = round - 1;
        startNextRound();
    }

    public void tick() {
        if (currentRound == 0)  return;
        ticksUntilCurrentRoundCanEnd--;
        if (ticksUntilCurrentRoundCanEnd <= 0 && plugin.getGameManager().activeEnemies.isEmpty()) {
            startNextRound();
        }
    }

    private void startNextRound() {
        currentRound++;
        ticksUntilCurrentRoundCanEnd = BaseOperation.getTotalDuration(RoundData.ROUNDS[currentRound]);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showTitle(Title.title(Component.text(String.format("§cRound %d", currentRound)), Component.text("")));
            p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0F, 1.0F);
        }
        RoundSpawner.executeRound(plugin, currentRound);
    }



}
