package io.github.greatericontop.thedark.guns;

public enum GunType {

    PISTOL(4.0, 8L),

    ;

    private final double damage;
    private final long cooldownTicks;

    public double getDamage() {
        return damage;
    }
    public long getCooldownTicks() {
        return cooldownTicks;
    }

    GunType(double damage, long cooldownTicks) {
        this.damage = damage;
        this.cooldownTicks = cooldownTicks;
    }
}
