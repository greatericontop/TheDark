package io.github.greatericontop.thedark.rounds.operation;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Random;

public class SpawnOneAtATime extends BaseOperation {

    private final int offset;
    private final Class<? extends BaseEnemy> enemyClass;
    private final int count;
    private final int spacing;

    private final Random random;

    public SpawnOneAtATime(int offsetTicks, Class<? extends BaseEnemy> enemyClass, int count, int spacing) {
        this.offset = offsetTicks;
        this.enemyClass = enemyClass;
        this.count = count;
        this.spacing = spacing;
        random = new Random();
    }

    @Override
    protected int getOffset() {
        return offset;
    }

    @Override
    public void actuallyExecute(OperationContext ctx) {
        Bukkit.getScheduler().runTaskLater(ctx.plugin(), () -> spawnOne(ctx, count), 1L);
    }

    @Override
    public int getCompletionTime() {
        // The tick after the last zombie spawns
        return offset + spacing*(count-1) + 1;
    }

    private void spawnOne(OperationContext ctx, int numberRemaining) {
        if (numberRemaining <= 0) {
            return;
        }
        Location[] locations = ctx.locations();
        ctx.plugin().getGameManager().spawnEnemy(enemyClass, locations[random.nextInt(locations.length)]);
        final int numberRemainingNext = numberRemaining - 1;
        Bukkit.getScheduler().runTaskLater(ctx.plugin(), () -> spawnOne(ctx, numberRemainingNext), spacing);
    }

}
