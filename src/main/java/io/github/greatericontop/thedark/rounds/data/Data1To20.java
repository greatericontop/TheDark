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

import io.github.greatericontop.thedark.enemy.zombies.BasicZombie;
import io.github.greatericontop.thedark.enemy.zombies.ChainmailZombie;
import io.github.greatericontop.thedark.enemy.zombies.HelmetZombie;
import io.github.greatericontop.thedark.enemy.zombies.IronZombie;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillager;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class Data1To20 {

    // Early Game Rounds (1-20)
    // Intended to be manageable difficulty

    public static final BaseOperation[] R1 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 10, 50, false)
    .getOutput();

    public static final BaseOperation[] R2 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 20, 50, false)
    .getOutput();

    public static final BaseOperation[] R3 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 15, 45, false)
            .delaySeconds(4)
            .addSpawnOneAtATime(HelmetZombie.class, 10, 80, false)
    .getOutput();

    public static final BaseOperation[] R4 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 25, 80, true)
            .delaySeconds(6).addSpawnOneAtATime(HelmetZombie.class, 10, 120, false)
    .getOutput();

    public static final BaseOperation[] R5 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 40, 14, false)
    .getOutput();

    public static final BaseOperation[] R6 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 25, 40, true)
            .addSpawnOneAtATime(HelmetZombie.class, 15, 75, false)
    .getOutput();

    public static final BaseOperation[] R7 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 15, 40, false)
            .delaySeconds(4)
            .addSpawnOneAtATime(HelmetZombie.class, 15, 20, false)
    .getOutput();

    public static final BaseOperation[] R8 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 15, 30, false)
            .addSpawnOneAtATime(ChainmailZombie.class, 4, 100, false)
    .getOutput();

    public static final BaseOperation[] R9 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ChainmailZombie.class, 5, 80, false)
            .delaySeconds(10)
            .addSpawnOneAtATime(ChainmailZombie.class, 5, 80, false)
    .getOutput();

    public static final BaseOperation[] R10 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 10, 30, false)
            .addSpawnOneAtATime(HelmetZombie.class, 10, 30, false)
            .addSpawnOneAtATime(ChainmailZombie.class, 3, 30, false)
    .getOutput();

    // Start with $3300 on round 11

    public static final BaseOperation[] R11 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(HelmetZombie.class, 30, 25, false)
    .getOutput();

    public static final BaseOperation[] R12 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(HelmetZombie.class, 10, 1, false)
            .delaySeconds(9).addSpawnOneAtATime(HelmetZombie.class, 10, 1, false)
            .delaySeconds(9).addSpawnOneAtATime(HelmetZombie.class, 10, 1, false)
    .getOutput();

    public static final BaseOperation[] R13 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ChainmailZombie.class, 6, 1, false)
            .delaySeconds(6).addSpawnOneAtATime(BasicZombie.class, 15, 25, false)
            .addSpawnOneAtATime(HelmetZombie.class, 15, 25, false)
    .getOutput();

    public static final BaseOperation[] R14 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ChainmailZombie.class, 8, 1, false)
            .delaySeconds(16).addSpawnOneAtATime(ChainmailZombie.class, 8, 1, false)
            .delaySeconds(16).addSpawnOneAtATime(ChainmailZombie.class, 8, 1, false)
    .getOutput();

    public static final BaseOperation[] R15 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 50, 10, true)
            .addSpawnOneAtATime(ChainmailZombie.class, 4, 120, false)
    .getOutput();

    public static final BaseOperation[] R16 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(HelmetZombie.class, 6, 10, false)
            .delaySeconds(5).addSpawnOneAtATime(ChainmailZombie.class, 6, 10, false)
            .delaySeconds(10).addSpawnOneAtATime(IronZombie.class, 3, 10, false)
    .getOutput();

    public static final BaseOperation[] R17 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(IronZombie.class, 1, 1, true)
            .addSpawnOneAtATime(ChainmailZombie.class, 8, 10, false)
            .delaySeconds(15)
            .addSpawnOneAtATime(IronZombie.class, 1, 1, true)
            .addSpawnOneAtATime(ChainmailZombie.class, 8, 10, false)
    .getOutput();

    public static final BaseOperation[] R18 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(IronZombie.class, 5, 1, false)
            .delaySeconds(10).addSpawnOneAtATime(ChainmailZombie.class, 10, 80, false)
    .getOutput();

    public static final BaseOperation[] R19 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(IronZombie.class, 2, 1, false)
            .delaySeconds(7).addSpawnOneAtATime(HelmetZombie.class, 30, 30, false)
            .delaySeconds(3).addSpawnOneAtATime(IronZombie.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R20 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
    .getOutput();

}
