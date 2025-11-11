package io.github.greatericontop.thedark.upgrades;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.guns.GunType;
import org.bukkit.NamespacedKey;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class UpgradeListing {
    public static final NamespacedKey TOP_PATH = new NamespacedKey("thedark", "upgrade_top");
    public static final NamespacedKey BOTTOM_PATH = new NamespacedKey("thedark", "upgrade_bottom");
    private final Map<GunType, ItemUpgrades> upgradeList;

    private final TheDark plugin;
    public UpgradeListing(TheDark plugin) {
        this.plugin = plugin;
        this.upgradeList = new HashMap<>();
        upgradeList.put(GunType.PISTOL, new ItemUpgrades(
                new Upgrade("Sharper Bullets", "+1 pierce", 125),
                new Upgrade("Even Sharper Bullets", "+1 pierce", 160),
                new Upgrade("Hollow Point", "-1 pierce but +6 damage", 500),
                new Upgrade("50 Cal", "Incredible pierce and damage!", 2300),
                new Upgrade("Faster Firing", "Decrease cooldown to 8.75 ticks", 100),
                new Upgrade("Even Faster Firing", "Decrease cooldown to 7.75 ticks", 125),
                new Upgrade("Double Barrel", "Shoot 2 bullets at once", 400),
                new Upgrade("Machine Gun Pistol", "Can fire every 3 ticks!", 1550)
        ));
        upgradeList.put(GunType.RIFLE, new ItemUpgrades(
                new Upgrade("High Caliber", "5 -> 9 damage", 1300),
                new Upgrade("Even Higher Caliber", "9 -> 13 damage", 1500),
                new Upgrade("High Energy Rounds", "25 damage per shot and deals extra knockback!", 3500),
                new Upgrade("Doom Rounds", "These zombies won't know what hit them!", 16000),
                new Upgrade("Faster Firing", "Decrease cooldown to 4 ticks", 1000),
                new Upgrade("Even Faster Firing", "Decrease cooldown to 3 ticks", 1400),
                new Upgrade("Burst", "Shoot 3 bullets instantly!", 2000),
                new Upgrade("Ultra Assault Rifle", "5 shot high-pierce bursts with no cooldown!", 6000)
        ));
    }

    @Nullable
    public ItemUpgrades getUpgradeList(GunType type) {
        return upgradeList.get(type);
    }




}
