package io.github.greatericontop.thedark.menus;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.guns.GunBuying;
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.player.PlayerProfile;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SignListener implements Listener {
    public static final NamespacedKey SIGN_TYPE_KEY = new NamespacedKey("thedark", "sign_type");

    private final TheDark plugin;
    public SignListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onSignClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)  return;
        if (event.getClickedBlock() == null)  return;
        if (event.getClickedBlock().getType() != Material.OAK_SIGN && event.getClickedBlock().getType() != Material.OAK_WALL_SIGN)  return;
        Sign signBlock = (Sign) event.getClickedBlock().getState();
        PersistentDataContainer pdc = signBlock.getPersistentDataContainer();
        if (!pdc.has(SIGN_TYPE_KEY, PersistentDataType.STRING))  return;
        event.setCancelled(true);
        String signType = pdc.get(SIGN_TYPE_KEY, PersistentDataType.STRING);
        Player player = event.getPlayer();
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null) {
            player.sendMessage("§cYou don't have a profile!");
            return;
        }

        if (signType.equals("armor")) {
            plugin.armorBuyListener.openMenu(profile);
        } else if (signType.equals("armorEnchantment")) {
            plugin.armorEnchantmentListener.openMenu(profile);
        } else if (signType.equals("sword")) {
            plugin.swordBuyListener.openMenu(profile);
        } else if (signType.equals("startGame")) {
            plugin.startGameMenu.openMenu(profile);
        } else if (signType.startsWith("buyGun_")) {
            GunType toBuy = GunType.valueOf(signType.substring(7)); // remove "buyGun_"
            GunBuying.buy(toBuy, profile, player);
        }
    }

}
