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
import io.github.greatericontop.thedark.enemy.zombies.ZombiePiglin;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillager;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class Data21to30 {

    // Early-Mid Game (21-30)
    // Difficulty is hard to precisely control so we'll just shoot for "fair" difficulty in general. If this ends up
    //   feeling easier/harder that can just feed into the uniqueness of the rounds.
    // Cash generation slightly throttled

    public static final BaseOperation[] R21 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(HelmetZombie.class, 8, 1, false)
            .delaySeconds(7)
            .addSpawnOneAtATime(HelmetZombie.class, 8, 1, false)
            .delaySeconds(7)
            .addSpawnOneAtATime(HelmetZombie.class, 8, 1, false)
            .delaySeconds(7)
            .addSpawnOneAtATime(HelmetZombie.class, 10, 1, false)
    .getOutput();

    public static final BaseOperation[] R22 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(IronZombie.class, 1, 1, true).addSpawnOneAtATime(ChainmailZombie.class, 4, 1, false)
            .delaySeconds(9)
            .addSpawnOneAtATime(IronZombie.class, 2, 1, true).addSpawnOneAtATime(ChainmailZombie.class, 3, 1, false)
            .delaySeconds(9)
            .addSpawnOneAtATime(IronZombie.class, 3, 1, true).addSpawnOneAtATime(ChainmailZombie.class, 2, 1, false)
            .delaySeconds(9)
            .addSpawnOneAtATime(IronZombie.class, 4, 1, true).addSpawnOneAtATime(ChainmailZombie.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R23 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(IronZombie.class, 12, 1, false)
    .getOutput();

    public static final BaseOperation[] R24 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 15, 20, false)
            .addSpawnOneAtATime(HelmetZombie.class, 15, 25, false)
            .addSpawnOneAtATime(ChainmailZombie.class, 12, 30, false)
            .addSpawnOneAtATime(IronZombie.class, 9, 40, false)
    .getOutput();

    public static final BaseOperation[] R25 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ChainmailZombie.class, 20, 25, true)
            .addSpawnOneAtATime(IronZombie.class, 10, 50, false)
    .getOutput();

    public static final BaseOperation[] R26 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(HelmetZombie.class, 25, 40, true)
            .delaySeconds(10).addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
            .delaySeconds(40).addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R27 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ZombieVillager.class, 3, 100, false)
            .delaySeconds(12)
            .addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R28 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 50, 9, true)
            .addSpawnOneAtATime(ZombieVillager.class, 3, 120, false)
    .getOutput();

    public static final BaseOperation[] R29 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
            .delaySeconds(4).addSpawnOneAtATime(IronZombie.class, 18, 1, false)
            .delaySeconds(15).addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R30 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ZombiePiglin.class, 1, 1, false)
    .getOutput();

}
