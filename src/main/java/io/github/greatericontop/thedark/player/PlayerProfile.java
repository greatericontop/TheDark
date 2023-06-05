package io.github.greatericontop.thedark.player;

import io.github.greatericontop.thedark.menus.ArmorBuyListener;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerProfile {
    private Player player;
    public int coins;
    public int armorLevel;
    public int armorProtectionLevel;

    public PlayerProfile(Player player) {
        this.player = player;
        coins = 0;
        armorLevel = 0;
        armorProtectionLevel = 0;
    }

    public Player getPlayer() {
        return player;
    }


    public void updateArmor() {
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
