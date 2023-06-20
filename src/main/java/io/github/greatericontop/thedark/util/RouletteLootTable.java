package io.github.greatericontop.thedark.util;

import io.github.greatericontop.thedark.guns.BuyGunManager;
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class RouletteLootTable {
    public static int ROULETTE_REWARD_COUNT = 4;

    public static int getCost(int winKey) {
        return new int[] {
                2_000, // 0 - Flamethrower
                2_000, // 1 - Midas Pistol
                17_500, // 2 - Rocket Launcher
                1_000, // 3 - sword sharpness upgrade
                3_000, // 4 - shield
        }[winKey];
    }

    public static boolean requiresHotBarClick(int winKey) {
        return new boolean[] {
                true,
                true,
                true,
                false, // sword sharpness upgrade
                false, // shield
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
            case 3 -> {
                return Util.createItemStack(Material.DIAMOND_SWORD, 1,
                        "§4Sharpness Upgrade",
                        "§fYour swords now have §cSharpness I §fpermanently."
                );
            }
            case 4 -> {
                return Util.createItemStack(Material.SHIELD, 1,
                        "§7§lShield",
                        "§fActivating this shield can block §a40%",
                        "§fof incoming damage.",
                        "§7This shield will never break!"
                );
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

            case 3 -> {
                profile.swordSharpnessOne = true;
                profile.coins -= getCost(winKey);
                Util.playSuccessSound(player);
                player.closeInventory();
            }

            case 4 -> {
                ItemStack shield = new ItemStack(Material.SHIELD, 1);
                ItemMeta im = shield.getItemMeta();
                im.setUnbreakable(true);
                im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                shield.setItemMeta(im);
                player.getInventory().setItemInOffHand(shield);
                profile.coins -= getCost(winKey);
                Util.playSuccessSound(player);
                player.closeInventory();
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
