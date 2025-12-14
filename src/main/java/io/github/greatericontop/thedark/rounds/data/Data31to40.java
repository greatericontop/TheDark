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
import io.github.greatericontop.thedark.enemy.zombies.PiglinBrute;
import io.github.greatericontop.thedark.enemy.zombies.ZombiePiglin;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillager;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class Data31to40 {

    // Mid Game (31-40)
    // Trying to ramp difficulty up a bit, with a few extra challenging rounds
    // Cash generation much more throttled

    public static final BaseOperation[] R31 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ZombieVillager.class, 2, 1, false)
            .delaySeconds(6).addSpawnOneAtATime(ChainmailZombie.class, 10, 1, false)
            .delaySeconds(6).addSpawnOneAtATime(ChainmailZombie.class, 10, 1, false)
            .delaySeconds(6).addSpawnOneAtATime(ChainmailZombie.class, 10, 1, false)
    .getOutput();

    public static final BaseOperation[] R32 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(HelmetZombie.class, 24, 40, true) // 48s
            .delaySeconds(10).addSpawnOneAtATime(ChainmailZombie.class, 70, 1, false)
            .delaySeconds(20).addSpawnOneAtATime(ChainmailZombie.class, 70, 1, false)
    .getOutput();

    public static final BaseOperation[] R33 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
            .delaySeconds(10)
            .addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
            .delaySeconds(10)
            .addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
            .delaySeconds(10)
            .addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
            .delaySeconds(10)
            .addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R34 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ChainmailZombie.class, 20, 40, true) // 40s
            .delaySeconds(5).addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
            .delaySeconds(15).addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
            .delaySeconds(15).addSpawnOneAtATime(ZombiePiglin.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R35 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ZombiePiglin.class, 1, 1, false)
            .addSpawnOneAtATime(IronZombie.class, 35, 25, false)
    .getOutput();

    public static final BaseOperation[] R36 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 250, 1, true)
            .addSpawnOneAtATime(BasicZombie.class, 250, 1, false) // spawns 2 per tick
    .getOutput();

    public static final BaseOperation[] R37 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ZombiePiglin.class, 2, 1, false)
            .delaySeconds(8).addSpawnOneAtATime(BasicZombie.class, 20, 1, false)
            .delaySeconds(8).addSpawnOneAtATime(HelmetZombie.class, 20, 1, false)
            .delaySeconds(8).addSpawnOneAtATime(ChainmailZombie.class, 20, 1, false)
            .delaySeconds(8).addSpawnOneAtATime(IronZombie.class, 20, 1, false)
    .getOutput();

    public static final BaseOperation[] R38 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(HelmetZombie.class, 100, 1, false)
            .delaySeconds(15)
            .addSpawnOneAtATime(IronZombie.class, 90, 1, false)
            .delaySeconds(5)
            .addSpawnOneAtATime(BasicZombie.class, 20, 20, false)
    .getOutput();

    public static final BaseOperation[] R39 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 225, 4, true) // 45s
            .delaySeconds(5).addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
            .delaySeconds(5).addSpawnOneAtATime(ZombieVillager.class, 1, 1, false)
            .delaySeconds(5).addSpawnOneAtATime(ZombiePiglin.class, 1, 1, false)
            .delaySeconds(5).addSpawnOneAtATime(ZombiePiglin.class, 1, 1, false)
            .delaySeconds(10).addSpawnOneAtATime(ZombiePiglin.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R40 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(PiglinBrute.class, 1, 1, false)
    .getOutput();

}
