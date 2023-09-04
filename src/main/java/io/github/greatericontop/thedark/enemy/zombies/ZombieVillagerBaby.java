package io.github.greatericontop.thedark.enemy.zombies;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ZombieVillager;

public class ZombieVillagerBaby extends BaseEnemy {

    public ZombieVillagerBaby(Location spawnLocation) {
        ZombieVillager zombieVillager = (ZombieVillager) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE_VILLAGER, false);
        zombieVillager.setBaby();
        entity = zombieVillager;
        setUp(12.0, 0.23); // TODO: does using vanilla speed cancel the baby's movement bonus?
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 10;
    }

}
