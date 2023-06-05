package io.github.greatericontop.thedark.guns;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class GunUtil implements Listener {
    public final static NamespacedKey GUN_KEY = new NamespacedKey("thedark", "gun");
    private final static NamespacedKey DAMAGE_KEY = new NamespacedKey("thedark", "arrow_damage");

    public static void fireProjectile(GunType gunType, Location source, Vector direction, Player owner, double damage) {
        Arrow arrow = (Arrow) source.getWorld().spawnEntity(source, EntityType.ARROW);
        arrow.setVelocity(direction.normalize().multiply(15.0));
        arrow.getPersistentDataContainer().set(GUN_KEY, PersistentDataType.STRING, gunType.name()); // shows what gun it came from
        arrow.getPersistentDataContainer().set(DAMAGE_KEY, PersistentDataType.DOUBLE, damage);
        arrow.setShooter(owner);
    }

    @EventHandler()
    public void onArrowLand(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow))  return;
        if (!(arrow.getShooter() instanceof Player shooter))  return;
        if (!arrow.getPersistentDataContainer().has(DAMAGE_KEY, PersistentDataType.DOUBLE))  return;
        double damage = arrow.getPersistentDataContainer().get(DAMAGE_KEY, PersistentDataType.DOUBLE);

        arrow.remove();
        if (event.getHitEntity() instanceof LivingEntity hitTarget) {
            hitTarget.damage(damage, shooter);
        }
    }

}
