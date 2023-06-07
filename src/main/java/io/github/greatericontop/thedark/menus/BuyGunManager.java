package io.github.greatericontop.thedark.menus;

import io.github.greatericontop.thedark.guns.GunType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuyGunManager {

    public static void attemptBuy(GunType gunType, Player player, int requestedSlot) {
        if (requestedSlot < 1 || requestedSlot > 3) {
            player.sendMessage("§3You can only buy guns in your 3 slots!");
            return;
        }
        // if the player has available space and tried to replace a gun, cancel
        if (getFirstSpace(player) != -1 && isPopulated(player.getInventory().getItem(requestedSlot))) {
            player.sendMessage("§3You have available slots. You don't need to replace a gun.");
            return;
        }
        ItemStack gunItem = gunType.createItemStack();
        player.getInventory().setItem(requestedSlot, gunItem);
    }

    public static void debugGiveGun(GunType gunType, Player player, Integer forceSlot) {
        int slot = forceSlot != null ? forceSlot : getFirstSpace(player);
        if (slot == -1) {
            // no space available and not forced
            player.sendMessage("§3No slot was available and the gun give was not forced to a slot.");
            return;
        }
        ItemStack gunItem = gunType.createItemStack();
        player.getInventory().setItem(slot, gunItem);
    }

    private static int getFirstSpace(Player player) {
        // check for guns in slot 1, 2, 3
        for (int slot = 1; slot <= 3; slot++) {
            if (!isPopulated(player.getInventory().getItem(slot))) {
                return slot;
            }
        }
        return -1;
    }

    private static boolean isPopulated(ItemStack stack) {
        return stack != null && stack.getType() != Material.LIGHT_GRAY_DYE;
    }

}
