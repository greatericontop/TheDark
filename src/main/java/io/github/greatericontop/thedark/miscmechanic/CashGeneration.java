package io.github.greatericontop.thedark.miscmechanic;

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
        return 0.0;
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
