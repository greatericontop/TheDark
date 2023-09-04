package io.github.greatericontop.thedark.enemy.other;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class FatDebugZombie extends BaseEnemy {

    public FatDebugZombie(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE, false);
        setUp(400.0, 0.115);
        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 300;
    }

}
