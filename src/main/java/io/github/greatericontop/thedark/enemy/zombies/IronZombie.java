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

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class IronZombie extends BaseEnemy {

    public IronZombie(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE, false);
        setUp(160.0, 1.35, 5.0);
        entity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD, 1)); // sword adds +5 damage for total of 10
    }

}
