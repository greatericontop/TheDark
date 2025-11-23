package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.miscmechanic.FireStatus;
import io.github.greatericontop.thedark.upgrades.UpgradeListing;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public enum GunType {

    PISTOL(30, 12, 200,
            "§ePistol", "§7A basic pistol.",
            Material.WOODEN_HOE) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            int pierce = 2;
            double damage = 4.0;
            double cooldownTicks = 11.0;
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
                pierce = 7;
                damage = 18.0;
            }
            if (bottomPath >= 1) {
                cooldownTicks = 8.75;
            }
            if (bottomPath >= 2) {
                cooldownTicks = 7.0;
            }
            if (bottomPath >= 3) {
                doubleBarrel = true;
            }
            if (bottomPath >= 4) {
                cooldownTicks = 2.0;
            }
            if (doubleBarrel) {
                Vector direction = player.getEyeLocation().getDirection();
                Vector left = direction.clone().rotateAroundY(0.034906); // 2 degrees
                Vector right = direction.clone().rotateAroundY(-0.034906);
                plugin.shootGunListener.performFire(this, player, left, pierce, damage, -1.0,
                        0.0, false, ShootGunHelper.MAX_DISTANCE, null,
                        Particle.ASH, Sound.ENTITY_GENERIC_EXPLODE);
                plugin.shootGunListener.performFire(this, player, right, pierce, damage, cooldownTicks,
                        0.0, false, ShootGunHelper.MAX_DISTANCE, null,
                        Particle.ASH, Sound.ENTITY_GENERIC_EXPLODE);
            } else {
                plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, cooldownTicks,
                        0.0, false, ShootGunHelper.MAX_DISTANCE, null,
                        Particle.ASH, Sound.ENTITY_GENERIC_EXPLODE);
            }
        }
    },

    RIFLE(30, 30, 1100,
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
                extraKBStrength = 0.45;
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
                plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, totalCooldownTicks,
                        extraKBStrength, false, ShootGunHelper.MAX_DISTANCE, null,
                        Particle.ASH, Sound.ENTITY_GENERIC_EXPLODE);
                new BukkitRunnable() {
                    int shotsLeft = 4;
                    public void run() {
                        if (shotsLeft <= 0) {
                            this.cancel();
                            return;
                        }
                        plugin.shootGunListener.performFire(RIFLE, player, player.getEyeLocation().getDirection(), finalPierce, finalDamage, -1.0,
                                finalExtraKBStrength, false, ShootGunHelper.MAX_DISTANCE, null,
                                Particle.ASH, Sound.ENTITY_GENERIC_EXPLODE);
                        shotsLeft--;
                    }
                }.runTaskTimer(plugin, 1L, 1L);
            } else if (bottomPath == 3) {
                // 3 shot burst (6t cooldown starting from the first)
                final double totalCooldownTicks = 6;
                final int finalPierce = pierce;
                final double finalDamage = damage;
                final double finalExtraKBStrength = extraKBStrength;
                plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, totalCooldownTicks,
                        extraKBStrength, false, ShootGunHelper.MAX_DISTANCE, null,
                        Particle.ASH, Sound.ENTITY_GENERIC_EXPLODE);
                new BukkitRunnable() {
                    int shotsLeft = 2;
                    public void run() {
                        if (shotsLeft <= 0) {
                            this.cancel();
                            return;
                        }
                        plugin.shootGunListener.performFire(RIFLE, player, player.getEyeLocation().getDirection(), finalPierce, finalDamage, -1.0,
                                finalExtraKBStrength, false, ShootGunHelper.MAX_DISTANCE, null,
                                Particle.ASH, Sound.ENTITY_GENERIC_EXPLODE);
                        shotsLeft--;
                    }
                }.runTaskTimer(plugin, 2L, 2L);
            } else {
                plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, cooldownTicks,
                        extraKBStrength, false, ShootGunHelper.MAX_DISTANCE, null,
                        Particle.ASH, Sound.ENTITY_GENERIC_EXPLODE);
            }
        }
    },

    SHOTGUN(30, 5, 400,
            "§bShotgun", "§7This shotgun damages multiple enemies.",
            Material.IRON_SHOVEL) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            int shotsPerSide = 1; // = 3 total
            double anglePerShot = 0.069813; // 4 degrees
            int pierce = 1;
            double damage = 4.0;
            double cooldownTicks = 11.0;
            int verticalShotsPerSide = 0; // 1 total
            if (topPath >= 1) {
                shotsPerSide = 2; // 5
            }
            if (topPath >= 2) {
                shotsPerSide = 3; // 7
            }
            if (topPath >= 3) {
                shotsPerSide = 6; // 13
                anglePerShot = 0.034907; // 12/6 = 2 degrees
            }
            if (topPath >= 4) {
                verticalShotsPerSide = 1;
            }
            if (bottomPath >= 1) {
                cooldownTicks = 8.75;
            }
            if (bottomPath >= 2) {
                cooldownTicks = 7.0;
            }
            if (bottomPath >= 3) {
                damage = 8.0;
            }
            if (bottomPath >= 4) {
                pierce = 3;
                damage = 12.0;
            }
            Vector baseDirection = player.getEyeLocation().getDirection().normalize();
            double baseR = Math.sqrt(baseDirection.getX()*baseDirection.getX() + baseDirection.getZ()*baseDirection.getZ());
            double basePitch = Math.atan2(baseDirection.getY(), baseR);
            double baseYaw = Math.atan2(baseDirection.getZ(), baseDirection.getX());
            boolean firstShot = true;
            for (int vertical = -verticalShotsPerSide; vertical <= verticalShotsPerSide; vertical++) {
                double pitch = basePitch + anglePerShot*vertical;
                for (int horizontal = -shotsPerSide; horizontal <= shotsPerSide; horizontal++) {
                    double yaw = baseYaw + anglePerShot*horizontal;
                    Vector direction = new Vector(Math.cos(pitch)*Math.cos(yaw), Math.sin(pitch), Math.cos(pitch)*Math.sin(yaw));
                    plugin.shootGunListener.performFire(this, player, direction, pierce, damage, firstShot ? cooldownTicks : -1.0,
                            0.0, true, ShootGunHelper.MAX_DISTANCE, null,
                            Particle.ASH, firstShot ? Sound.ENTITY_GENERIC_EXPLODE : null);
                    firstShot = false;
                }
            }
        }
    },

    FLAMETHROWER(60, 48, 450,
            "§6Flamethrower", "§7BURN",
            Material.GOLDEN_SHOVEL) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            int pierce = 3;
            int fireTicks = 80 + 10;
            double fireDamage = 3.0;
            double cooldownTicks = 8.0;
            double rangeBlocks = 7.0;
            boolean isSevere = false;
            if (topPath >= 1) {
                fireTicks = 120 + 10;
            }
            if (topPath >= 2) {
                fireDamage = 4.5;
            }
            if (topPath >= 3) {
                fireDamage = 9.0;
            }
            if (topPath >= 4) {
                fireTicks = Integer.MAX_VALUE;
                isSevere = true;
            }
            if (bottomPath >= 1) {
                rangeBlocks = 9.0;
            }
            if (bottomPath >= 2) {
                cooldownTicks = 5.0;
            }
            if (bottomPath >= 3) {
                rangeBlocks = 15.0;
                pierce = 10;
            }
            if (bottomPath >= 4) {
                cooldownTicks = 1.0;
            }
            FireStatus fireStatus = new FireStatus(fireTicks, fireDamage, isSevere);
            plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, fireDamage*0.5, cooldownTicks,
                    0.0, false, rangeBlocks, fireStatus,
                    Particle.SMALL_FLAME, Sound.ENTITY_PLAYER_HURT_ON_FIRE);
        }
    },

    MIDAS_PISTOL(30, 12, 750,
            "§6Midas Pistol", "§7Turns zombies into gold! Earn +60% coins from hits!",
            Material.GOLDEN_HOE) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            int pierce = 2;
            double damage = 4.0;
            double cooldownTicks = 11.0;
            // Only effect on shooting is the speed
            if (topPath >= 3) {
                cooldownTicks = 5.0;
            }
            plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, cooldownTicks,
                    0.0, false, ShootGunHelper.MAX_DISTANCE, null,
                    Particle.ASH, Sound.ENTITY_GENERIC_EXPLODE);
        }
    },

    ROCKET_LAUNCHER(2, 1, 800,
            "§dRocket Launcher", "§7Rockets explode and damage many zombies!",
            Material.NETHERITE_PICKAXE) {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            int pierce = 6;
            double damage = 5.0;
            double cooldownTicks = 24.0;
            double explosionRadius = 3.0;
            boolean secondaryExplosions = false;
            if (topPath >= 1) {
                damage = 9.0;
            }
            if (topPath >= 2) {
                damage = 14.0;
            }
            if (topPath >= 3) {
                damage = 32.0;
            }
            if (topPath >= 4) {
                damage = 75.0;
            }
            if (bottomPath >= 1) {
                cooldownTicks = 18.0;
            }
            if (bottomPath >= 2) {
                cooldownTicks = 12.0;
            }
            if (bottomPath >= 3) {
                explosionRadius = 5.0;
                pierce = 12;
            }
            if (bottomPath >= 4) {
                secondaryExplosions = true;
            }
            plugin.shootGunListener.performFireExplosion(this, player, player.getEyeLocation().getDirection(), pierce, damage, cooldownTicks,
                    ShootGunHelper.EXPLOSION_MAX_DISTANCE, explosionRadius, secondaryExplosions);
        }
    }

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
