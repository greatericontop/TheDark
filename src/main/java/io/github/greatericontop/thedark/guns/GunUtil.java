package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GunUtil implements Listener {
    public static final NamespacedKey GUN_KEY = new NamespacedKey("thedark", "gun");
    private static final double MAX_DISTANCE = 48.0;

    private record Hit(LivingEntity target, double distance) {}

    private final TheDark plugin;
    public GunUtil(TheDark plugin) {
        this.plugin = plugin;
    }

    public static void fireProjectile(Location sourceLoc, Vector direction, Player owner, double damage, int pierce, TheDark plugin) {
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
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (int i = 0; i < Math.min(pierce, hits.size()); i++) {
                LivingEntity target = hits.get(i).target;
                target.setNoDamageTicks(0);
            }
        }, 1L);
        for (int i = 0; i < Math.min(pierce, hits.size()); i++) {
            LivingEntity target = hits.get(i).target;
            target.damage(damage, owner);
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

    public static int getDamagePositionBelowCurrent(int maxDurability, int ticksToRefill, int currentDamage) {
        // we subtract 0.5 here because the initial damage will be :maxDurability - 1:, so
        // then it will successfully go down to the next one on the next tick
        double step = (maxDurability - 0.5) / ((double) ticksToRefill);
        // iterative approach is slow but good enough
        for (double damageAmount = 0.0; damageAmount < maxDurability; damageAmount += step) {
            // (add compensation for floating point problems, with slightly more for the first one)
            if (damageAmount + 0.0000015 >= currentDamage) {
                return (int) (damageAmount - step + 0.000001);
            }
        }
        throw new RuntimeException("currentDamage is greater than last value of damageAmount (" + currentDamage + ")");
    }

    public static @Nullable GunClassification getHeldGunClassification(Player player) {
        ItemStack stack = player.getInventory().getItemInMainHand();
        ItemMeta im = stack.getItemMeta();
        if (im == null)  return null;
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        if (pdc.has(GunUtil.GUN_KEY, PersistentDataType.STRING)) {
            return GunClassification.valueOf(pdc.get(GunUtil.GUN_KEY, PersistentDataType.STRING));
        }
        return null;
    }

    @EventHandler()
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))  return;
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null)  return;
        double multiplier = 0.8;
        if (getHeldGunClassification(player) == GunClassification.MIDAS_PISTOL) {
            multiplier *= 3;
        }
        profile.coins += Util.roundNumber(event.getFinalDamage() * multiplier);
    }

}
