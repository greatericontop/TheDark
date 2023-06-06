package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
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

    @EventHandler()
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND)  return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)  return;
        Player player = event.getPlayer();
        ItemMeta im = player.getInventory().getItemInMainHand().getItemMeta();
        if (im == null)  return;
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        if (!pdc.has(GunUtil.GUN_KEY, PersistentDataType.STRING))  return;

        GunType gunType = GunType.valueOf(pdc.get(GunUtil.GUN_KEY, PersistentDataType.STRING));
        Map<UUID, Boolean> cooldowns = this.cooldowns.get(gunType);
        if (cooldowns.getOrDefault(player.getUniqueId(), false))  return;

        GunUtil.fireProjectile(gunType, player.getEyeLocation(), player.getEyeLocation().getDirection(), player, gunType.getDamage(), plugin);
        cooldowns.put(player.getUniqueId(), true);
        Bukkit.getScheduler().runTaskLater(plugin, () -> cooldowns.put(player.getUniqueId(), false), gunType.getCooldownTicks());
    }

}
