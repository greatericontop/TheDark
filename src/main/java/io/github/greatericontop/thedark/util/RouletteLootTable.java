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
        // see BuyGunManager.java - don't replace a gun if there are open spaces available
        if (BuyGunManager.getFirstSpace(player) != -1 && BuyGunManager.isPopulated(player.getInventory().getItem(hotbarSlot))) {
            player.sendMessage("§3You have available slots. You don't need to replace a gun.");
            return;
        }

        profile.coins -= getCost(winKey);
        switch (winKey) {

            case 0 -> {
                BuyGunManager.attemptGive(GunType.FLAMETHROWER, player, hotbarSlot);
            }

            case 1 -> {
                BuyGunManager.attemptGive(GunType.MIDAS_PISTOL, player, hotbarSlot);
            }

            case 2 -> {
                BuyGunManager.attemptGive(GunType.ROCKET_LAUNCHER, player, hotbarSlot);
            }

        }
        Util.playSuccessSound(player);
        player.closeInventory();
    }

}
