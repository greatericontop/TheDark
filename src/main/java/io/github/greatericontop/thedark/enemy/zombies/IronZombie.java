package io.github.greatericontop.thedark.enemy.zombies;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class IronZombie extends BaseEnemy {

    public IronZombie(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE, false);
        setUp(200.0, 1.4, 5.0);
        entity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD, 1)); // sword adds +5 damage for total of 10
    }

}
