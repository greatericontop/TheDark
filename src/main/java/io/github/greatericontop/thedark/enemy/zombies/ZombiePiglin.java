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

public class ZombiePiglin extends EnragedEnemy {

    public ZombiePiglin(Location spawnLocation) {
        super(720);
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIFIED_PIGLIN, false);
        setUp(900.0, 1.175, 300.0);
        entity.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD, 1));
    }

    @Override
    public void extraDeathEvent(TheDark plugin, PlayerProfile killerProfile) {
        for (int i = 0; i < 4; i++) {
            plugin.getGameManager().spawnEnemy(ZombieVillager.class, entity.getLocation());
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
