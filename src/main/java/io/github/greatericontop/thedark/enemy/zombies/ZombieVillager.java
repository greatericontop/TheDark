package io.github.greatericontop.thedark.enemy.zombies;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.enemy.EnragedEnemy;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZombieVillager extends EnragedEnemy {

    public ZombieVillager(Location spawnLocation) {
        super(600);
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE_VILLAGER, false);
        setUp(350.0, 1.175, 20.0);
        entity.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
    }

    @Override
    public void extraDeathEvent(TheDark plugin, PlayerProfile killerProfile) {
        for (int i = 0; i < 4; i++) {
            plugin.getGameManager().spawnEnemy(IronZombie.class, entity.getLocation());
        }
    }

    @Override
    protected void enrageSelf() {
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1.0F, 1.0F);
        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.23 * 2.0);
        for (Entity e : entity.getNearbyEntities(100.0, 100.0, 100.0)) {
            if (e instanceof Player p) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 100, 0));
            }
        }
    }

}
