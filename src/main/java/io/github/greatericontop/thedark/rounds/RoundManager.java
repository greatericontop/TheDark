package io.github.greatericontop.thedark.rounds;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.rounds.data.RoundData;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import org.bukkit.Bukkit;

public class RoundManager {
    private int currentRound;
    private int ticksUntilCurrentRoundCanEnd;

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

    public void tick() {
        if (currentRound == 0)  return;
        ticksUntilCurrentRoundCanEnd--;
        if (ticksUntilCurrentRoundCanEnd <= 0 && plugin.getGameManager().activeEnemies.isEmpty()) {
            startNextRound();
        }
    }

    private void startNextRound() {
        currentRound++;
        Bukkit.broadcastMessage("start round "+currentRound);
        ticksUntilCurrentRoundCanEnd = BaseOperation.getTotalDuration(RoundData.ROUNDS[currentRound]);
        RoundSpawner.executeRound(plugin, currentRound);
    }



}
