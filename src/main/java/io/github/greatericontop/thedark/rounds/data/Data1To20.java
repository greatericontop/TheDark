package io.github.greatericontop.thedark.rounds.data;

import io.github.greatericontop.thedark.enemy.zombies.BasicZombie;
import io.github.greatericontop.thedark.enemy.zombies.HelmetZombie;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class Data1To20 {

    // Early Game Rounds (1-20)

    public static final BaseOperation[] R1 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 10, 40, false)
    .getOutput();

    public static final BaseOperation[] R2 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 25, 40, false)
    .getOutput();

    public static final BaseOperation[] R3 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 10, 40, false)
            .addSpawnOneAtATime(BasicZombie.class, 10, 40, true)
            .addSpawnOneAtATime(HelmetZombie.class, 10, 40, false)
    .getOutput();

    public static final BaseOperation[] R4 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 25, 40, true)
            .addSpawnOneAtATime(HelmetZombie.class, 15, 66, false)
    .getOutput();

    public static final BaseOperation[] R5 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 40, 10, false)
    .getOutput();


}
