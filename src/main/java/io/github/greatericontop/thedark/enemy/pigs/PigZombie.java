package io.github.greatericontop.thedark.enemy.pigs;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class PigZombie extends BaseEnemy {

    public PigZombie(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIFIED_PIGLIN, false);
        setUp(25.0, 0.23); // 25% extra health, same speed
        entity.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET, 1));
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD, 1));
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 20;
    }

}
