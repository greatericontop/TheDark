package io.github.greatericontop.thedark.upgrades;

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

public record ItemUpgrades(
    Upgrade top1,
    Upgrade top2,
    Upgrade top3,
    Upgrade top4,
    Upgrade bottom1,
    Upgrade bottom2,
    Upgrade bottom3,
    Upgrade bottom4
) {}
