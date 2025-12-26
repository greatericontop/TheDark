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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerRejoinListener implements Listener {

    private final TheDark plugin;
    public PlayerRejoinListener(TheDark plugin) {
        this.plugin = plugin;
    }

    // Refresh profile player objects when players rejoin
    @EventHandler()
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (plugin.getGameManager().playerProfiles.containsKey(uuid)) {
            plugin.getGameManager().playerProfiles.get(uuid).setPlayer(event.getPlayer());
            event.getPlayer().sendMessage("§3Your game profile was restored!");
        }
    }

}
