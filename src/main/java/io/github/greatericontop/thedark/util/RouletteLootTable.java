package io.github.greatericontop.thedark.util;

import io.github.greatericontop.thedark.guns.BuyGunManager;
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RouletteLootTable {

    public static int getCost(int winKey) {
        return new int[] {
                2_000, // 0 - Flamethrower
                2_000, // 1 - Midas Pistol
                17_500, // 2 - Rocket Launcher
        }[winKey];
    }

    public static ItemStack getIcon(int winKey) {
        switch (winKey) {
            case 0 -> {
                return GunType.FLAMETHROWER.createFullyLoadedItemStack();
            }
            case 1 -> {
                return GunType.MIDAS_PISTOL.createFullyLoadedItemStack();
            }
            case 2 -> {
                return GunType.ROCKET_LAUNCHER.createFullyLoadedItemStack();
            }
            default -> {
                throw new IllegalArgumentException("invalid winKey " + winKey);
            }
        }
    }

    public static void runClaim(int winKey, PlayerProfile profile, Player player, int hotbarSlot) {
        switch (winKey) {

            case 0 -> {
                rouletteLoot_giveGun(GunType.FLAMETHROWER, winKey, profile, player, hotbarSlot);
            }

            case 1 -> {
                rouletteLoot_giveGun(GunType.MIDAS_PISTOL, winKey, profile, player, hotbarSlot);
            }

            case 2 -> {
                rouletteLoot_giveGun(GunType.ROCKET_LAUNCHER, winKey, profile, player, hotbarSlot);
            }

        }
    }

    private static void rouletteLoot_giveGun(GunType gunType, int winKey, PlayerProfile profile, Player player, int hotbarSlot) {
        if (BuyGunManager.attemptGive(gunType, player, hotbarSlot)) {
            profile.coins -= getCost(winKey);
            Util.playSuccessSound(player);
            player.closeInventory();
        } else {
            Util.playFailSound(player);
        }
    }

}
