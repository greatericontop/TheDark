package io.github.greatericontop.thedark.enemy.zombies;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class HelmetZombie extends BaseEnemy {

    public HelmetZombie(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE, false);
        setUp(30.0, 0.253); // 50% extra health, 10% faster
        entity.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 25;
    }

}
