package io.github.greatericontop.thedark.rounds.data;

import io.github.greatericontop.thedark.enemy.zombies.ChainmailZombie;
import io.github.greatericontop.thedark.enemy.zombies.HelmetZombie;
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

    // Hard one!
    public static final BaseOperation[] R32 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(HelmetZombie.class, 20, 40, true) // 40s
            .delaySeconds(10).addSpawnOneAtATime(ChainmailZombie.class, 50, 1, false)
            .delaySeconds(30).addSpawnOneAtATime(ChainmailZombie.class, 50, 1, false)
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
    .getOutput();

    public static final BaseOperation[] R35 = new RoundOperationHelper(4 * 20)
    .getOutput();

    public static final BaseOperation[] R36 = new RoundOperationHelper(4 * 20)
    .getOutput();

}
