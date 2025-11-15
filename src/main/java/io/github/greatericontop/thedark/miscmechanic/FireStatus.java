package io.github.greatericontop.thedark.miscmechanic;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

public class FireStatus {
    public static final int FIRE_DAMAGE_INTERVAL = 20;
    public static final NamespacedKey SEVERE_FIRE_KEY = new NamespacedKey("thedark", "severe_fire");

    public int durationLeft;
    public double damagePerSecond;
    public int ticksToDamage;
    public boolean isSevere;

    public FireStatus(int durationLeft, double damagePerSecond, boolean isSevere) {
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
