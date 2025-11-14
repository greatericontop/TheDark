package io.github.greatericontop.thedark.enemy;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.Color;
import org.bukkit.Particle;

public abstract class EnragedEnemy extends BaseEnemy {
    private boolean isEnraged;
    private int rageTimerMax;
    private int rageTimer;

    public EnragedEnemy(int rageTimer) {
        isEnraged = false;
        this.rageTimerMax = rageTimer;
        this.rageTimer = rageTimer;
    }

    @Override
    public void tick(TheDark plugin) {
        super.tick(plugin);

        if ((!isEnraged) && rageTimer > 0) {
            rageTimer--;
            if (rageTimer == 0) {
                isEnraged = true;
                enrageSelf();
            } else if (rageTimer < rageTimerMax/2) {
                double greenness = (double) rageTimer / (rageTimerMax/2);
                Particle.DustOptions options = new Particle.DustOptions(Color.fromRGB(255, (int) (255 * greenness), 0), 1);
                entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation().add(0, 0.35, 0), 3, options);
            }
        }
    }

    protected abstract void enrageSelf();

}
