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

public enum GameDifficulty {
    IRON(0.9, 28.0, true),
    GOLD(1.0, 24.0, true),
    DIAMOND(1.07, 20.0, true),
    NETHERITE(1.07, 20.0, false),

    ;

    private final double costMultiplier;
    private final double playerMaxHealth;
    private final boolean naturalRegeneration;

    GameDifficulty(double costMultiplier, double playerMaxHealth, boolean naturalRegeneration) {
        this.costMultiplier = costMultiplier;
        this.playerMaxHealth = playerMaxHealth;
        this.naturalRegeneration = naturalRegeneration;
    }

    public double getCostMultiplier() {
        return costMultiplier;
    }
    public double getPlayerMaxHealth() {
        return playerMaxHealth;
    }
    public boolean hasNaturalRegeneration() {
        return naturalRegeneration;
    }

    public static int getAdjustedCost(int stdCost, GameDifficulty difficulty) {
        return (int) (Math.round(stdCost * difficulty.getCostMultiplier() / 5)) * 5;
    }

}
