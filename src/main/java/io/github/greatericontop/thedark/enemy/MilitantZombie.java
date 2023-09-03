package io.github.greatericontop.thedark.enemy;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class MilitantZombie extends BaseEnemy {

    public MilitantZombie(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE, false);
        setUp(35.0, 0.2645); // 75% extra health, 15% faster
        entity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD, 1));
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 40;
    }

}
