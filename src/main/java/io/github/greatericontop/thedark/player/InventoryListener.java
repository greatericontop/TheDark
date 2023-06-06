package io.github.greatericontop.thedark.player;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryListener implements Listener {
    private final TheDark plugin;

    public InventoryListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event) {
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(event.getWhoClicked().getUniqueId());
        if (profile == null)  return; // don't do anything to players not involved in the game
        if (event.getClickedInventory().equals(profile.getPlayer().getInventory())) {
            // don't allow player to drop/click/move items in their own inventory
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onDropItem(PlayerDropItemEvent event) {
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(event.getPlayer().getUniqueId());
        if (profile != null) {
            event.setCancelled(true);
        }
    }

}
