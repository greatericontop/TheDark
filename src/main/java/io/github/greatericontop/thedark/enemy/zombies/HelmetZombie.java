package io.github.greatericontop.thedark.enemy.zombies;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.miscmechanic.FireStatus;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class HelmetZombie extends BaseEnemy {

    public HelmetZombie(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE, false);
        setUp(40.0, 1.2, 4.5);
        entity.getEquipment().setHelmet(new ItemStack(Material.TURTLE_HELMET, 1));
        this.fireStatus = new FireStatus(200, 3.9); // DEBUG
    }

}
