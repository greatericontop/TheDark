package io.github.greatericontop.thedark.miscmechanic;

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
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavestateManager {

    private final Map<Integer, List<Savestate>> savestates;

    public SavestateManager() {
        savestates = new HashMap<>();
    }

    public void clear() {
        savestates.clear();
    }

    public void createSavestates(int round, TheDark plugin) {
        List<Savestate> savestateList = new ArrayList<>();
        for (PlayerProfile profile : plugin.getGameManager().playerProfiles.values()) {
            savestateList.add(new Savestate(
                profile.getPlayer().getUniqueId(),
                profile.getPlayer(),
                profile.getPlayer().getHealth(),
                profile.coins,
                profile.armorLevel,
                profile.getPlayer().getInventory().getItem(1),
                profile.getPlayer().getInventory().getItem(2),
                profile.getPlayer().getInventory().getItem(3)
            ));
        }
        savestates.put(round, savestateList);
    }

    public boolean hasSavestate(int round) {
        return savestates.containsKey(round);
    }

    public void restoreSavestate(int round, TheDark plugin) {
        plugin.getGameManager().playerProfiles.clear();
        for (Savestate savestate : savestates.get(round)) {
            Player player = Bukkit.getPlayer(savestate.playerUUID());
            if (player == null) {
                // Can reuse the old object temporarily if they are offline
                // When they reconnect, the join listener will refresh the player object
                player = savestate.cachedPlayer();
            }
            PlayerProfile profile = new PlayerProfile(player, true);
            profile.coins = savestate.coins();
            profile.armorLevel = savestate.armorLevel();
            player.setHealth(Math.min(
                    savestate.health(), player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
            player.getInventory().setItem(1, savestate.gun1());
            player.getInventory().setItem(2, savestate.gun2());
            player.getInventory().setItem(3, savestate.gun3());
            plugin.getGameManager().playerProfiles.put(player.getUniqueId(), profile);
        }
        plugin.getGameManager().restoreSavestateGame(round);
    }

}
