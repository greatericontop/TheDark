package io.github.greatericontop.thedark.enemy;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public abstract class BaseEnemy {
    protected LivingEntity entity;

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

    public void extraDeathEvent(TheDark plugin, PlayerProfile killerProfile) {
        // This is used to add extra behavior when this enemy's entity dies
        // By default, do nothing
    }

}
