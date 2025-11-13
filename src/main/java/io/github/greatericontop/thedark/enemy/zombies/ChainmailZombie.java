package io.github.greatericontop.thedark.enemy.zombies;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class ChainmailZombie extends BaseEnemy {

    public ChainmailZombie(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE, false);
        setUp(100.0, 1.3, 6.0);
        entity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
    }

}
