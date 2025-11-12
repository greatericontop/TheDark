package io.github.greatericontop.thedark.rounds.data;

import io.github.greatericontop.thedark.enemy.zombies.BasicZombie;
import io.github.greatericontop.thedark.enemy.zombies.ChainmailZombie;
import io.github.greatericontop.thedark.enemy.zombies.HelmetZombie;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class Data1To20 {

    // Early Game Rounds (1-20)

    public static final BaseOperation[] R1 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 10, 50, false)
    .getOutput();

    public static final BaseOperation[] R2 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 25, 50, false)
    .getOutput();

    public static final BaseOperation[] R3 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 20, 50, false)
            .delaySeconds(4)
            .addSpawnOneAtATime(HelmetZombie.class, 10, 80, false)
    .getOutput();

    public static final BaseOperation[] R4 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 25, 80, true)
            .delaySeconds(10).addSpawnOneAtATime(HelmetZombie.class, 10, 120, false)
    .getOutput();

    public static final BaseOperation[] R5 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 40, 14, false)
    .getOutput();

    public static final BaseOperation[] R6 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 25, 50, true)
            .addSpawnOneAtATime(HelmetZombie.class, 15, 80, false)
    .getOutput();

    public static final BaseOperation[] R7 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 15, 50, false)
            .delaySeconds(4)
            .addSpawnOneAtATime(HelmetZombie.class, 15, 20, false)
    .getOutput();

    public static final BaseOperation[] R8 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(BasicZombie.class, 15, 30, false)
            .addSpawnOneAtATime(ChainmailZombie.class, 4, 100, false)
    .getOutput();


}
