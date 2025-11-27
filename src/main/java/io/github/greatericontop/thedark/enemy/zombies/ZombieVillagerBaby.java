package io.github.greatericontop.thedark.enemy.zombies;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class ZombieVillagerBaby extends BaseEnemy {

    public ZombieVillagerBaby(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE_VILLAGER, false);
        setUp(400.0, 1.1, 300.0);
        entity.getEquipment().setHelmet(new ItemStack(Material.DRAGON_HEAD, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
        ((org.bukkit.entity.ZombieVillager) entity).setBaby();
    }

    @Override
    public void extraDeathEvent(TheDark plugin, PlayerProfile killerProfile) {
        plugin.getGameManager().spawnEnemy(IronZombie.class, entity.getLocation());
    }

}
