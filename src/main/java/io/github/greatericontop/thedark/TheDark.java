package io.github.greatericontop.thedark;

/*
 * Copyright (C) 2023-present greateric.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import io.github.greatericontop.thedark.enemy.EnemyListener;
import io.github.greatericontop.thedark.guns.ShootGunListener;
import io.github.greatericontop.thedark.menus.ArmorBuyListener;
import io.github.greatericontop.thedark.menus.GunUpgradeListener;
import io.github.greatericontop.thedark.menus.SignListener;
import io.github.greatericontop.thedark.menus.StartGameMenu;
import io.github.greatericontop.thedark.player.PlayerDeathListener;
import io.github.greatericontop.thedark.player.PlayerShennaniganPreventionListener;
import io.github.greatericontop.thedark.rounds.RoundManager;
import io.github.greatericontop.thedark.upgrades.UpgradeListing;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TheDark extends JavaPlugin {

    private GameManager gameManager;
    public GameManager getGameManager() {
        return gameManager;
    }
    private RoundManager roundManager;
    public RoundManager getRoundManager() {
        return roundManager;
    }

    public UpgradeListing upgradeUtils = null;

    public ArmorBuyListener armorBuyListener = null;
    public GunUpgradeListener gunUpgradeListener = null;
    public StartGameMenu startGameMenu = null;

    public ShootGunListener shootGunListener = null;



    @Override
    public void onEnable() {
        gameManager = new GameManager(this);
        roundManager = new RoundManager(this);
        this.getCommand("thedark").setExecutor(new TheDarkCommand(this));

        upgradeUtils = new UpgradeListing(this);

        this.getServer().getPluginManager().registerEvents(new SignListener(this), this);
        armorBuyListener = new ArmorBuyListener(this);
        this.getServer().getPluginManager().registerEvents(armorBuyListener, this);
        gunUpgradeListener = new GunUpgradeListener(this);
        this.getServer().getPluginManager().registerEvents(gunUpgradeListener, this);
        startGameMenu = new StartGameMenu(this);
        this.getServer().getPluginManager().registerEvents(startGameMenu, this);

        this.getServer().getPluginManager().registerEvents(new EnemyListener(this), this);

        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerShennaniganPreventionListener(this), this);

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
