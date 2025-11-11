package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.enemy.EnemyDeathListener;
import io.github.greatericontop.thedark.guns.GunUtil;
import io.github.greatericontop.thedark.guns.ShootGunListener;
import io.github.greatericontop.thedark.menus.ArmorBuyListener;
import io.github.greatericontop.thedark.menus.ArmorEnchantmentListener;
import io.github.greatericontop.thedark.menus.EnhancementListener;
import io.github.greatericontop.thedark.menus.GunUpgradeListener;
import io.github.greatericontop.thedark.menus.SignListener;
import io.github.greatericontop.thedark.menus.SwordBuyListener;
import io.github.greatericontop.thedark.player.PlayerShennaniganPreventionListener;
import io.github.greatericontop.thedark.upgrades.UpgradeUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TheDark extends JavaPlugin {

    private GameManager gameManager;
    public GameManager getGameManager() {
        return gameManager;
    }

    public UpgradeUtils upgradeUtils = null;

    public ArmorBuyListener armorBuyListener = null;
    public ArmorEnchantmentListener armorEnchantmentListener = null;
    public EnhancementListener enhancementListener = null;
    public GunUpgradeListener gunUpgradeListener = null;
    public SwordBuyListener swordBuyListener = null;

    public ShootGunListener shootGunListener = null;



    @Override
    public void onEnable() {
        gameManager = new GameManager(this);
        this.getCommand("thedark").setExecutor(new TheDarkCommand(this));

        upgradeUtils = new UpgradeUtils(this);

        this.getServer().getPluginManager().registerEvents(new SignListener(this), this);
        armorBuyListener = new ArmorBuyListener(this);
        this.getServer().getPluginManager().registerEvents(armorBuyListener, this);
        armorEnchantmentListener = new ArmorEnchantmentListener(this);
        this.getServer().getPluginManager().registerEvents(armorEnchantmentListener, this);
        enhancementListener = new EnhancementListener(this);
        this.getServer().getPluginManager().registerEvents(enhancementListener, this);
        gunUpgradeListener = new GunUpgradeListener(this);
        this.getServer().getPluginManager().registerEvents(gunUpgradeListener, this);
        swordBuyListener = new SwordBuyListener(this);
        this.getServer().getPluginManager().registerEvents(swordBuyListener, this);

        this.getServer().getPluginManager().registerEvents(new EnemyDeathListener(this), this);

        this.getServer().getPluginManager().registerEvents(new PlayerShennaniganPreventionListener(this), this);

        this.getServer().getPluginManager().registerEvents(new GunUtil(this), this);
        shootGunListener = new ShootGunListener(this);
        this.getServer().getPluginManager().registerEvents(shootGunListener, this);

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        // Placeholders
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderManager(this).register();
        } else {
            getLogger().warning("PlaceholderAPI not found, you will lose its functionality");
        }

        Bukkit.getScheduler().runTaskTimer(this, () -> gameManager.tick(), 20L, 1L);

    }

}
