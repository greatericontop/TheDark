package io.github.greatericontop.thedark.miscmechanic;

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

import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

public class FireStatus {
    public static final int FIRE_DAMAGE_INTERVAL = 20;
    public static final NamespacedKey SEVERE_FIRE_KEY = new NamespacedKey("thedark", "severe_fire");

    public PlayerProfile playerProfile;
    public int durationLeft;
    public double damagePerSecond;
    public int ticksToDamage;
    public boolean isSevere;

    public FireStatus(PlayerProfile playerProfile, int durationLeft, double damagePerSecond, boolean isSevere) {
        this.playerProfile = playerProfile;
        this.durationLeft = durationLeft;
        this.damagePerSecond = damagePerSecond;
        this.ticksToDamage = FIRE_DAMAGE_INTERVAL;
        this.isSevere = isSevere;
    }

    public void placeSeverity(LivingEntity entity) {
        if (isSevere) {
            entity.getPersistentDataContainer().set(SEVERE_FIRE_KEY, PersistentDataType.INTEGER, 1);
        }
    }
    public void removeSeverity(LivingEntity entity) {
        entity.getPersistentDataContainer().remove(SEVERE_FIRE_KEY);
    }

}
