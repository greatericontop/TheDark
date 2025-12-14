package io.github.greatericontop.thedark.enemy.zombies;

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
import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class ZombieVillagerBaby extends BaseEnemy {

    public ZombieVillagerBaby(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE_VILLAGER, false);
        setUp(400.0, 1.1, 20.0);
        entity.getEquipment().setHelmet(new ItemStack(Material.DRAGON_HEAD, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
        ((org.bukkit.entity.ZombieVillager) entity).setBaby();
    }

    @Override
    public void extraDeathEvent(TheDark plugin, PlayerProfile killerProfile) {
        plugin.getGameManager().spawnEnemy(IronZombie.class, entity.getLocation());
    }

}
