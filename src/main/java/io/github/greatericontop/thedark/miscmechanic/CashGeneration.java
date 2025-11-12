package io.github.greatericontop.thedark.miscmechanic;

import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.util.Util;

public class CashGeneration {

    public static void rewardCoinsOnDamage(PlayerProfile profile, double finalDamage) {
        // In the future, the multiplier may be dependent on which zombie is being damaged
        double multiplier = 0.38;
        profile.coins += Util.roundNumber(finalDamage * multiplier);
    }

}
