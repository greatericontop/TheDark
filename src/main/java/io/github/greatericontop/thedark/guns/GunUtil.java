package io.github.greatericontop.thedark.guns;

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

    public static @Nullable GunClassification getHeldGunClassification(Player player) {
        ItemStack stack = player.getInventory().getItemInMainHand();
        ItemMeta im = stack.getItemMeta();
        if (im == null)  return null;
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        if (pdc.has(GunUtil.GUN_KEY, PersistentDataType.STRING)) {
            return GunClassification.valueOf(pdc.get(GunUtil.GUN_KEY, PersistentDataType.STRING));
        }
        return null;
    }

}
