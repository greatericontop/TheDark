package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShootGunHelper {
    private static final double MAX_DISTANCE = 48.0;

    private record Hit(LivingEntity target, double distance) {}

    public static void fireProjectile(Location sourceLoc, Vector direction, Player owner, double damage, int pierce, TheDark plugin,
                                      double extraKBStrength, boolean bypassDamageTicks) {
        direction = direction.normalize();
        Location start = sourceLoc.clone();
        Location end = start.clone().add(direction.clone().multiply(MAX_DISTANCE));
        List<Hit> hits = new ArrayList<>();
        BoundingBox boundingBox = BoundingBox.of(start, end);
        for (Entity e : start.getWorld().getNearbyEntities(boundingBox)) {
            if (e instanceof Player)  continue;
            if (!(e instanceof LivingEntity target))  continue;
            RayTraceResult hit = e.getBoundingBox().rayTrace(start.toVector(), direction, MAX_DISTANCE);
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
                // Multiple shots in the same tick deal damage but only 0.6x damage
                target.setLastDamage(damage * 0.4);
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
            target.damage(damage, owner);
            if (extraKBStrength > 0.0) {
                Vector knockback = direction.clone().multiply(extraKBStrength);
                target.setVelocity(target.getVelocity().add(knockback));
            }
        }

        // FX
        Vector totalDelta = end.toVector().subtract(start.toVector());
        Vector step = totalDelta.clone().normalize().multiply(0.2);
        Location current = start.clone();
        for (int i = 0; i < (int) (totalDelta.length() / 0.2); i++) {
            current.add(step);
            current.getWorld().spawnParticle(Particle.ASH, current, 1, 0.0, 0.0, 0.0, 0.0);
        }
        sourceLoc.getWorld().playSound(sourceLoc, Sound.ENTITY_GENERIC_EXPLODE, 0.45F, 1.0F);
    }
}
