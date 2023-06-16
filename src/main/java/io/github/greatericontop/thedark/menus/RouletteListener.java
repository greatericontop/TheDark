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

import java.util.concurrent.ThreadLocalRandom;

public class RouletteListener extends GenericMenu {
    public static final Component INVENTORY_NAME = Component.text("§c[TheDark] §2Roulette Wheel");

    private final TheDark plugin;
    public RouletteListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @Override
    public void openMenu(PlayerProfile profile) {
        Player player = profile.getPlayer();
        Inventory gui = Bukkit.createInventory(player, 9, INVENTORY_NAME);

        String lore1, lore2;
        if (profile.emeralds == 0) {
            lore1 = "§cYou don't have any emeralds!";
            lore2 = "§fYou can get an emerald by killing a vindicator.";
        } else {
            lore1 = "§dSpin the roulette wheel for a chance to get powerful loot!";
            lore2 = "§fCost: §21 Emerald";
        }

        gui.setItem(4, Util.createItemStack(Material.END_PORTAL_FRAME, 1, "§aRoluette Spin", lore1, lore2));

        player.openInventory(gui);
    }

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().title().equals(INVENTORY_NAME))  return;
        event.setCancelled(true);
        int slot = event.getSlot();
        if (slot != 4)  return;
        Player player = (Player) event.getWhoClicked();
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null) {
            player.sendMessage("§cYou don't have a profile!");
            return;
        }
        if (profile.emeralds == 0) {
            player.sendMessage("§cYou can't afford this!");
            Util.playFailSound(player);
            return;
        }
        profile.emeralds -= 1;
        player.closeInventory();
        int number = ThreadLocalRandom.current().nextInt(3);
        plugin.rouletteRewardClaimListener.openMenu(profile, number); // TODO: DEBUG
//        if (number < 4) { // weight 4
//            player.sendMessage("§cSorry, you got nothing.");
//            Util.playFailSound(player);
//        } else if (number < 8) { // weight 4
//            player.sendMessage("§aYou received §6500 coins§a!");
//            profile.coins += 500;
//            Util.playSuccessSound(player);
//        } else if (number < 11) { // weight 3
//            player.sendMessage("§aYou received §61,000 coins§a!");
//            profile.coins += 1000;
//            Util.playSuccessSound(player);
//        } else if (number < 14) { // weight 3
//            player.sendMessage("§aYou received §62,000 coins§a!");
//            profile.coins += 2000;
//            Util.playSuccessSound(player);
//        } else {
//            player.sendMessage("§cPlaceholder. You should get a weapon.");
//            plugin.rouletteRewardClaimListener.openMenu(profile, number - 14);
//            Util.playSuccessSound(player);
//        }
    }

}
