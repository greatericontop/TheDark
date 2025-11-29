package io.github.greatericontop.thedark.miscmechanic;

public enum GameDifficulty {
    IRON(0.9, 28.0, true),
    GOLD(1.0, 24.0, true),
    DIAMOND(1.05, 20.0, true),
    NETHERITE(1.05, 20.0, false),

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

}
