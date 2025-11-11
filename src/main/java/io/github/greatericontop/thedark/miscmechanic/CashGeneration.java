package io.github.greatericontop.thedark.miscmechanic;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.util.Util;
import net.kyori.adventure.text.Component;

public class CashGeneration {

    public static void rewardCoinsOnDamage(PlayerProfile profile, double finalDamage) {
        double multiplier = 0.8;
        profile.coins += Util.roundNumber(finalDamage * multiplier);
    }

    public static void rewardCoinsOnDeath(PlayerProfile profile, BaseEnemy enemy) {
        int coins = enemy.coinsToAwardOnDeath();
        profile.coins += coins;
        profile.getPlayer().sendMessage(Component.text(String.format("§6+%d coins (kill)", coins)));
    }

}
