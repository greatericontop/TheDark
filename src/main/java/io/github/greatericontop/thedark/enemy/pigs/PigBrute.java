package io.github.greatericontop.thedark.enemy.pigs;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.inventory.ItemStack;

public class PigBrute extends BaseEnemy {

    public PigBrute(Location spawnLocation) {
        PiglinBrute pigBrute = (PiglinBrute) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.PIGLIN_BRUTE, false);
        pigBrute.setImmuneToZombification(true);
        entity = pigBrute;
        setUp(50.0, 0.35); // same health, same speed
        entity.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET, 1));
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE, 1));
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 50;
    }

    // TODO: onDeath event that spawns 2 PigZombies

}
