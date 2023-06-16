package io.github.greatericontop.thedark.menus;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.util.RouletteLootTable;
import io.github.greatericontop.thedark.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class RouletteRewardClaimListener implements Listener {
    public static final Component INVENTORY_NAME = Component.text("§c[TheDark] §2Roulette §aReward");
    private static final NamespacedKey WIN_KEY = new NamespacedKey("thedark", "win_key");

    private final TheDark plugin;
    public RouletteRewardClaimListener(TheDark plugin) {
        this.plugin = plugin;
    }

    public void openMenu(PlayerProfile profile, int winKey) {
        Player player = profile.getPlayer();
        Inventory gui = Bukkit.createInventory(player, 9, INVENTORY_NAME);

        ItemStack winKeyStorageStack = new ItemStack(Material.EMERALD, 1);
        winKeyStorageStack.editMeta(im -> im.getPersistentDataContainer().set(WIN_KEY, PersistentDataType.INTEGER, winKey));
        gui.setItem(0, winKeyStorageStack);
        gui.setItem(4, RouletteLootTable.getIcon(winKey));
        gui.setItem(8, Util.createItemStack(Material.ENDER_CHEST, 1,
                "§aCLAIM REWARD",
                "§fYou received a reward from the roulette wheel!",
                "",
                "§dPress a hotkey to one of your gun slots to place this gun in your inventory.",
                "§7A gun already in that slot will be replaced.",
                "",
                "§7Since this is a rare item, you must pay to claim it.",
                String.format("§fCost: §6%,d coins", RouletteLootTable.getCost(winKey))
        ));

        player.openInventory(gui);
    }

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().title().equals(INVENTORY_NAME))  return;
        event.setCancelled(true);
        int slot = event.getSlot();
        if (slot != 8)  return;
        Player player = (Player) event.getWhoClicked();
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null) {
            player.sendMessage("§cYou don't have a profile!");
            return;
        }
        if (event.getClick() != ClickType.NUMBER_KEY) {
            player.sendMessage("§cYou need to press a hotkey (hotbar button)!");
            return;
        }
        int winKey = event.getInventory().getItem(0).getItemMeta().getPersistentDataContainer().get(WIN_KEY, PersistentDataType.INTEGER);
        if (profile.coins < RouletteLootTable.getCost(winKey)) {
            player.sendMessage("§cYou can't afford this!");
            Util.playFailSound(player);
            return;
        }

        RouletteLootTable.runClaim(winKey, profile, player, event.getHotbarButton());
    }

}
