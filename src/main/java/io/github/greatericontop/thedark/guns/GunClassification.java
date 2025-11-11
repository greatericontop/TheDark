package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.upgrades.UpgradeUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.List;

public enum GunClassification {
    // this enum contains the classifications of guns (compared to GunType which contains distinct types for
    // each enhancement star), and also contains some characteristics that are constant across enhancement stars

    PISTOL(30, 10, "§ePistol", "§7A basic pistol.",
            Material.WOODEN_HOE) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            int pierce = 2;
            double damage = 4.0;
            double cooldownTicks = 10.0;
            boolean doubleBarrel = false;
            // This format lets earlier upgrades carry over to later ones (unless explicitly overwritten)
            if (topPath >= 1) {
                pierce = 3;
            }
            if (topPath >= 2) {
                pierce = 4;
            }
            if (topPath >= 3) {
                pierce = 3;
                damage = 10.0;
            }
            if (topPath >= 4) {
                pierce = 8;
                damage = 14.0;
            }
            if (bottomPath >= 1) {
                cooldownTicks = 8.75;
            }
            if (bottomPath >= 2) {
                cooldownTicks = 7.75;
            }
            if (bottomPath >= 3) {
                doubleBarrel = true;
            }
            if (bottomPath >= 4) {
                cooldownTicks = 3.0;
            }
            if (doubleBarrel) {
                Vector direction = player.getEyeLocation().getDirection();
                Vector left = direction.clone().rotateAroundY(0.034906); // 2 degrees
                Vector right = direction.clone().rotateAroundY(-0.034906);
                plugin.shootGunListener.performFire(this, player, left, pierce, damage, -1.0);
                plugin.shootGunListener.performFire(this, player, right, pierce, damage, cooldownTicks);
            } else {
                plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, cooldownTicks);
            }
        }
    },

    RIFLE(30, 30, "§bRifle", "§7A high-powered rifle that fires quickly.",
            Material.STONE_HOE) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            // TODO
        }
    },

    SHOTGUN(40, 5, "§bShotgun", "§7This shotgun damages multiple enemies.",
            Material.IRON_SHOVEL) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            // TODO
        }
    },

    ;

    private final int rechargeTicks;
    private final int ammoCapacity;
    private final String displayName;
    private final String miniDescription;
    private final Material material;

    public int getRechargeTicks() {
        return rechargeTicks;
    }
    public int getAmmoCapacity() {
        return ammoCapacity;
    }
    public String getMiniDescription() {
        return miniDescription;
    }

    GunClassification(int rechargeTicks, int ammoCapacity, String displayName, String miniDescription, Material material) {
        this.rechargeTicks = rechargeTicks;
        this.ammoCapacity = ammoCapacity;
        this.displayName = displayName;
        this.miniDescription = miniDescription;
        this.material = material;
    }

    public ItemStack createFullyLoadedItemStack() {
        ItemStack stack = new ItemStack(material, ammoCapacity);
        ItemMeta im = stack.getItemMeta();
        im.displayName(Component.text(displayName));
        im.lore(List.of(
                Component.text(getMiniDescription()),
                Component.text(""),
                Component.text(String.format("§8Capacity: %d | Reload: %.1fs", getAmmoCapacity(), getRechargeTicks() * 0.05))
        ));
        im.getPersistentDataContainer().set(GunUtil.GUN_KEY, PersistentDataType.STRING, this.name());
        im.getPersistentDataContainer().set(UpgradeUtils.TOP_PATH, PersistentDataType.INTEGER, 0);
        im.getPersistentDataContainer().set(UpgradeUtils.BOTTOM_PATH, PersistentDataType.INTEGER, 0);
        stack.setItemMeta(im);
        return stack;
    }

    public int getMaxDurability() {
        return material.getMaxDurability();
    }


    public abstract void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC);

}
