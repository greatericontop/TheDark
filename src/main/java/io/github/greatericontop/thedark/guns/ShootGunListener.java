package io.github.greatericontop.thedark.guns;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.upgrades.UpgradeListing;
import io.github.greatericontop.thedark.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShootGunListener implements Listener {
    private final Map<GunClassification, Map<UUID, Boolean>> cooldowns = new HashMap<>();
    private final TheDark plugin;

    public ShootGunListener(TheDark plugin) {
        this.plugin = plugin;
        for (GunClassification classification : GunClassification.values()) {
            cooldowns.put(classification, new HashMap<>());
        }
    }

    @EventHandler()
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

    @EventHandler()
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
        GunClassification gunType = GunClassification.valueOf(pdc.get(GunUtil.GUN_KEY, PersistentDataType.STRING));
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

        // Damage/pierce/cooldown/etc handled by :GunClassification: implementation,
        // which then finally calls :performFire:
        gunType.fire(player, plugin, pdc.get(UpgradeListing.TOP_PATH, PersistentDataType.INTEGER), pdc.get(UpgradeListing.BOTTOM_PATH, PersistentDataType.INTEGER), pdc);
    }

    public void performFire(GunClassification classification, Player player, Vector direction, int pierce, double damage, double cooldownTicks) {
        GunUtil.fireProjectile(player.getEyeLocation(), direction, player, damage, pierce, plugin);
        int intCooldownTicks = Util.roundNumber(cooldownTicks);
        if (intCooldownTicks > 0) {
            Map<UUID, Boolean> gunCooldowns = cooldowns.get(classification);
            gunCooldowns.put(player.getUniqueId(), true);
            Bukkit.getScheduler().runTaskLater(plugin, () -> gunCooldowns.put(player.getUniqueId(), false), intCooldownTicks);
        }
        player.sendMessage(String.format("§7[Debug] fired with pierce %d, damage %.1f, cooldown %dt", pierce, damage, intCooldownTicks));
    }

}
