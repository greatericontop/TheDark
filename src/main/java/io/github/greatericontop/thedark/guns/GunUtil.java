package io.github.greatericontop.thedark.guns;

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

import io.github.greatericontop.thedark.upgrades.UpgradeListing;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

public class GunUtil implements Listener {
    public static final NamespacedKey GUN_KEY = new NamespacedKey("thedark", "gun");

    public static int getDamagePositionBelowCurrent(int maxDurability, int ticksToRefill, int currentDamage) {
        // we subtract 0.5 here because the initial damage will be :maxDurability - 1:, so
        // then it will successfully go down to the next one on the next tick
        double step = (maxDurability - 0.5) / ((double) ticksToRefill);
        // iterative approach is slow but good enough
        for (double damageAmount = 0.0; damageAmount < maxDurability; damageAmount += step) {
            // (add compensation for floating point problems, with slightly more for the first one)
            if (damageAmount + 0.0000015 >= currentDamage) {
                return (int) (damageAmount - step + 0.000001);
            }
        }
        throw new RuntimeException("currentDamage is greater than last value of damageAmount (" + currentDamage + ")");
    }

    public static @Nullable GunType getHeldGunType(ItemStack stack) {
        ItemMeta im = stack.getItemMeta();
        if (im == null)  return null;
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        if (pdc.has(GunUtil.GUN_KEY, PersistentDataType.STRING)) {
            return GunType.valueOf(pdc.get(GunUtil.GUN_KEY, PersistentDataType.STRING));
        }
        return null;
    }
    public static @Nullable GunType getHeldGunType(Player player) {
        ItemStack stack = player.getInventory().getItemInMainHand();
        return getHeldGunType(stack);
    }

    public static int[] getUpgradesForHeldGun(ItemStack stack) {
        ItemMeta im = stack.getItemMeta();
        if (im == null)  return null;
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        int top = pdc.get(UpgradeListing.TOP_PATH, PersistentDataType.INTEGER);
        int bottom = pdc.get(UpgradeListing.BOTTOM_PATH, PersistentDataType.INTEGER);
        return new int[]{top, bottom};
    }

}
