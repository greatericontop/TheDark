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
import org.bukkit.Color;
import org.bukkit.Particle;

public abstract class EnragedEnemy extends BaseEnemy {
    private boolean isEnraged;
    private int rageTimerMax;
    private int rageTimer;

    public EnragedEnemy(int rageTimer) {
        isEnraged = false;
        this.rageTimerMax = rageTimer;
        this.rageTimer = rageTimer;
    }

    @Override
    public void tick(TheDark plugin) {
        super.tick(plugin);

        if ((!isEnraged) && rageTimer > 0) {
            rageTimer--;
            if (rageTimer == 0) {
                isEnraged = true;
                enrageSelf();
            } else if (rageTimer < rageTimerMax/2) {
                double greenness = (double) rageTimer / (rageTimerMax/2);
                Particle.DustOptions options = new Particle.DustOptions(Color.fromRGB(255, (int) (255 * greenness), 0), 1);
                entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation().add(0, 0.35, 0), 3, options);
            }
        }
    }

    protected abstract void enrageSelf();

}
