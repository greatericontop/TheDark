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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerDropItemListener implements Listener {

    private final TheDark plugin;
    public PlayerDropItemListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onDropItem(PlayerDropItemEvent event) {
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(event.getPlayer().getUniqueId());
        if (profile == null)  return;
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        ItemMeta droppedIM = droppedItem.getItemMeta();
        if (droppedIM == null || !droppedIM.getPersistentDataContainer().has(GunUtil.GUN_KEY, PersistentDataType.STRING)) {
            event.setCancelled(true);
            return;
        }

        // The event is called AFTER the item is dropped.
        // If the item being dropped is a gun (has the pdc), just delete it rather than trying to cancel the event.
        // If there's only 1 left, we cancel the event normally. To check, we see if the dropped gun is no longer in
        // the first 3 slots of the inventory.
        // In both cases, drop is an early reload (set durability damage)

        ItemStack toModify;
        if (droppedItem.isSimilar(profile.getPlayer().getInventory().getItem(1))
                || droppedItem.isSimilar(profile.getPlayer().getInventory().getItem(2))
                || droppedItem.isSimilar(profile.getPlayer().getInventory().getItem(3))) {
            event.getItemDrop().remove();
            toModify = profile.getPlayer().getInventory().getItemInMainHand();

        } else {
            event.setCancelled(true);
            // Slightly hacky: the server places back the dropped item, but we can modify what it places back
            toModify = event.getItemDrop().getItemStack();
        }

        if (toModify.isSimilar(droppedItem)) {
            Damageable im = (Damageable) toModify.getItemMeta();
            im.setDamage(toModify.getType().getMaxDurability() - 1);
            toModify.setItemMeta(im);
            toModify.setAmount(1);
        }
    }

}
