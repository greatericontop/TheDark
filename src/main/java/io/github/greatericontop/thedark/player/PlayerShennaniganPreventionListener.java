package io.github.greatericontop.thedark.player;

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

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.guns.GunUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

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

    @EventHandler()
    public void onOffhandSwap(PlayerSwapHandItemsEvent event) {
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(event.getPlayer().getUniqueId());
        if (profile != null) {
            if (event.getOffHandItem() != null && event.getMainHandItem() != null
                    && event.getOffHandItem().getType() == event.getMainHandItem().getType()) {
                // if the offhand and mainhand items are the same type, most likely our plugin reset the item in
                // the main hand before the event was called (and the item was duplicated)
                event.setOffHandItem(null);
            } else {
                event.setCancelled(true);
            }
        }
    }

    // refresh profiles when player rejoins
    @EventHandler()
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (plugin.getGameManager().playerProfiles.containsKey(uuid)) {
            plugin.getGameManager().playerProfiles.get(uuid).setPlayer(event.getPlayer());
            event.getPlayer().sendMessage("§3Your profile was refreshed!");
        }
    }

}
