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

import java.util.ArrayList;
import java.util.List;

public class RoundOperationHelper {

    private List<BaseOperation> operations;
    private int currentOffsetTicks;

    public RoundOperationHelper(int initialDelayTicks) {
        this.operations = new ArrayList<>();
        this.currentOffsetTicks = initialDelayTicks;
    }

    public BaseOperation[] getOutput() {
        return operations.toArray(new BaseOperation[0]);
    }

    //

    public RoundOperationHelper delay(int ticks) {
        currentOffsetTicks += ticks;
        return this;
    }

    public RoundOperationHelper delaySeconds(int seconds) {
        currentOffsetTicks += seconds * 20;
        return this;
    }

    // Note that :isAsync: will add the operation at the current offset without increasing the offset
    // This means that it will run at the same time as the NEXT operation, NOT the CURRENT one

    public RoundOperationHelper addSpawnOneAtATime(Class<? extends BaseEnemy> enemyClass, int count, int spacing, boolean isAsync) {
        BaseOperation operation = new SpawnOneAtATime(currentOffsetTicks, enemyClass, count, spacing);
        operations.add(operation);
        if (!isAsync) {
            currentOffsetTicks += (count - 1) * spacing + 1;
        }
        return this;
    }

}
