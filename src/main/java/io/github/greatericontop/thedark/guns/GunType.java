package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.upgrades.UpgradeListing;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public enum GunType {

    PISTOL(30, 10, 200,
            "§ePistol", "§7A basic pistol.",
            Material.WOODEN_HOE) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            int pierce = 2;
            double damage = 4.0;
            double cooldownTicks = 10.0;
            boolean doubleBarrel = false;
            final double extraKBStrength = 0.0;
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
                plugin.shootGunListener.performFire(this, player, left, pierce, damage, -1.0, extraKBStrength);
                plugin.shootGunListener.performFire(this, player, right, pierce, damage, cooldownTicks, extraKBStrength);
            } else {
                plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, cooldownTicks, extraKBStrength);
            }
        }
    },

    RIFLE(30, 30, 1300,
            "§bRifle", "§7A high-powered rifle that fires quickly.",
            Material.STONE_HOE) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            int pierce = 3;
            double damage = 5.0;
            double cooldownTicks = 5.0;
            double extraKBStrength = 0.0;
            // This format lets earlier upgrades carry over to later ones (unless explicitly overwritten)
            if (topPath >= 1) {
                damage = 9.0;
            }
            if (topPath >= 2) {
                damage = 13.0;
            }
            if (topPath >= 3) {
                damage = 25.0;
                extraKBStrength = 0.4;
            }
            if (topPath >= 4) {
                damage = 90.0;
                extraKBStrength = 1.0;
            }
            if (bottomPath >= 1) {
                cooldownTicks = 4;
            }
            if (bottomPath >= 2) {
                cooldownTicks = 3;
            }
            if (bottomPath >= 3) {
                pierce = 4;
            }
            if (bottomPath >= 4) {
                pierce = 8;
            }

            if (bottomPath == 4) {
                // 5 shot burst with no cooldown (5t cooldown so you don't start multiple bursts)
                final double totalCooldownTicks = 5;
                final int finalPierce = pierce;
                final double finalDamage = damage;
                final double finalExtraKBStrength = extraKBStrength;
                plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, totalCooldownTicks, extraKBStrength);
                new BukkitRunnable() {
                    int shotsLeft = 4;
                    public void run() {
                        if (shotsLeft <= 0) {
                            this.cancel();
                            return;
                        }
                        plugin.shootGunListener.performFire(RIFLE, player, player.getEyeLocation().getDirection(), finalPierce, finalDamage, -1.0, finalExtraKBStrength);
                        shotsLeft--;
                    }
                }.runTaskTimer(plugin, 1L, 1L);
            } else if (bottomPath == 3) {
                // 3 shot burst (6t cooldown starting from the first)
                final double totalCooldownTicks = 6;
                final int finalPierce = pierce;
                final double finalDamage = damage;
                final double finalExtraKBStrength = extraKBStrength;
                plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, totalCooldownTicks, extraKBStrength);
                new BukkitRunnable() {
                    int shotsLeft = 2;
                    public void run() {
                        if (shotsLeft <= 0) {
                            this.cancel();
                            return;
                        }
                        plugin.shootGunListener.performFire(RIFLE, player, player.getEyeLocation().getDirection(), finalPierce, finalDamage, -1.0, finalExtraKBStrength);
                        shotsLeft--;
                    }
                }.runTaskTimer(plugin, 2L, 2L);
            } else {
                plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, cooldownTicks, extraKBStrength);
            }
        }
    },

    SHOTGUN(40, 5, 400,
            "§bShotgun", "§7This shotgun damages multiple enemies.",
            Material.IRON_SHOVEL) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            // TODO
        }
    },

    ;

    private final int rechargeTicks;
    private final int ammoCapacity;
    private final int baseCost;
    private final String displayName;
    private final String miniDescription;
    private final Material material;

    public int getRechargeTicks() {
        return rechargeTicks;
    }
    public int getAmmoCapacity() {
        return ammoCapacity;
    }
    public int getBaseCost() {
        return baseCost;
    }
    public String getMiniDescription() {
        return miniDescription;
    }

    GunType(int rechargeTicks, int ammoCapacity, int baseCost,
            String displayName, String miniDescription, Material material) {
        this.rechargeTicks = rechargeTicks;
        this.ammoCapacity = ammoCapacity;
        this.baseCost = baseCost;
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
        im.getPersistentDataContainer().set(UpgradeListing.TOP_PATH, PersistentDataType.INTEGER, 0);
        im.getPersistentDataContainer().set(UpgradeListing.BOTTOM_PATH, PersistentDataType.INTEGER, 0);
        stack.setItemMeta(im);
        return stack;
    }

    public int getMaxDurability() {
        return material.getMaxDurability();
    }


    public abstract void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC);

}
