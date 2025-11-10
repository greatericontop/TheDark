package io.github.greatericontop.thedark.menus;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.guns.GunClassification;
import io.github.greatericontop.thedark.guns.GunUtil;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.upgrades.Upgrade;
import io.github.greatericontop.thedark.upgrades.UpgradeList;
import io.github.greatericontop.thedark.upgrades.UpgradeUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class GunUpgradeListener extends GenericMenu {
    public static final Component INVENTORY_NAME = Component.text("§c[TheDark] §2Weapon Upgrade");

    private final TheDark plugin;
    public GunUpgradeListener(TheDark plugin) {
        this.plugin = plugin;
    }

    public void openMenu(PlayerProfile profile) {
        Player player = profile.getPlayer();
        ItemMeta im = player.getInventory().getItemInMainHand().getItemMeta();
        if (im == null) {
            player.sendMessage("§cYou must be holding a weapon to upgrade it!");
            return;
        }
        Integer topPath = im.getPersistentDataContainer().get(UpgradeUtils.TOP_PATH, PersistentDataType.INTEGER);
        Integer bottomPath = im.getPersistentDataContainer().get(UpgradeUtils.BOTTOM_PATH, PersistentDataType.INTEGER);
        if (topPath == null || bottomPath == null) {
            player.sendMessage("§cYou must be holding a weapon to upgrade it!");
            return;
        }
        GunClassification classification = GunUtil.getHeldGunClassification(player);
        UpgradeList upgradeList = plugin.upgradeUtils.getUpgradeList(classification);
        if (upgradeList == null) {
            player.sendMessage("§cThis weapon cannot be upgraded yet!");
            return;
        }

        Inventory gui = Bukkit.createInventory(player, 18, INVENTORY_NAME);
        gui.setItem(0, renderItem(topPath >= 1 ? Material.LIME_STAINED_GLASS_PANE : Material.WHITE_STAINED_GLASS_PANE, upgradeList.top1()));
        if (topPath >= 1) {
            gui.setItem(1, renderItem(topPath >= 2 ? Material.LIME_STAINED_GLASS_PANE : Material.WHITE_STAINED_GLASS_PANE, upgradeList.top2()));
            if (topPath >= 2 && bottomPath <= 2) {
                gui.setItem(2, renderItem(topPath >= 3 ? Material.LIME_STAINED_GLASS_PANE : Material.WHITE_STAINED_GLASS_PANE, upgradeList.top3()));
                if (topPath >= 3) {
                    gui.setItem(3, renderItem(topPath >= 4 ? Material.LIME_STAINED_GLASS_PANE : Material.WHITE_STAINED_GLASS_PANE, upgradeList.top4()));
                }
            }
        }
        gui.setItem(9, renderItem(bottomPath >= 1 ? Material.LIME_STAINED_GLASS_PANE : Material.WHITE_STAINED_GLASS_PANE, upgradeList.bottom1()));
        if (bottomPath >= 1) {
            gui.setItem(10, renderItem(bottomPath >= 2 ? Material.LIME_STAINED_GLASS_PANE : Material.WHITE_STAINED_GLASS_PANE, upgradeList.bottom2()));
            if (bottomPath >= 2 && topPath <= 2) {
                gui.setItem(11, renderItem(bottomPath >= 3 ? Material.LIME_STAINED_GLASS_PANE : Material.WHITE_STAINED_GLASS_PANE, upgradeList.bottom3()));
                if (bottomPath >= 3) {
                    gui.setItem(12, renderItem(bottomPath >= 4 ? Material.LIME_STAINED_GLASS_PANE : Material.WHITE_STAINED_GLASS_PANE, upgradeList.bottom4()));
                }
            }
        }
        player.openInventory(gui);
    }

    private ItemStack renderItem(Material mat, Upgrade upgrade) {
        ItemStack stack = new ItemStack(mat, 1);
        ItemMeta im = stack.getItemMeta();
        im.setDisplayName("§6" + upgrade.name());
        im.setLore(List.of("§7" + upgrade.description(), String.format("§fCost: §6%d coins", upgrade.cost())));
        stack.setItemMeta(im);
        return stack;
    }

}
