package io.github.greatericontop.thedark.rounds.data;

import io.github.greatericontop.thedark.enemy.zombies.MilitantZombie;
import io.github.greatericontop.thedark.enemy.zombies.BasicZombie;
import io.github.greatericontop.thedark.enemy.other.EmeraldVindicator;
import io.github.greatericontop.thedark.enemy.zombies.StandardZombie;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class Data1To10 {

    // The first 10 are intended to be intro rounds.
    // They should not be very difficult.

    public static final BaseOperation[] R1 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 6, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 8, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 10, 40, false)
    .getOutput();

    public static final BaseOperation[] R2 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 6, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 8, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 8, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 10, 40, false)
    .getOutput();

    public static final BaseOperation[] R3 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 8, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 10, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 12, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 14, 40, false)
    .getOutput();

    public static final BaseOperation[] R4 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 10, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 12, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 14, 40, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(BasicZombie.class, 16, 40, false)
            .delaySeconds(12)
            .addSpawnOneAtATime(EmeraldVindicator.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R5 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 10, 40, true)
            .addSpawnOneAtATime(StandardZombie.class, 10, 60, false)
            .delaySeconds(6)
            .addSpawnOneAtATime(MilitantZombie.class, 8, 40, false)
    .getOutput();


}
