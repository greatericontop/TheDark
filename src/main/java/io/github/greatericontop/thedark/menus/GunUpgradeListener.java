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
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class GunUpgradeListener extends GenericMenu {
    public static final Component INVENTORY_NAME = Component.text("§c[TheDark] §2Weapon Upgrade");
    private static final Material BOUGHT = Material.LIME_STAINED_GLASS_PANE;
    private static final Material READY = Material.WHITE_STAINED_GLASS_PANE;
    private static final int TOP1 = 0;
    private static final int TOP2 = 1;
    private static final int TOP3 = 2;
    private static final int TOP4 = 3;
    private static final int BOTTOM1 = 18;
    private static final int BOTTOM2 = 19;
    private static final int BOTTOM3 = 20;
    private static final int BOTTOM4 = 21;

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

        Inventory gui = Bukkit.createInventory(player, 27, INVENTORY_NAME);
        gui.setItem(TOP1, renderItem(topPath >= 1 ? BOUGHT : READY, upgradeList.top1()));
        if (topPath >= 1) {
            gui.setItem(TOP2, renderItem(topPath >= 2 ? BOUGHT : READY, upgradeList.top2()));
            if (topPath >= 2 && bottomPath <= 2) {
                gui.setItem(TOP3, renderItem(topPath >= 3 ? BOUGHT : READY, upgradeList.top3()));
                if (topPath >= 3) {
                    gui.setItem(TOP4, renderItem(topPath >= 4 ? BOUGHT : READY, upgradeList.top4()));
                }
            }
        }
        gui.setItem(BOTTOM1, renderItem(bottomPath >= 1 ? BOUGHT : READY, upgradeList.bottom1()));
        if (bottomPath >= 1) {
            gui.setItem(BOTTOM2, renderItem(bottomPath >= 2 ? BOUGHT : READY, upgradeList.bottom2()));
            if (bottomPath >= 2 && topPath <= 2) {
                gui.setItem(BOTTOM3, renderItem(bottomPath >= 3 ? BOUGHT : READY, upgradeList.bottom3()));
                if (bottomPath >= 3) {
                    gui.setItem(BOTTOM4, renderItem(bottomPath >= 4 ? BOUGHT : READY, upgradeList.bottom4()));
                }
            }
        }
        player.openInventory(gui);
    }

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().title().equals(INVENTORY_NAME))  return;
        event.setCancelled(true);
        int slot = event.getSlot();
        if (event.getClickedInventory().getItem(slot) == null || event.getClickedInventory().getItem(slot).getType() != READY)  return;
        Player player = (Player) event.getWhoClicked();
        PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
        if (profile == null) {
            player.sendMessage("§cYou don't have a profile!");
            return;
        }

        GunClassification classification = GunUtil.getHeldGunClassification(player);
        UpgradeList upgradeList = plugin.upgradeUtils.getUpgradeList(classification);
        switch (slot) {
            case 0 -> attemptToBuy(profile, upgradeList.top1(), true);
            case 1 -> attemptToBuy(profile, upgradeList.top2(), true);
            case 2 -> attemptToBuy(profile, upgradeList.top3(), true);
            case 3 -> attemptToBuy(profile, upgradeList.top4(), true);
            case 9 -> attemptToBuy(profile, upgradeList.bottom1(), false);
            case 10 -> attemptToBuy(profile, upgradeList.bottom2(), false);
            case 11 -> attemptToBuy(profile, upgradeList.bottom3(), false);
            case 12 -> attemptToBuy(profile, upgradeList.bottom4(), false);
        }
    }

    private void attemptToBuy(PlayerProfile profile, Upgrade upgrade, boolean isTop) {
        if (profile.coins < upgrade.cost()) {
            profile.getPlayer().sendMessage("§cYou can't afford this!");
            return;
        }
        profile.coins -= upgrade.cost();
        ItemStack stack = profile.getPlayer().getInventory().getItemInMainHand();
        ItemMeta im = stack.getItemMeta();
        if (isTop) {
            int topPath = im.getPersistentDataContainer().get(UpgradeUtils.TOP_PATH, PersistentDataType.INTEGER);
            im.getPersistentDataContainer().set(UpgradeUtils.TOP_PATH, PersistentDataType.INTEGER, topPath+1);
        } else {
            int bottomPath = im.getPersistentDataContainer().get(UpgradeUtils.BOTTOM_PATH, PersistentDataType.INTEGER);
            im.getPersistentDataContainer().set(UpgradeUtils.BOTTOM_PATH, PersistentDataType.INTEGER, bottomPath+1);
        }
        stack.setItemMeta(im);
        profile.getPlayer().sendMessage("§aUpgrade purchased!");
        profile.getPlayer().closeInventory();
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
