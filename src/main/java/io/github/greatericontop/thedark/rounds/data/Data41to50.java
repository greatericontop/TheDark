package io.github.greatericontop.thedark.rounds.data;

import io.github.greatericontop.thedark.enemy.zombies.ChainmailZombie;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillager;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.RoundOperationHelper;

public class Data41to50 {

    // Late Game (41-50)
    // Things are going to get real

    public static final BaseOperation[] R41 = new RoundOperationHelper(4 * 20)
            .addSpawnOneAtATime(ZombieVillager.class, 2, 1, false)
            .delaySeconds(6).addSpawnOneAtATime(ChainmailZombie.class, 10, 1, false)
            .delaySeconds(6).addSpawnOneAtATime(ChainmailZombie.class, 10, 1, false)
            .delaySeconds(6).addSpawnOneAtATime(ChainmailZombie.class, 10, 1, false)
    .getOutput();

    /*
     * 41 a fair quantity of normal zombies & some minis
     * 42 warmup for r47
     * 43* first babies
     * 44 a lot of normal zombies & some minis
     * 45* a few piggy brutes
     * 46 babies + piglins
     * 47* extra insanely high total hp
     * 48* high total hp + babies
     * 49* onslaught of z villagers, but not insanely bad
     * 50* final boss
     */

}
