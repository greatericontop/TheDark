package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class BuyGunManager {

    public static void buy(GunType gunType, PlayerProfile profile, Player player) {
        int cost = gunType.getCost();
        if (profile.coins < cost) {
            player.sendMessage("§3You don't have enough money to buy this gun!");
            return;
        }
        if (attemptGive(gunType, player, player.getInventory().getHeldItemSlot())) {
            // only subtract coins if successful
            profile.coins -= cost;
        }
    }

    public static boolean attemptGive(GunType gunType, Player player, int requestedSlot) {
        if (requestedSlot < 1 || requestedSlot > 3) {
            player.sendMessage("§3You can only buy guns in your 3 slots!");
            return false;
        }
        // if the player has available space and tried to replace a gun, cancel
        if (getFirstSpace(player) != -1 && isPopulated(player.getInventory().getItem(requestedSlot))) {
            player.sendMessage("§3You have available slots. You don't need to replace a gun.");
            return false;
        }
        // only 1 of each gun permitted
        if (getGunsInInventory(player).contains(gunType.getClassification())) {
            player.sendMessage("§3You can only have 1 of each gun!");
            return false;
        }
        ItemStack gunItem = gunType.createFullyLoadedItemStack();
        player.getInventory().setItem(requestedSlot, gunItem);
        player.sendMessage("§3Successfully purchased your gun!");
        return true;
    }

    public static void debugGiveGun(GunType gunType, Player player, Integer forceSlot) {
        int slot = forceSlot != null ? forceSlot : getFirstSpace(player);
        if (slot == -1) {
            // no space available and not forced
            player.sendMessage("§3No slot was available and the gun give was not forced to a slot.");
            return;
        }
        ItemStack gunItem = gunType.createFullyLoadedItemStack();
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

    private static List<GunClassification> getGunsInInventory(Player player) {
        List<GunClassification> guns = new ArrayList<>();
        for (int slot = 1; slot <= 3; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (!isPopulated(stack))  continue;
            ItemMeta im = stack.getItemMeta();
            if (im == null)  continue;
            PersistentDataContainer pdc = im.getPersistentDataContainer();
            if (pdc.has(GunUtil.GUN_KEY, PersistentDataType.STRING)) {
                GunType gunType = GunType.valueOf(pdc.get(GunUtil.GUN_KEY, PersistentDataType.STRING));
                guns.add(gunType.getClassification());
            }
        }
        return guns;
    }

    private static boolean isPopulated(ItemStack stack) {
        return stack != null && stack.getType() != Material.LIGHT_GRAY_DYE;
    }

}
