package io.github.greatericontop.thedark.rounds.data;

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

import io.github.greatericontop.thedark.enemy.zombies.IronZombie;
import io.github.greatericontop.thedark.enemy.zombies.PiglinBrute;
import io.github.greatericontop.thedark.enemy.zombies.ZombiePiglin;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillager;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillagerBaby;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class LateGame {

    public static BaseOperation[] generateRoundWithSize(int size) {
        int countIron = 0;
        int countVillager = 0;
        int countPiglin = 0;
        int countBrute = 0;
        int countBaby = 0;
        while (size > 0) {
            double choice = Math.random();
            if (choice < 0.45) { // 45%
                size -= 1;
                countIron++;
            } else if (choice < 0.75) { // 30%
                if (size < 4) {
                    size -= 1;
                    continue;
                }
                size -= 4;
                countVillager++;
            } else if (choice < 0.9) { // 15%
                if (size < 20) {
                    size -= 1;
                    continue;
                }
                size -= 20;
                countPiglin++;
            } else if (choice < 0.95) { // 5%
                if (size < 100) {
                    size -= 1;
                    continue;
                }
                size -= 100;
                countBrute++;
            } else { // 5%
                if (size < 50) {
                    size -= 1;
                    continue;
                }
                size -= 50;
                countBaby++;
            }
        }
        return new RoundOperationHelper(4 * 20)
                .addSpawnOneAtATime(IronZombie.class, countIron, 1, true)
                .addSpawnOneAtATime(ZombieVillager.class, countVillager, 1, true)
                .addSpawnOneAtATime(ZombiePiglin.class, countPiglin, 1, true)
                .addSpawnOneAtATime(PiglinBrute.class, countBrute, 1, true)
                .addSpawnOneAtATime(ZombieVillagerBaby.class, countBaby, 1, false)
        .getOutput();
    }

    public static BaseOperation[] generateRound(int num) {
        int progress = num - 51;
        double size = 120.0 + 10.0*progress + 0.45*progress*progress;
        return generateRoundWithSize((int) size);
    }



}
