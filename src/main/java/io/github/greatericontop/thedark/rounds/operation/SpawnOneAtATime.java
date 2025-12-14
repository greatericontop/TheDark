package io.github.greatericontop.thedark.rounds.operation;

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

    public Class<? extends BaseEnemy> getEnemyClass() {
        return enemyClass;
    }
    public int getCount() {
        return count;
    }

    @Override
    public void actuallyExecute(OperationContext ctx) {
        Bukkit.getScheduler().runTaskLater(ctx.plugin(), () -> spawnOne(ctx, count), 1L);
    }

    @Override
    public int getCompletionTime() {
        // The tick after the last zombie spawns
        return offset + spacing*(count-1) + 2;
    }

    private void spawnOne(OperationContext ctx, int numberRemaining) {
        if (numberRemaining <= 0) {
            return;
        }
        if (!ctx.plugin().getRoundManager().gameIsActive()) {
            // Don't spawn more if the game has ended (e.g. everyone died)
            return;
        }
        Location[] locations = ctx.locations();
        ctx.plugin().getGameManager().spawnEnemy(enemyClass, locations[random.nextInt(locations.length)]);
        final int numberRemainingNext = numberRemaining - 1;
        Bukkit.getScheduler().runTaskLater(ctx.plugin(), () -> spawnOne(ctx, numberRemainingNext), spacing);
    }

}
