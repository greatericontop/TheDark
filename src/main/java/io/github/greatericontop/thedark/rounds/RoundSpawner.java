package io.github.greatericontop.thedark.rounds;

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

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.rounds.data.RoundData;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.OperationContext;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class RoundSpawner {

    public static Location[] getSpawnableLocations(TheDark plugin) {
        List<Location> locations = new ArrayList<>();
        List validSpawnLocations = plugin.getConfig().getList("valid-spawn-locations");
        if (validSpawnLocations == null) {
            plugin.getLogger().warning(":valid-spawn-locations: is null! (can't execute round)");
            return null;
        }
        for (Object o : validSpawnLocations) {
            if (!(o instanceof List locationObject)) {
                plugin.getLogger().warning("Values in :valid-spawn-locations: must be lists! (skipping)");
                continue;
            }
            if (locationObject.size() != 4) {
                plugin.getLogger().warning("Values in :valid-spawn-locations: must have exactly 4 elements! (skipping)");
                continue;
            }
            String worldName = (String) locationObject.get(0);
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                plugin.getLogger().warning("World " + worldName + " does not exist! (skipping)");
                continue;
            }
            double x = (double) locationObject.get(1);
            double y = (double) locationObject.get(2);
            double z = (double) locationObject.get(3);
            locations.add(new Location(world, x, y, z));
        }
        return locations.toArray(new Location[0]);
    }

    public static void executeRound(OperationContext ctx, BaseOperation[] round) {
        for (BaseOperation operation : round) {
            operation.execute(ctx);
        }
    }
    public static void executeRound(TheDark plugin, int roundNumber) {
        executeRound(plugin, RoundData.ROUNDS[roundNumber]);
    }
    public static void executeRound(TheDark plugin, BaseOperation[] round) {
        Location[] locations = getSpawnableLocations(plugin);
        if (locations == null) {
            return;
        }
        OperationContext ctx = new OperationContext(plugin, locations);
        executeRound(ctx, round);
    }


}
