package io.github.greatericontop.thedark.menus;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.util.Util;
import io.github.greatericontop.thedark.player.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ArmorBuyListener extends GenericMenu {
    public static final Component INVENTORY_NAME = Component.text("§c[TheDark] §bArmor Upgrades");
    private static final int NUMBER_UPGRADES = 3;
    private static final String[] NAMES = {
            "§7Leather Armor",
            "§fChain Armor",
            "§f§lIron Armor",
    };
    private static final int[] COSTS = {
            500,
            1000,
            1500
    };
    public static final Material[][] ARMOR_MATERIALS = {
            {Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS},
            {Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS},
            {Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS},
    };

    private final TheDark plugin;
    public ArmorBuyListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @Override
    public void openMenu(PlayerProfile profile) {
        Player player = profile.getPlayer();
        Inventory gui = Bukkit.createInventory(player, 9, INVENTORY_NAME);

        int lvl = profile.armorLevel;

        for (int i = 0; i < NUMBER_UPGRADES; i++) {
            Material mat = (lvl == i) ? Material.LIGHT_BLUE_STAINED_GLASS : (lvl > i) ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
            gui.setItem(i, Util.createItemStack(mat, 1,
                    NAMES[i],
                    String.format("§7Cost: §6%,d", COSTS[i])));
        }

        player.openInventory(gui);
    }

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().title().equals(INVENTORY_NAME))  return;
        event.setCancelled(true);
        int slot = event.getSlot();
        if (slot >= NUMBER_UPGRADES)  return;
        Player player = (Player) event.getWhoClicked();
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null) {
            player.sendMessage("§cYou don't have a profile!");
            return;
        }
        int currentLevel = profile.armorLevel;
        if (slot > currentLevel) {
            player.sendMessage("§cYou need to buy the previous upgrade first!");
            Util.playFailSound(player);
            return;
        }
        if (slot < currentLevel) {
            player.sendMessage("§cYou already have this upgrade!");
            Util.playFailSound(player);
            return;
        }
        int cost = COSTS[slot];
        if (profile.coins < cost) {
            player.sendMessage("§cYou can't afford this!");
            Util.playFailSound(player);
            return;
        }
        profile.coins -= cost;
        profile.armorLevel++;
        player.sendMessage("§aYou have successfully upgraded your armor!");
        player.closeInventory();
        Util.playSuccessSound(player);
    }

}
