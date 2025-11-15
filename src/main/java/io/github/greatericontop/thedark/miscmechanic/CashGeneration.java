package io.github.greatericontop.thedark.miscmechanic;

import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.guns.GunUtil;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.util.Util;

public class CashGeneration {

    public static void rewardCoinsOnDamage(PlayerProfile profile, double finalDamage) {
        // In the future, the multiplier may be dependent on which zombie is being damaged
        double multiplier = 0.38;

        GunType type = GunUtil.getHeldGunType(profile.getPlayer());
        if (type == GunType.MIDAS_PISTOL) {
            double multiplier_multiplier = 1.5;
            int[] upgrades = GunUtil.getUpgradesForHeldGun(profile.getPlayer().getInventory().getItemInMainHand());
            if (upgrades[0] >= 1) {
                multiplier_multiplier = 1.75;
            }
            if (upgrades[0] >= 2) {
                multiplier_multiplier = 2.0;
            }
            if (upgrades[0] >= 4) {
                multiplier_multiplier = 3.0;
            }
            multiplier *= multiplier_multiplier;
        }

        profile.coins += Util.roundNumber(finalDamage * multiplier);
    }

}
