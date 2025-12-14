package io.github.greatericontop.thedark.player;

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
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final TheDark plugin;
    public PlayerDeathListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onPlayerDeath(PlayerDeathEvent event) {
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(event.getEntity().getUniqueId());
        if (profile != null) {
            profile.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

}
