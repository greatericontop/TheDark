package io.github.greatericontop.thedark.enemy.other;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.player.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EmeraldVindicator extends BaseEnemy {

    public EmeraldVindicator(Location spawnLocation) {
        entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.VINDICATOR, false);
        setUp(48.0, 0.35); // 2x health, speed same as vanilla
        entity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE, 1));
    }

    @Override
    public void extraDeathEvent(TheDark plugin, PlayerProfile killerProfile) {
        Player killer = killerProfile.getPlayer();
        killerProfile.emeralds += 1;
        killer.sendMessage(Component.text("§2+1 Emerald"));
        killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    }

    @Override
    public int coinsToAwardOnDeath() {
        return 100;
    }

}
