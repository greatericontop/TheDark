package io.github.greatericontop.thedark;

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

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PlaceholderManager extends PlaceholderExpansion {

    private final TheDark plugin;
    public PlaceholderManager(TheDark plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "greateric";
    }

    @Override
    public String getIdentifier() {
        return "thedark";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String args) {
        if (args.startsWith("round")) {
            return String.valueOf(plugin.getRoundManager().getCurrentRound());
        }
        if (args.startsWith("enemiesleft")) {
            return String.valueOf(plugin.getGameManager().activeEnemies.size());
        }
        return null;
    }

}
