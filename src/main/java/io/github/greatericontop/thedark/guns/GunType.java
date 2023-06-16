package io.github.greatericontop.thedark.guns;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public enum GunType {

    // basic

    PISTOL(
            0, 4.0, 11L, 125, 10,
            GunClassification.PISTOL,
            Material.WOODEN_HOE,
            "§fPistol"

    ),
    PISTOL_1STAR(
            1, 5.0, 11L, -1, 16,
            GunClassification.PISTOL,
            Material.WOODEN_HOE,
            "§ePistol §9⚝"
    ),

    RIFLE(
            0, 5.0, 8L, 500, 30,
            GunClassification.RIFLE,
            Material.STONE_HOE,
            "§eRifle"

    ),
    RIFLE_1STAR(
            1, 6.0, 7L, -1, 30,
            GunClassification.RIFLE,
            Material.STONE_HOE,
            "§bRifle §9⚝"
    ),

    SHOTGUN(
            0, 4.0, 25L, 400, 5,
            GunClassification.SHOTGUN,
            Material.IRON_SHOVEL,
            "§eShotgun"
    ),
    SHOTGUN_1STAR(
            1, 5.0, 25L, -1, 6,
            GunClassification.SHOTGUN,
            Material.IRON_SHOVEL,
            "§bShotgun §9⚝"
    ),

    // better

    FLAMETHROWER(
            0, 2.5, 3L, -1, 48,
            GunClassification.FLAMETHROWER,
            Material.GOLDEN_SHOVEL,
            "§bFlamethrower"
    ),

    MIDAS_PISTOL(
            0, 4.0, 11L, -1, 10,
            GunClassification.MIDAS_PISTOL,
            Material.GOLDEN_HOE,
            "§bMidas Pistol"
    ),

    ROCKET_LAUNCHER(
            // :cooldownTicks: is set to the reload speed mainly to show the player the reload time on the tooltip
            0, 20.0, 80L, -1, 1,
            GunClassification.ROCKET_LAUNCHER,
            Material.NETHERITE_PICKAXE,
            "§bRocket Launcher"
    ),

    // other

    SUPER_WEAPON(
            0, 20.0, 1L, 10, 64,
            GunClassification.SUPER_WEAPON,
            Material.NETHERITE_HOE,
            "§dTHE SUPERWEAPON"
    ),

    ;

    private final int enhancementStarCount;
    private final double damage;
    private final long cooldownTicks;
    private final int cost;
    private final int ammoSize;

    private final GunClassification classification;

    private final Material itemMaterial;
    private final Component itemName;

    public double getDamage() {
        return damage;
    }
    public long getCooldownTicks() {
        return cooldownTicks;
    }
    public int getCost() {
        return cost;
    }
    public int getAmmoSize() {
        return ammoSize;
    }
    public GunClassification getClassification() {
        return classification;
    }

    GunType(
            int enhancementStarCount, double damage, long cooldownTicks, int cost, int ammoSize,
            GunClassification classification,
            Material itemMaterial, String itemName
    ) {
        this.enhancementStarCount = enhancementStarCount;
        this.damage = damage;
        this.cooldownTicks = cooldownTicks;
        this.cost = cost;
        this.ammoSize = ammoSize;
        this.classification = classification;
        this.itemMaterial = itemMaterial;
        this.itemName = Component.text(itemName);
    }

    public int getRechargeTicks() {
        return classification.getRechargeTicks();
    }

    private List<Component> generateLore() {
        if (enhancementStarCount == 0) {
            return List.of(
                    Component.text(classification.getMiniDescription()),
                    Component.text(""),
                    Component.text(String.format("§7Damage: §c%.1f", damage)),
                    Component.text(String.format("§7Cooldown: §f%.2fs", cooldownTicks * 0.05)),
                    Component.text(String.format("§7Capacity: §f%d", ammoSize))
            );
        } else {
            GunType root = classification.getRootGun();
            String damageMessage, cooldownMessage, capacityMessage;
            if (damage == root.getDamage()) {
                damageMessage = String.format("§7Damage: §c%.1f", damage);
            } else {
                damageMessage = String.format("§7Damage: §8%.1f §7-> §c%.1f", root.getDamage(), damage);
            }
            if (cooldownTicks == root.getCooldownTicks()) {
                cooldownMessage = String.format("§7Cooldown: §f%.2fs", cooldownTicks * 0.05);
            } else {
                cooldownMessage = String.format("§7Cooldown: §8%.2fs §7-> §f%.2fs", root.getCooldownTicks() * 0.05, cooldownTicks * 0.05);
            }
            if (ammoSize == root.getAmmoSize()) {
                capacityMessage = String.format("§7Capacity: §f%d", ammoSize);
            } else {
                capacityMessage = String.format("§7Capacity: §8%d §7-> §f%d", root.getAmmoSize(), ammoSize);
            }
            return List.of(
                    Component.text(String.format("§6§l%d Enhancement Star", enhancementStarCount)),
                    Component.text(""),
                    Component.text(classification.getMiniDescription()),
                    Component.text(""),
                    Component.text(damageMessage),
                    Component.text(cooldownMessage),
                    Component.text(capacityMessage),
                    Component.text(String.format("§7Reload Time: %.1fs", getRechargeTicks() * 0.05))
            );
        }
    }

    public ItemStack createFullyLoadedItemStack() {
        ItemStack stack = new ItemStack(itemMaterial, ammoSize);
        ItemMeta im = stack.getItemMeta();
        im.displayName(itemName);
        im.lore(generateLore());
        im.getPersistentDataContainer().set(GunUtil.GUN_KEY, PersistentDataType.STRING, this.name());
        if (enhancementStarCount > 0) {
            im.addEnchant(Enchantment.LUCK, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        stack.setItemMeta(im);
        return stack;
    }

    public int getMaxDurability() {
        return itemMaterial.getMaxDurability();
    }

}
