package io.github.greatericontop.thedark.miscmechanic;

public class FireStatus {
    public static final int FIRE_DAMAGE_INTERVAL = 20;

    public int durationLeft;
    public double damagePerSecond;
    public int ticksToDamage;

    public FireStatus(int durationLeft, double damagePerSecond) {
        this.durationLeft = durationLeft + 1; // so 20 ticks actually sets 21 (otherwise no damage would be applied)
        this.damagePerSecond = damagePerSecond;
        this.ticksToDamage = FIRE_DAMAGE_INTERVAL;
    }

}
