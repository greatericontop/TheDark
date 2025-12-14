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

import org.bukkit.Bukkit;

public abstract class BaseOperation {

    // Some constructor here
    // This will initialize config stuff (e.g., what enemies to spawn, how many, how long to delay)
    // Other info will be passed in to :execute: when it is called

    protected abstract int getOffset();

    public void execute(OperationContext ctx) {
        Bukkit.getScheduler().runTaskLater(ctx.plugin(), () -> actuallyExecute(ctx), getOffset());
    }

    // Note: you should be able to execute this method multiple times.
    public abstract void actuallyExecute(OperationContext ctx);

    // Returns the time in ticks when this operation will finish. (Probably offset + duration)
    // This needs to be the tick number *strictly after* the last zombie spawns, so that the last zombie spawns while
    // the round manager's tick timer is 1 on the tick it spawns.
    public abstract int getCompletionTime();


    public static int getTotalDuration(BaseOperation[] operations) {
        int max = 0;
        for (BaseOperation operation : operations) {
            int completionTime = operation.getCompletionTime();
            if (completionTime > max) {
                max = completionTime;
            }
        }
        return max;
    }

}
