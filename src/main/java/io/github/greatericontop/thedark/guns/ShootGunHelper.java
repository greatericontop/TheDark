package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.miscmechanic.FireStatus;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShootGunHelper {
    public static final double MAX_DISTANCE = 48.0;
    public static final double EXPLOSION_MAX_DISTANCE = 20.0;
    private static final double SEVERE_FIRE_MULTIPLIER = 1.25;

    private record Hit(LivingEntity target, double distance) {}

    public static void fireProjectile(Location sourceLoc, Vector direction, Player owner, double damage, int pierce, TheDark plugin,
                                      double extraKBStrength, boolean bypassDamageTicks, double rayDistance, @Nullable FireStatus fireStatus,
                                      Particle customParticle, @Nullable Sound customSound,
                                      @Nullable PotionEffect potionEffect) {
        direction = direction.normalize();
        Location start = sourceLoc.clone();
        Location end = start.clone().add(direction.clone().multiply(rayDistance));
        List<Hit> hits = new ArrayList<>();
        BoundingBox boundingBox = BoundingBox.of(start, end);
        for (Entity e : start.getWorld().getNearbyEntities(boundingBox)) {
            if (e instanceof Player)  continue;
            if (!(e instanceof LivingEntity target))  continue;
            RayTraceResult hit = e.getBoundingBox().rayTrace(start.toVector(), direction, rayDistance);
            if (hit == null)  continue;
            // Vector from start to the position where we touch the entity's bounding box
            Vector delta = hit.getHitPosition().subtract(start.toVector());
            double distance = delta.length();
            hits.add(new Hit(target, distance));
        }
        hits.sort(Comparator.comparingDouble(a -> a.distance));

        if (bypassDamageTicks) {
            for (int i = 0; i < Math.min(pierce, hits.size()); i++) {
                LivingEntity target = hits.get(i).target;
                // Multiple shots in the same tick deal 0.9x damage
                target.setLastDamage(damage * 0.1);
            }
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (int i = 0; i < Math.min(pierce, hits.size()); i++) {
                LivingEntity target = hits.get(i).target;
                target.setNoDamageTicks(0);
            }
        }, 1L);

        for (int i = 0; i < Math.min(pierce, hits.size()); i++) {
            LivingEntity target = hits.get(i).target;
            if (target.getPersistentDataContainer().has(FireStatus.SEVERE_FIRE_KEY)) {
                target.damage(damage * SEVERE_FIRE_MULTIPLIER, owner);
            } else {
                target.damage(damage, owner);
            }
            if (fireStatus != null) {
                // TODO: This can probably be made O(1) rather than O(N)
                for (BaseEnemy enemy : plugin.getGameManager().activeEnemies) {
                    if (enemy.getEntity().getUniqueId().equals(target.getUniqueId())) {
                        enemy.applyFire(fireStatus);
                    }
                }
            }
            if (extraKBStrength > 0.0) {
                Vector knockback = direction.clone().multiply(extraKBStrength);
                target.setVelocity(target.getVelocity().add(knockback));
            }
            if (potionEffect != null) {
                target.addPotionEffect(potionEffect);
            }
        }

        // FX
        Vector totalDelta = end.toVector().subtract(start.toVector());
        Vector step = totalDelta.clone().normalize().multiply(0.2);
        Location current = start.clone();
        for (int i = 0; i < (int) (totalDelta.length() / 0.2); i++) {
            current.add(step);
            current.getWorld().spawnParticle(customParticle, current, 1, 0.0, 0.0, 0.0, 0.0);
        }
        if (customSound != null) {
            sourceLoc.getWorld().playSound(sourceLoc, customSound, 0.45F, 1.0F);
        }
    }

    public static void fireExplosionProjectile(Location sourceLoc, Vector direction, Player owner, double damage, int pierce, TheDark plugin,
                                               double rayDistance, double explosionRadius, boolean secondaryExplosions) {
        direction = direction.normalize();
        // Find location where ray terminates
        RayTraceResult hit = sourceLoc.getWorld().rayTrace(sourceLoc, direction, rayDistance, FluidCollisionMode.NEVER, true, 0.08, e -> !(e instanceof Player));
        Location endLoc = sourceLoc.clone().add(direction.multiply(rayDistance));
        if (hit != null) {
            endLoc = hit.getHitPosition().toLocation(sourceLoc.getWorld());
        }
        // Explosions have limited pierce, so manually find targets and sort by distance
        if (secondaryExplosions) {
            for (int i = 0; i < 4; i++) {
                // 90 degrees apart
                Vector modifier = new Vector(direction.getX(), 0.0, direction.getZ()).normalize().rotateAroundY(1.570796 * i).multiply(explosionRadius * 0.7);
                performExplosion(endLoc.clone().add(modifier), explosionRadius, owner, pierce, damage);
            }
        } else {
            performExplosion(endLoc, explosionRadius, owner, pierce, damage);
        }
        // FX
        owner.playSound(endLoc, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
        Vector totalDelta = endLoc.toVector().subtract(sourceLoc.toVector());
        Vector step = totalDelta.clone().normalize().multiply(0.2);
        Location current = sourceLoc.clone();
        for (int i = 0; i < (int) (totalDelta.length() / 0.2); i++) {
            current.add(step);
            current.getWorld().spawnParticle(Particle.ASH, current, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }
    private static void performExplosion(Location endLoc, double explosionRadius, Player owner, int pierce, double damage) {
        List<Hit> hits = new ArrayList<>();
        for (Entity e : endLoc.getWorld().getNearbyEntities(endLoc, explosionRadius, explosionRadius, explosionRadius)) {
            if (e instanceof Player)  continue;
            if (!(e instanceof LivingEntity target))  continue;
            double distanceSq = e.getLocation().distanceSquared(endLoc);
            if (distanceSq > explosionRadius * explosionRadius)  continue;
            hits.add(new Hit(target, distanceSq));
        }
        hits.sort(Comparator.comparingDouble(a -> a.distance));
        for (int i = 0; i < Math.min(pierce, hits.size()); i++) {
            LivingEntity target = hits.get(i).target;
            if (target.getPersistentDataContainer().has(FireStatus.SEVERE_FIRE_KEY)) {
                target.damage(damage * SEVERE_FIRE_MULTIPLIER, owner);
            } else {
                target.damage(damage, owner);
            }
        }
        endLoc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, endLoc, 1);
    }

}
