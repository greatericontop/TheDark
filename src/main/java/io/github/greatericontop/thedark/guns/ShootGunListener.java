package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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

    @EventHandler(priority = EventPriority.HIGH) // runs later
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND)  return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)  return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.useInteractedBlock() == Event.Result.DENY) {
            // it doesn't run when cancelled by the SignListener event
            return;
        }
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        ItemMeta im = stack.getItemMeta();
        if (im == null)  return;
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        if (!pdc.has(GunUtil.GUN_KEY, PersistentDataType.STRING))  return;

        GunType gunType = GunType.valueOf(pdc.get(GunUtil.GUN_KEY, PersistentDataType.STRING));
        Map<UUID, Boolean> cooldowns = this.cooldowns.get(gunType);
        if (cooldowns.getOrDefault(player.getUniqueId(), false))  return;

        Damageable im1 = (Damageable) im;
        int newDamage = im1.getDamage() + gunType.getDurabilityPerUse();
        if (newDamage >= gunType.getDurabilityPerUse()*gunType.getAmmoSize()) {
            player.sendMessage("§7Placeholder for something else! Reloading!");
            im1.setDamage(0);
        } else {
            im1.setDamage(newDamage);
        }
        stack.setItemMeta(im1);

        GunUtil.fireProjectile(gunType, player.getEyeLocation(), player.getEyeLocation().getDirection(), player, gunType.getDamage(), plugin);
        cooldowns.put(player.getUniqueId(), true);
        Bukkit.getScheduler().runTaskLater(plugin, () -> cooldowns.put(player.getUniqueId(), false), gunType.getCooldownTicks());
    }

}
