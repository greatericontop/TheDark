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
                new Upgrade("Faster Firing", "Decrease cooldown to 9 ticks", 110),
                new Upgrade("Even Faster Firing", "Decrease cooldown to 7.5 ticks", 140),
                new Upgrade("Double Barrel", "Shoot 2 bullets at once", 400),
                new Upgrade("Machine Gun Pistol", "Can fire every 3 ticks!", 1550)
        ));
        upgradeList.put(GunType.RIFLE, new ItemUpgrades(
                new Upgrade("High Caliber", "5 -> 9 damage", 1300),
                new Upgrade("Even Higher Caliber", "9 -> 13 damage", 1500),
                new Upgrade("High Energy Rounds", "25 damage per shot and deals extra knockback!", 3500),
                new Upgrade("Doom Rounds", "These zombies won't know what hit them!", 17000),
                new Upgrade("Faster Firing", "Decrease cooldown to 4 ticks", 1000),
                new Upgrade("Even Faster Firing", "Decrease cooldown to 3 ticks", 1400),
                new Upgrade("Burst", "Shoot 3 extra-pierce bullets instantly for incredible spraying!", 2000),
                new Upgrade("Ultra Assault Rifle", "5 shot bursts with no cooldown!", 6000)
        ));
        upgradeList.put(GunType.SHOTGUN, new ItemUpgrades(
                new Upgrade("Bigger Blast", "Shoots 5 shells", 300),
                new Upgrade("Even Bigger Blast", "Shoots 7 shells", 400),
                new Upgrade("More Shells", "Crams 13 shells into the same area!", 1200),
                new Upgrade("Wave of Destruction", "Shoots 39 shells at a time!", 3750),
                new Upgrade("Faster Firing", "Decrease cooldown to 9 ticks", 220),
                new Upgrade("Even Faster Firing", "Decrease cooldown to 7.5 ticks", 280),
                new Upgrade("Buckshot", "Each shot does double damage!", 1250),
                new Upgrade("Insane Buckshot", "Shots have 3 pierce and deal 12 damage!", 5000)
        ));
        upgradeList.put(GunType.FLAMETHROWER, new ItemUpgrades(
                new Upgrade("High Octane", "Burn lasts an extra 2 seconds", 250),
                new Upgrade("Pure Burn", "Fire deals 50% more damage", 450),
                new Upgrade("Soul Fire", "Fire deals 3 damage per second!", 1400),
                new Upgrade("Nether Master's Napalm", "Fire lasts FOREVER and enemies take 20% more damage while burning!", 6750),
                new Upgrade("Better Pumps", "Flames travel 2 blocks farther and can hit an extra zombie", 225),
                new Upgrade("Ultra Fast Pumps", "Decrease cooldown from 8 ticks to 5!", 600),
                new Upgrade("Heat Sustaining Mixtures", "Flames now travel 15 blocks and can hit 10 zombies!", 1250),
                new Upgrade("Perma-Flame", "No cooldown to fire!", 3050)
        ));
        upgradeList.put(GunType.MIDAS_PISTOL, new ItemUpgrades(
                new Upgrade("Better Deals", "Increases cash bonus from 50% -> 75%", 425),
                new Upgrade("Incredible Deals", "Increases cash bonus from 75% -> 100%", 525),
                new Upgrade("Machine Gun Money Printer", "Fires twice as fast for twice the cash!", 2100),
                new Upgrade("Zombies to Gold", "Turns zombies to gold for triple the cash!", 3333),
                new Upgrade("Zombie Business", "Sells ammo to zombies (conflict of interest?) for 60 coins per round", 500),
                new Upgrade("More Zombie Business", "Now makes 120 coins per round", 750),
                new Upgrade("Zombie Banking", "Makes 200 coins per round and pays 2% interest on up to 2,500 coins!", 1700),
                new Upgrade("Zombie Investments", "Makes 325 coins per round and pays 3% interest on up to 5,000 coins!", 3600)
        ));
    }

    @Nullable
    public ItemUpgrades getUpgradeList(GunType type) {
        return upgradeList.get(type);
    }

}
