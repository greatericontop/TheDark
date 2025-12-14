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
import io.github.greatericontop.thedark.miscmechanic.FireStatus;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public abstract class BaseEnemy {
    protected LivingEntity entity;
    protected FireStatus fireStatus = null;

    public LivingEntity getEntity() {
        return entity;
    }

    public boolean isDead() {
        return entity.isDead();
    }

    protected void setUp(double maxHealth, double speedScale, double attackDamage) {
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);
        entity.getAttribute(Attribute.GENERIC_ARMOR).addModifier(new AttributeModifier(UUID.randomUUID(), "thedark_no_armor", -100.0, AttributeModifier.Operation.ADD_NUMBER));
        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.23 * speedScale);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(attackDamage);
        entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(80.0);
        entity.getPersistentDataContainer().set(EnemyListener.NO_DROP_ITEMS, PersistentDataType.INTEGER, 1);
        entity.setPersistent(true);
    }

    public void applyFire(FireStatus newFireStatus) {
        // A severe status is never overwritten, but the only time it is used it has infinite duration, so this is fine
        if (fireStatus == null || ((!fireStatus.isSevere) && newFireStatus.damagePerSecond > fireStatus.damagePerSecond)
                || ((!fireStatus.isSevere) && newFireStatus.damagePerSecond == fireStatus.damagePerSecond && newFireStatus.durationLeft > fireStatus.durationLeft)) {
            newFireStatus.placeSeverity(entity);
            this.fireStatus = newFireStatus;
        }
    }

    public void tick(TheDark plugin) {
        // This is executed each tick, this implementation contains some common behavior (fire)

        if (fireStatus != null) {
            entity.setVisualFire(true);
            fireStatus.durationLeft--;
            fireStatus.ticksToDamage--;
            if (fireStatus.durationLeft <= 0) {
                entity.setVisualFire(false);
                fireStatus.removeSeverity(entity);
                fireStatus = null;
            } else if (fireStatus.ticksToDamage <= 0) { // If both hit 0 the same tick, no damage is applied
                entity.damage(fireStatus.damagePerSecond);
                CashGeneration.rewardCoinsOnDamage(fireStatus.playerProfile, fireStatus.damagePerSecond, plugin);
                Bukkit.getScheduler().runTaskLater(plugin, () -> entity.setNoDamageTicks(0), 1L);
                fireStatus.ticksToDamage = FireStatus.FIRE_DAMAGE_INTERVAL;
            }
        }
    }

    public void extraDeathEvent(TheDark plugin, PlayerProfile killerProfile) {
        // This is used to add extra behavior when this enemy's entity dies
        // By default, do nothing
    }

}
