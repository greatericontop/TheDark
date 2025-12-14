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
import io.github.greatericontop.thedark.enemy.EnragedEnemy;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MegaPiglinBrute extends EnragedEnemy {

    public MegaPiglinBrute(Location spawnLocation) {
        super(1200);
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.PIGLIN_BRUTE, false);
        setUp(10000.0, 1.175, 300.0);
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_AXE, 1));
        ((org.bukkit.entity.PiglinBrute) entity).setImmuneToZombification(true);
        // TODO: 1.21 api for increased size
    }

    @Override
    public void extraDeathEvent(TheDark plugin, PlayerProfile killerProfile) {
        for (int i = 0; i < 2; i++) {
            plugin.getGameManager().spawnEnemy(PiglinBrute.class, entity.getLocation());
        }
    }

    @Override
    protected void enrageSelf() {
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1.0F, 1.0F);
        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.23 * 2.0);
        for (Entity e : entity.getNearbyEntities(100.0, 100.0, 100.0)) {
            if (e instanceof Player p) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 100, 0));
            }
        }
    }

}
