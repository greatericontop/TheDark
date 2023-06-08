package io.github.greatericontop.thedark.player;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.guns.GunUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerShennaniganPreventionListener implements Listener {
    private final TheDark plugin;

    public PlayerShennaniganPreventionListener(TheDark plugin) {
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
        if (profile == null)  return;
        // if the item being dropped is a gun (has the pdc), just delete it rather than trying to cancel the event
        // this prevents the bug that separates out the stack, and you'll only lose 1 ammo slot
        // however, if you only have 1 left... you're screwed
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        ItemMeta droppedIM = droppedItem.getItemMeta();
        if (droppedIM != null && droppedIM.getPersistentDataContainer().has(GunUtil.GUN_KEY, PersistentDataType.STRING)) {
            event.getItemDrop().remove();
            event.getPlayer().sendMessage("§7DEBUG: deleting item drop rather than cancelling event");
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onRightClickSignWithLightGrayDye(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)  return;
        if (event.getClickedBlock().getType() != Material.OAK_SIGN && event.getClickedBlock().getType() != Material.OAK_WALL_SIGN)  return;
        if (event.getItem() == null || event.getItem().getType() != Material.LIGHT_GRAY_DYE)  return;
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(event.getPlayer().getUniqueId());
        if (profile != null) {
            event.setCancelled(true);
        }
    }

}
