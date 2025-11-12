package io.github.greatericontop.thedark.rounds.data;

import io.github.greatericontop.thedark.enemy.zombies.BasicZombie;
import io.github.greatericontop.thedark.enemy.zombies.ChainmailZombie;
import io.github.greatericontop.thedark.enemy.zombies.HelmetZombie;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class Data1To10 {

    // The first 10 are intended to be intro rounds.
    // They should not be very difficult.

    public static final BaseOperation[] R1 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 10, 30, false)
    .getOutput();


}
