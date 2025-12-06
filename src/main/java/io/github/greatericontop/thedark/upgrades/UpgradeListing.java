package io.github.greatericontop.thedark.upgrades;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.miscmechanic.GameDifficulty;
import org.bukkit.NamespacedKey;

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
                new Upgrade("50 Cal", "Incredible pierce and damage!", 2200),
                new Upgrade("Faster Firing", "Decrease cooldown to 8.75 ticks", 110),
                new Upgrade("Even Faster Firing", "Decrease cooldown to 7 ticks", 150),
                new Upgrade("Double Barrel", "Shoot 2 bullets at once", 400),
                new Upgrade("Machine Gun Pistol", "Can fire every 2 ticks!", 1550)
        ));
        upgradeList.put(GunType.RIFLE, new ItemUpgrades(
                new Upgrade("High Caliber", "5 -> 9 damage", 1100),
                new Upgrade("Even Higher Caliber", "9 -> 13 damage", 1300),
                new Upgrade("High Energy Rounds", "30 damage per shot and deals extra knockback!", 3400),
                new Upgrade("Doom Rounds", "These zombies won't know what hit them!", 16000),
                new Upgrade("Faster Firing", "Decrease cooldown to 4 ticks", 550),
                new Upgrade("Even Faster Firing", "Decrease cooldown to 3 ticks", 800),
                new Upgrade("Burst", "Shoot 3 extra-pierce bullets instantly for incredible spraying!", 2000),
                new Upgrade("Ultra Assault Rifle", "5 shot bursts with no cooldown!", 6000)
        ));
        upgradeList.put(GunType.SHOTGUN, new ItemUpgrades(
                new Upgrade("Bigger Blast", "Shoots 5 shells", 300),
                new Upgrade("Even Bigger Blast", "Shoots 7 shells", 400),
                new Upgrade("More Shells", "Crams 13 shells into the same area!", 1200),
                new Upgrade("Wave of Destruction", "Shoots 39 shells at a time!", 3600),
                new Upgrade("Faster Firing", "Decrease cooldown to 8.75 ticks", 220),
                new Upgrade("Even Faster Firing", "Decrease cooldown to 7 ticks", 300),
                new Upgrade("Buckshot", "Each shot does double damage!", 1250),
                new Upgrade("Insane Buckshot", "Shots have 3 pierce and deal 16 damage!", 5000)
        ));
        upgradeList.put(GunType.FLAMETHROWER, new ItemUpgrades(
                new Upgrade("High Octane", "Burn lasts an extra 2 seconds", 250),
                new Upgrade("Pure Burn", "Fire deals 50% more damage", 450),
                new Upgrade("Soul Fire", "Fire deals 12 damage per second!", 1400),
                new Upgrade("Nether Master's Napalm", "Fire lasts FOREVER and enemies take 25% more damage while burning!", 6750),
                new Upgrade("Better Pumps", "Flames travel 3 blocks farther and can hit an extra zombie", 225),
                new Upgrade("Ultra Fast Pumps", "Decrease cooldown from 8 ticks to 5!", 600),
                new Upgrade("Heat Sustaining Mixtures", "Flames now travel 18 blocks and can hit 10 zombies!", 1250),
                new Upgrade("Perma-Flame", "No cooldown to fire!", 3050)
        ));
        upgradeList.put(GunType.MIDAS_PISTOL, new ItemUpgrades(
                new Upgrade("Better Deals", "Increases cash bonus from 60% -> 90%", 425),
                new Upgrade("Incredible Deals", "Increases cash bonus from 90% -> 120%", 525),
                new Upgrade("Machine Gun Money Printer", "Fires twice as fast for twice the cash!", 2100),
                new Upgrade("Zombies to Gold", "Turns zombies to gold for 250% more cash!", 3333),
                new Upgrade("Zombie Business", "Sells ammo to zombies (conflict of interest?) for 70 coins per round", 500),
                new Upgrade("More Zombie Business", "Now makes 140 coins per round", 750),
                new Upgrade("Zombie Banking", "Makes 225 coins per round and pays 2% interest on up to 2,500 coins!", 1350),
                new Upgrade("Zombie Investments", "Makes 500 coins per round and pays 3% interest on up to 5,000 coins!", 3450)
        ));
        upgradeList.put(GunType.ROCKET_LAUNCHER, new ItemUpgrades(
                new Upgrade("Bigger Bombs", "Rockets do 9 damage instead of 5", 850),
                new Upgrade("Even Bigger Bombs", "Rockets do 14 damage!", 1250),
                new Upgrade("Extra Ordinance Explosives", "Rockets deal a massive 32 damage!", 3950),
                new Upgrade("Nuclear Bombs", "These ones will wipe out almost all groups of zombies!", 10800),
                new Upgrade("Faster Reload", "Decrease fire cooldown to 18 ticks", 400),
                new Upgrade("Even Faster Reload", "Decrease fire cooldown to 12 ticks", 800),
                new Upgrade("Sharpnel Blast", "Explosions are bigger and can hurt twice as many zombies.", 1750),
                new Upgrade("Cluster Bombs", "Rockets split into 4 high-damage explosions on impact!", 9100)
        ));
        upgradeList.put(GunType.ICE_BLASTER, new ItemUpgrades(
                new Upgrade("Longer Freeze", "Freezes zombies for an extra 2 seconds", 250),
                new Upgrade("Piercing Ice", "Ice can hit 2 more zombies", 500),
                new Upgrade("Mega Freeze", "Freezing effect upgraded to slowness IV!", 1600),
                new Upgrade("Absolute Zero", "Absolute zero freezing ice freezes zombies solid for 8 seconds!", 6000),
                new Upgrade("Faster Firing", "Decrease cooldown to 7 ticks", 300),
                new Upgrade("Icicles", "Sharp icicles now deal 8 damage", 650),
                new Upgrade("Ultra Fast Firing", "Decrease cooldown to 3 ticks", 1100),
                new Upgrade("Glacial Barrage", "Shoots 3 shards of ice at once that each deal 14 damage!", 6000)
        ));
    }

    public ItemUpgrades getUpgradeList(GunType type) {
        return upgradeList.get(type);
    }

    public int getSellValue(GunType type, int top, int bottom, GameDifficulty difficulty) {
        int totalSpent = type.getBaseCost();
        ItemUpgrades upgrades = upgradeList.get(type);
        if (top >= 1)  totalSpent += GameDifficulty.getAdjustedCost(upgrades.top1().cost(), difficulty);
        if (top >= 2)  totalSpent += GameDifficulty.getAdjustedCost(upgrades.top2().cost(), difficulty);
        if (top >= 3)  totalSpent += GameDifficulty.getAdjustedCost(upgrades.top3().cost(), difficulty);
        if (top >= 4)  totalSpent += GameDifficulty.getAdjustedCost(upgrades.top4().cost(), difficulty);
        if (bottom >= 1)  totalSpent += GameDifficulty.getAdjustedCost(upgrades.bottom1().cost(), difficulty);
        if (bottom >= 2)  totalSpent += GameDifficulty.getAdjustedCost(upgrades.bottom2().cost(), difficulty);
        if (bottom >= 3)  totalSpent += GameDifficulty.getAdjustedCost(upgrades.bottom3().cost(), difficulty);
        if (bottom >= 4)  totalSpent += GameDifficulty.getAdjustedCost(upgrades.bottom4().cost(), difficulty);
        return (int) (totalSpent * 0.75);
    }

}
