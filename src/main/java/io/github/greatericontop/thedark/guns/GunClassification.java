package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.Vector;

public enum GunClassification {
    // this enum contains the classifications of guns (compared to GunType which contains distinct types for
    // each enhancement star), and also contains some characteristics that are constant across enhancement stars

    PISTOL(30, "§7A basic pistol.", 1,
            "PISTOL", "PISTOL_1STAR") {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            int pierce = 2;
            double damage = 4.0;
            double cooldownTicks = 10.0;
            boolean doubleBarrel = false;
            switch (topPath) {
                case 1 -> {
                    pierce = 3;
                }
                case 2 -> {
                    pierce = 4;
                }
                case 3 -> {
                    pierce = 3;
                    damage = 10.0;
                }
                case 4 -> {
                    pierce = 8;
                    damage = 14.0;
                }
            }
            switch (bottomPath) {
                case 1 -> {
                    cooldownTicks = 8.75;
                }
                case 2 -> {
                    cooldownTicks = 7.75;
                }
                case 3 -> {
                    cooldownTicks = 7.75;
                    doubleBarrel = true;
                }
                case 4 -> {
                    cooldownTicks = 3.0;
                    doubleBarrel = true;
                }
            }

            if (doubleBarrel) {
                Vector direction = player.getEyeLocation().getDirection();
                Vector left = direction.clone().rotateAroundY(0.087266); // 5 degrees
                Vector right = direction.clone().rotateAroundY(-0.087266);
                plugin.shootGunListener.performFire(this, player, left, pierce, damage, -1.0);
                plugin.shootGunListener.performFire(this, player, right, pierce, damage, cooldownTicks);
            } else {
                plugin.shootGunListener.performFire(this, player, player.getEyeLocation().getDirection(), pierce, damage, cooldownTicks);
            }
        }
    },
    RIFLE(30, "§7A high-powered rifle that fires quickly.", 1,
            "RIFLE", "RIFLE_1STAR") {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            // TODO
        }
    },
    SHOTGUN(40, "§7This shotgun damages multiple enemies.", 1,
            "SHOTGUN", "SHOTGUN_1STAR") {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            // TODO
        }
    },

    FLAMETHROWER(30, "§7Like §4f§ci§6r§ee§7? We got it here!", 2,
            "FLAMETHROWER", "FLAMETHROWER_1STAR", "FLAMETHROWER_2STAR") {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            // TODO
        }
    },
    MIDAS_PISTOL(30, "§7Enemies hit turn to §6gold §7and drop §6triple gold§7!", 2,
            "MIDAS_PISTOL", "MIDAS_PISTOL_1STAR", "MIDAS_PISTOL_2STAR") {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            // TODO
        }
    },

    ROCKET_LAUNCHER(140, "§8§lBig Boom", 3,
            "ROCKET_LAUNCHER", "ROCKET_LAUNCHER_1STAR", "ROCKET_LAUNCHER_2STAR", "ROCKET_LAUNCHER_3STAR") {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            // TODO
        }
    },

    SUPER_WEAPON(30, "§4Need I say more?", 1,
            "SUPER_WEAPON", "SUPER_WEAPON_1STAR") {
        @Override
        public void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC) {
            // TODO
        }
    },

    ;

    private final int rechargeTicks;
    private final String miniDescription;
    private final int maxEnhancementStars;
    private final String[] childrenNames;

    public int getRechargeTicks() {
        return rechargeTicks;
    }
    public String getMiniDescription() {
        return miniDescription;
    }
    public int getMaxEnhancementStars() {
        return maxEnhancementStars;
    }

    @Deprecated
    public GunType getRootGun() {
        return getChildGun(0);
    }
    public GunType getChildGun(int stars) {
        return GunType.valueOf(childrenNames[stars]);
    }

    GunClassification(int rechargeTicks, String miniDescription, int maxEnhancementStars, String... childrenNames) {
        assert childrenNames.length == maxEnhancementStars + 1; // root & 1 for each star
        this.rechargeTicks = rechargeTicks;
        this.miniDescription = miniDescription;
        this.maxEnhancementStars = maxEnhancementStars;
        this.childrenNames = childrenNames;
    }

    public abstract void fire(Player player, TheDark plugin, int topPath, int bottomPath, PersistentDataContainer extraPDC);

}
