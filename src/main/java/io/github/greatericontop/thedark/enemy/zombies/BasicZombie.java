package io.github.greatericontop.thedark.enemy.zombies;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class BasicZombie extends BaseEnemy {

    public BasicZombie(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE, false);
        setUp(20.0, 0.23); // same as vanilla
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 15;
    }

}
