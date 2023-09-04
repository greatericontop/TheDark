package io.github.greatericontop.thedark.enemy.other;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class EmeraldVindicator extends BaseEnemy {

    public EmeraldVindicator(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.VINDICATOR, false);
        setUp(48.0, 0.35); // 2x health, speed same as vanilla
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE, 1));
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 100;
    }

}
