package io.github.greatericontop.thedark.rounds.data;

import io.github.greatericontop.thedark.enemy.zombies.ChainmailZombie;
import io.github.greatericontop.thedark.enemy.zombies.IronZombie;
import io.github.greatericontop.thedark.enemy.zombies.PiglinBrute;
import io.github.greatericontop.thedark.enemy.zombies.ZombiePiglin;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillager;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillagerBaby;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class Data41to50 {

    // Late Game (41-50)
    // Things are going to get real

    public static final BaseOperation[] R41 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(IronZombie.class, 20, 1, false)
            .delaySeconds(8).addSpawnOneAtATime(IronZombie.class, 20, 1, false)
            .delaySeconds(8).addSpawnOneAtATime(ZombieVillager.class, 3, 1, false)
            .delaySeconds(8).addSpawnOneAtATime(ZombiePiglin.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R42 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ChainmailZombie.class, 80, 4, true)
            .addSpawnOneAtATime(ZombieVillager.class, 4, 1, true)
            .addSpawnOneAtATime(ZombiePiglin.class, 4, 1, false)
    .getOutput();

    public static final BaseOperation[] R43 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(IronZombie.class, 40, 8, true)
            .delaySeconds(4).addSpawnOneAtATime(ZombieVillagerBaby.class, 2, 1, false)
            .delaySeconds(8).addSpawnOneAtATime(ZombieVillagerBaby.class, 3, 1, false)
    .getOutput();

    public static final BaseOperation[] R44 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(IronZombie.class, 60, 8, true)
            .addSpawnOneAtATime(ZombieVillager.class, 12, 40, true)
    .getOutput();

    public static final BaseOperation[] R45 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(PiglinBrute.class, 3, 1, false)
    .getOutput();

    /*
     * 46 babies + piglins
     * 47* extra insanely high total hp
     * 48* high total hp + babies
     * 49* onslaught of z villagers, but not insanely bad
     * 50* final boss
     */

}
