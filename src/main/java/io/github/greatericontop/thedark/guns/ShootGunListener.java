package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShootGunListener implements Listener {
    private final Map<GunType, Map<UUID, Boolean>> cooldowns = new HashMap<>();
    private final TheDark plugin;

    public ShootGunListener(TheDark plugin) {
        this.plugin = plugin;
        for (GunType gunType : GunType.values()) {
            cooldowns.put(gunType, new HashMap<>());
        }
    }

    @EventHandler(priority = EventPriority.HIGH) // runs later, it can be cancelled by the SignListener event
    public void onInteractNormal(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND)  return;
        if ((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
                && event.getPlayer().isSneaking()) {
            PlayerProfile profile = plugin.getGameManager().getPlayerProfile(event.getPlayer());
            if (profile == null)  return;
            plugin.gunUpgradeListener.openMenu(profile);
        }
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            genericInteractEvent(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGH) // see above
    public void onRightClickEntity(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND)  return;
        genericInteractEvent(event.getPlayer());
    }


    private void genericInteractEvent(Player player) {
        ItemStack stack = player.getInventory().getItemInMainHand();
        ItemMeta im = stack.getItemMeta();
        if (im == null)  return;
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        if (!pdc.has(GunUtil.GUN_KEY, PersistentDataType.STRING))  return;
        GunType gunType = GunType.valueOf(pdc.get(GunUtil.GUN_KEY, PersistentDataType.STRING));
        Map<UUID, Boolean> cooldowns = this.cooldowns.get(gunType);
        if (cooldowns.getOrDefault(player.getUniqueId(), false))  return;
        Damageable damageableIM = (Damageable) im;

        if (damageableIM.getDamage() > 0) { // in the process of a reload
            return;
        }
        int currentAmount = stack.getAmount();
        if (currentAmount == 1) {
            // durability set close to 0 so that it will be exactly repaired after the desired number of ticks
            damageableIM.setDamage(gunType.getMaxDurability() - 1);
            stack.setItemMeta(damageableIM);
            stack.setAmount(1);
        } else {
            stack.setAmount(currentAmount - 1);
        }

        GunUtil.fireProjectile(gunType, player.getEyeLocation(), player.getEyeLocation().getDirection(), player, gunType.getDamage(), 2, plugin);
        // TODO: replace this with GunClassification or a gun firing class
        cooldowns.put(player.getUniqueId(), true);
        Bukkit.getScheduler().runTaskLater(plugin, () -> cooldowns.put(player.getUniqueId(), false), gunType.getCooldownTicks());
    }

}
