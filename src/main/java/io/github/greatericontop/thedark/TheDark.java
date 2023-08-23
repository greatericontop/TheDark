package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.guns.GunUtil;
import io.github.greatericontop.thedark.guns.ShootGunListener;
import io.github.greatericontop.thedark.menus.ArmorBuyListener;
import io.github.greatericontop.thedark.menus.ArmorEnchantmentListener;
import io.github.greatericontop.thedark.menus.EnhancementListener;
import io.github.greatericontop.thedark.menus.RouletteListener;
import io.github.greatericontop.thedark.menus.RouletteRewardClaimListener;
import io.github.greatericontop.thedark.menus.SignListener;
import io.github.greatericontop.thedark.menus.SwordBuyListener;
import io.github.greatericontop.thedark.player.PlayerShennaniganPreventionListener;
import io.github.greatericontop.thedark.player.ShieldListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TheDark extends JavaPlugin {

    private GameManager gameManager;
    public GameManager getGameManager() {
        return gameManager;
    }

    public ArmorBuyListener armorBuyListener = null;
    public ArmorEnchantmentListener armorEnchantmentListener = null;
    public EnhancementListener enhancementListener = null;
    public RouletteListener rouletteListener = null;
    public RouletteRewardClaimListener rouletteRewardClaimListener = null;
    public SwordBuyListener swordBuyListener = null;



    @Override
    public void onEnable() {
        gameManager = new GameManager(this);
        this.getCommand("thedark").setExecutor(new TheDarkCommand(this));

        this.getServer().getPluginManager().registerEvents(new SignListener(this), this);
        armorBuyListener = new ArmorBuyListener(this);
        this.getServer().getPluginManager().registerEvents(armorBuyListener, this);
        armorEnchantmentListener = new ArmorEnchantmentListener(this);
        this.getServer().getPluginManager().registerEvents(armorEnchantmentListener, this);
        enhancementListener = new EnhancementListener(this);
        this.getServer().getPluginManager().registerEvents(enhancementListener, this);
        rouletteListener = new RouletteListener(this);
        this.getServer().getPluginManager().registerEvents(rouletteListener, this);
        rouletteRewardClaimListener = new RouletteRewardClaimListener(this);
        this.getServer().getPluginManager().registerEvents(rouletteRewardClaimListener, this);
        swordBuyListener = new SwordBuyListener(this);
        this.getServer().getPluginManager().registerEvents(swordBuyListener, this);

        this.getServer().getPluginManager().registerEvents(new PlayerShennaniganPreventionListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ShieldListener(this), this);

        this.getServer().getPluginManager().registerEvents(new GunUtil(this), this);
        this.getServer().getPluginManager().registerEvents(new ShootGunListener(this), this);

        Bukkit.getScheduler().runTaskTimer(this, () -> gameManager.tick(), 20L, 1L);

    }

}
