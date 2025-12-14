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
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.guns.GunUtil;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.util.Util;

public class CashGeneration {

    public static double getCashScale(int round) {
        if (round <= 20) {
            return 1.0;
        }
        if (round <= 25) {
            return 0.7;
        }
        if (round <= 30) {
            return 0.55;
        }
        if (round <= 40) {
            return 0.35;
        }
        if (round <= 45) {
            return 0.25;
        }
        if (round <= 50) {
            return 0.2;
        }
        return 0.05;
    }

    public static void rewardCoinsOnDamage(PlayerProfile profile, double finalDamage, TheDark plugin) {
        double multiplier = 0.38;
        int round = plugin.getRoundManager().getCurrentRound();
        multiplier *= getCashScale(round);

        GunType type = GunUtil.getHeldGunType(profile.getPlayer());
        if (type == GunType.MIDAS_PISTOL) {
            double multiplier_multiplier = 1.6;
            int[] upgrades = GunUtil.getUpgradesForHeldGun(profile.getPlayer().getInventory().getItemInMainHand());
            if (upgrades[0] >= 1) {
                multiplier_multiplier = 1.9;
            }
            if (upgrades[0] >= 2) {
                multiplier_multiplier = 2.2;
            }
            if (upgrades[0] >= 4) {
                multiplier_multiplier = 3.5;
            }
            multiplier *= multiplier_multiplier;
        }

        profile.coins += Util.roundNumber(finalDamage * multiplier);
    }

}
