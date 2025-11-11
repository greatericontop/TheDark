package io.github.greatericontop.thedark.player;

import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.guns.GunUtil;
import io.github.greatericontop.thedark.menus.ArmorBuyListener;
import io.github.greatericontop.thedark.menus.SwordBuyListener;
import io.github.greatericontop.thedark.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerProfile {
    private Player player;

    public int coins;
    public int emeralds;

    public int armorLevel;
    public int armorProtectionLevel;
    public int swordTier;
    public boolean swordSharpnessOne;

    public PlayerProfile(Player player) {
        this.player = player;
        coins = 0;
        emeralds = 0;
        armorLevel = 0;
        armorProtectionLevel = 0;
        swordTier = 1;
        swordSharpnessOne = false;
        initializePlayer();
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    private void initializePlayer() {
        for (int i = 1; i <= 3; i++) {
            player.getInventory().setItem(i, Util.createItemStack(Material.LIGHT_GRAY_DYE, 1, "§7Empty Slot", "§fBuy a gun!"));
        }
    }

    public Component getActionBar() {
        if (emeralds == 0) {
            return Component.text(String.format("§6Coins: %,d", coins));
        }
        return Component.text(String.format("§6Coins: %,d    §2%d Emerald%s", coins, emeralds, emeralds == 1 ? "" : "s"));
    }

    public void updateInventory() {
        // update durability (during reloading) of guns
        for (int slot = 1; slot <= 3; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack == null || stack.getType() == Material.LIGHT_GRAY_DYE)  continue;
            ItemMeta genericIM = stack.getItemMeta();
            if (genericIM == null)  continue;
            if (!genericIM.getPersistentDataContainer().has(GunUtil.GUN_KEY, PersistentDataType.STRING))  continue;
            Damageable im = (Damageable) genericIM;
            if (im.getDamage() > 0) {
                // perform 1 tick worth of reloading
                GunType gunType = GunType.valueOf(im.getPersistentDataContainer().get(GunUtil.GUN_KEY, PersistentDataType.STRING));
                int newDamageAmount = GunUtil.getDamagePositionBelowCurrent(gunType.getMaxDurability(), gunType.getRechargeTicks(), im.getDamage());
                if (newDamageAmount <= 0) {
                    if (newDamageAmount < 0)  throw new RuntimeException();
                    stack.setAmount(gunType.getAmmoCapacity());
                    im.setDamage(0);
                } else {
                    im.setDamage(newDamageAmount);
                }
                stack.setItemMeta(im);
            }
        }

        // SWORD
        Material swordMaterial = SwordBuyListener.SWORD_MATERIALS[swordTier - 1];
        ItemStack sword = new ItemStack(swordMaterial, 1);
        if (swordSharpnessOne) {
            sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
        player.getInventory().setItem(0, sword);
        // ARMOR
        if (armorLevel == 0) {
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
            return;
        }
        Material[] armorMaterials = ArmorBuyListener.ARMOR_MATERIALS[armorLevel - 1];
        ItemStack helm = new ItemStack(armorMaterials[0]);
        ItemStack chest = new ItemStack(armorMaterials[1]);
        ItemStack legs = new ItemStack(armorMaterials[2]);
        ItemStack boots = new ItemStack(armorMaterials[3]);
        if (armorProtectionLevel > 0) {
            int allArmorProtection = Math.min(armorProtectionLevel, 4);
            helm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, armorProtectionLevel);
            chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, allArmorProtection);
            legs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, allArmorProtection);
            boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, allArmorProtection);
        }
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(legs);
        player.getInventory().setBoots(boots);
    }

}
