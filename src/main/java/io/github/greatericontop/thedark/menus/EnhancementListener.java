package io.github.greatericontop.thedark.menus;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.guns.GunClassification;
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.guns.GunUtil;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.util.RouletteLootTable;
import io.github.greatericontop.thedark.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EnhancementListener extends GenericMenu {
    public static final Component INVENTORY_NAME = Component.text("§c[TheDark] §2Item Enhancer");

    private static final int[] ENHANCEMENT_COSTS = {
            3000, // i=0, first
            10000, // i=1, second
            20000, // i=2, third
    };

    private final TheDark plugin;
    public EnhancementListener(TheDark plugin) {
        this.plugin = plugin;
    }

    @Override
    public void openMenu(PlayerProfile profile) {
        Player player = profile.getPlayer();

        // Calculate stuff
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemMeta im = heldItem.getItemMeta();
        if (im == null) {
            player.sendMessage("§cYou must be holding a gun to enhance it! §7[null ItemMeta]");
            return;
        }
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        if (!pdc.has(GunUtil.GUN_KEY, PersistentDataType.STRING)) {
            player.sendMessage("§cYou must be holding a gun to enhance it! §7[no pdc key GUN_KEY]");
            return;
        }
        GunType gunType = GunType.valueOf(pdc.get(GunUtil.GUN_KEY, PersistentDataType.STRING));
        GunClassification gunClassification = gunType.getClassification();
        if (gunClassification.getMaxEnhancementStars() == gunType.getEnhancementStarCount()) {
            player.sendMessage("§cThis gun is already maxed out!");
            return;
        }

        int coinCost = ENHANCEMENT_COSTS[gunType.getEnhancementStarCount()];
        ItemStack info = Util.createItemStack(Material.DIAMOND, 1, "§bEnhancement",
                String.format("§6%d §b-> §6§l%d", gunType.getEnhancementStarCount(), gunType.getEnhancementStarCount() + 1),
                "§fThis gun will gain a large, permanent boost in stats!",
                "§eCost: §6" + coinCost
        );
        ItemStack enhancedGunPreview = gunClassification.getChildGun(gunType.getEnhancementStarCount() + 1).createFullyLoadedItemStack();
        ItemStack clickHere = Util.createItemStack(Material.ANVIL, 1, "§bClick here to enhance!");
        Inventory gui = Bukkit.createInventory(player, 9, INVENTORY_NAME);
        gui.setItem(1, info);
        gui.setItem(5, enhancedGunPreview);
        gui.setItem(8, clickHere);
        player.openInventory(gui);
    }

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().title().equals(INVENTORY_NAME))  return;
        event.setCancelled(true);
        int slot = event.getSlot();
        if (slot != 8)  return;
        Player player = (Player) event.getWhoClicked();
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null) {
            player.sendMessage("§cYou don't have a profile!");
            return;
        }

        // Calculate stuff

        // ...

    }

}
