package io.github.greatericontop.thedark.enemy.zombies;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class ZombieVillager extends BaseEnemy {

    public ZombieVillager(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE_VILLAGER, false);
        setUp(20.0, 0.23); // same health, same speed
        int color = 0x11bb11;
        entity.getEquipment().setHelmet(Util.createLeatherArmor(Material.LEATHER_HELMET, color));
        entity.getEquipment().setChestplate(Util.createLeatherArmor(Material.LEATHER_CHESTPLATE, color));
        entity.getEquipment().setLeggings(Util.createLeatherArmor(Material.LEATHER_LEGGINGS, color));
        entity.getEquipment().setBoots(Util.createLeatherArmor(Material.LEATHER_BOOTS, color));
    }

    @Override
    public void extraDeathEvent(TheDark plugin, PlayerProfile killerProfile) {
        plugin.getGameManager().spawnEnemy(ZombieVillagerBaby.class, entity.getLocation());
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 20;
    }

}
