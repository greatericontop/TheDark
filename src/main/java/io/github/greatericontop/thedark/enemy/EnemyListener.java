package io.github.greatericontop.thedark.enemy;

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
import io.github.greatericontop.thedark.miscmechanic.CashGeneration;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class EnemyListener implements Listener {
    public static final NamespacedKey NO_DROP_ITEMS = new NamespacedKey("thedark", "no_drop_items");

    private final TheDark plugin;
    public EnemyListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getPersistentDataContainer().has(NO_DROP_ITEMS, PersistentDataType.INTEGER)) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }

    @EventHandler()
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))  return;
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null)  return;
        if (!(event.getEntity() instanceof LivingEntity target))  return;
        double effectiveDamage = Math.min(event.getFinalDamage(), target.getHealth());
        CashGeneration.rewardCoinsOnDamage(profile, effectiveDamage, plugin);
    }

}
