package io.github.greatericontop.thedark.enemy.zombies;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class MilitantZombie extends BaseEnemy {

    public MilitantZombie(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE, false);
        setUp(30.0, 0.2645); // 50% extra health, 15% faster
        entity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD, 1));
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 40;
    }

}
