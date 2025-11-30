package io.github.greatericontop.thedark.menus;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.miscmechanic.GameDifficulty;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class StartGameMenu extends GenericMenu {
    public static final Component INVENTORY_NAME = Component.text("§c[TheDark] §bStart Game");
    private static final int IRON_SLOT = 1;
    private static final int GOLD_SLOT = 3;
    private static final int DIAMOND_SLOT = 5;
    private static final int NETHERITE_SLOT = 7;

    private final TheDark plugin;
    public StartGameMenu(TheDark plugin) {
        this.plugin = plugin;
    }

    @Override
    public void openMenu(PlayerProfile profile) {
        Player player = profile.getPlayer();
        Inventory gui = Bukkit.createInventory(player, 9, INVENTORY_NAME);

        gui.setItem(IRON_SLOT, Util.createItemStack(Material.IRON_BLOCK, 1,
                "§fIron Difficulty",
                "§7Upgrades cost §e10% §7less",
                "§7Max health up to §e14 §7hearts",
                "",
                "§aClick to start!"));
        gui.setItem(GOLD_SLOT, Util.createItemStack(Material.GOLD_BLOCK, 1,
                "§6Gold Difficulty",
                "§7Normal upgrade costs",
                "§7Max health up to §e12 §7hearts",
                "",
                "§aClick to start!"));
        gui.setItem(DIAMOND_SLOT, Util.createItemStack(Material.DIAMOND_BLOCK, 1,
                "§bDiamond Difficulty",
                "§7Upgrades cost §e7% §7more",
                "§7Max health up to §e10 §7hearts",
                "",
                "§aClick to start!"));
        gui.setItem(NETHERITE_SLOT, Util.createItemStack(Material.NETHERITE_BLOCK, 1,
                "§8Netherite Difficulty",
                "§bDiamond §7rules apply",
                "§4No natural regeneration",
                "",
                "§cAre you ready?"));

        player.openInventory(gui);
    }

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().title().equals(INVENTORY_NAME))  return;
        event.setCancelled(true);
        int slot = event.getSlot();
        Player player = (Player) event.getWhoClicked();
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null) {
            player.sendMessage("§cYou don't have a profile!");
            return;
        }
        switch (slot) {
            case (IRON_SLOT) -> {
                plugin.getGameManager().setDifficulty(GameDifficulty.IRON);
                plugin.getRoundManager().startGame();
                player.closeInventory();
            }
            case (GOLD_SLOT) -> {
                plugin.getGameManager().setDifficulty(GameDifficulty.GOLD);
                plugin.getRoundManager().startGame();
                player.closeInventory();
            }
            case (DIAMOND_SLOT) -> {
                plugin.getGameManager().setDifficulty(GameDifficulty.DIAMOND);
                plugin.getRoundManager().startGame();
                player.closeInventory();
            }
            case (NETHERITE_SLOT) -> {
                plugin.getGameManager().setDifficulty(GameDifficulty.NETHERITE);
                plugin.getRoundManager().startGame();
                player.closeInventory();
            }
        }


    }

}
