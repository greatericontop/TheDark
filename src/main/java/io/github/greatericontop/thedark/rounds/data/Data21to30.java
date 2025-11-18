package io.github.greatericontop.thedark.rounds.data;

import io.github.greatericontop.thedark.enemy.zombies.ChainmailZombie;
import io.github.greatericontop.thedark.enemy.zombies.IronZombie;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillager;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class Data21to30 {

    // Early-Mid Game (21-30)
    // Intended to be about as challenging as 1-20, maybe slightly easier
    // Cash generation slightly throttled (87.5% through R25, 75% through R30)

    public static final BaseOperation[] R21 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(IronZombie.class, 1, 1, true).addSpawnOneAtATime(ChainmailZombie.class, 4, 1, false)
            .delaySeconds(8)
            .addSpawnOneAtATime(IronZombie.class, 2, 1, true).addSpawnOneAtATime(ChainmailZombie.class, 3, 1, false)
            .delaySeconds(8)
            .addSpawnOneAtATime(IronZombie.class, 3, 1, true).addSpawnOneAtATime(ChainmailZombie.class, 2, 1, false)
            .delaySeconds(8)
            .addSpawnOneAtATime(IronZombie.class, 4, 1, true).addSpawnOneAtATime(ChainmailZombie.class, 1, 1, false)
    .getOutput();

    public static final BaseOperation[] R22 = new RoundOperationHelper(4 * 20)
            .
    .getOutput();

    // zombie villager again at 26

}
