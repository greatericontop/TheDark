package io.github.greatericontop.thedark.rounds;

/*
 * Copyright (C) 2023-present greateric.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.guns.GunUtil;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.rounds.data.LateGame;
import io.github.greatericontop.thedark.rounds.data.RoundData;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RoundManager {
    private int currentRound;
    private int ticksUntilCurrentRoundCanEnd;
    public boolean lateGameDelayPassed;
    public boolean victoryScreenAppeared;

    public int getCurrentRound() {
        return currentRound;
    }
    public boolean gameIsActive() {
        return currentRound != 0;
    }

    private final TheDark plugin;
    public RoundManager(TheDark plugin) {
        this.plugin = plugin;
        this.currentRound = 0;
        this.ticksUntilCurrentRoundCanEnd = 0;
        this.lateGameDelayPassed = false; // also used to check if game has been won
        this.victoryScreenAppeared = false;
    }

    public void startGame() {
        if (currentRound != 0)  throw new RuntimeException("game should only be started while round is zero");
        startNextRound();
    }
    public void startGameAt(int round) {
        currentRound = round - 1;
        startNextRound();
    }

    public void reset() {
        currentRound = 0;
        ticksUntilCurrentRoundCanEnd = 0;
        lateGameDelayPassed = false;
        victoryScreenAppeared = false;
    }

    public void tick() {
        if (currentRound == 0)  return;
        ticksUntilCurrentRoundCanEnd--;
        if (ticksUntilCurrentRoundCanEnd <= 0 && plugin.getGameManager().activeEnemies.isEmpty()) {
            if (currentRound == RoundData.ROUNDS.length - 1) {
                if (!lateGameDelayPassed) {
                    lateGameDelayPassed = true;
                    ticksUntilCurrentRoundCanEnd = 240;
                    return;
                }
            }
            startNextRound();
        }
    }

    private void startNextRound() {
        currentRound++;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showTitle(Title.title(Component.text(String.format("§cRound %d", currentRound)), Component.text("")));
            p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0F, 1.0F);
            if (p.getGameMode() == GameMode.SPECTATOR) {
                // Revive
                p.setGameMode(GameMode.ADVENTURE);
                Location[] locations = RoundSpawner.getSpawnableLocations(plugin);
                p.teleport(locations[(int) (Math.random() * locations.length)]);
                p.setHealth(4.0); // Fairly arbitrary small number
            }
        }
        for (PlayerProfile profile : plugin.getGameManager().playerProfiles.values()) {
            for (int slot = 1; slot <= 3; slot++) {
                ItemStack stack = profile.getPlayer().getInventory().getItem(slot);
                if (stack == null)  continue;
                GunType type = GunUtil.getHeldGunType(stack);
                if (type == null)  continue;
                int[] upgrades = GunUtil.getUpgradesForHeldGun(stack);
                switch (type) {
                    case MIDAS_PISTOL -> {
                        switch (upgrades[1]) {
                            case 1 -> profile.coins += 70;
                            case 2 -> profile.coins += 140;
                            case 3 -> {
                                profile.coins += 225;
                                int interestableAmount = Math.min(profile.coins, 2500);
                                profile.coins += Util.roundNumber(0.02 * interestableAmount);
                            }
                            case 4 -> {
                                profile.coins += 500;
                                int interestableAmount = Math.min(profile.coins, 5000);
                                profile.coins += Util.roundNumber(0.03 * interestableAmount);
                            }
                        }
                    }
                }
            }
        }

        if (currentRound >= RoundData.ROUNDS.length) {
            BaseOperation[] round = LateGame.generateRound(currentRound);
            ticksUntilCurrentRoundCanEnd = BaseOperation.getTotalDuration(round);
            RoundSpawner.executeRound(plugin, round);
        } else {
            ticksUntilCurrentRoundCanEnd = BaseOperation.getTotalDuration(RoundData.ROUNDS[currentRound]);
            RoundSpawner.executeRound(plugin, currentRound);
        }
    }



}
