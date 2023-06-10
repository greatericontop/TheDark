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

    PISTOL(
            0, 4.0, 11L, 125, 10,
            GunClassification.PISTOL,
            Material.WOODEN_HOE,
            "§fPistol",
            "§7A basic pistol."
    ),
    PISTOL_1STAR(
            1, 5.0, 11L, -1, 16,
            GunClassification.PISTOL,
            Material.WOODEN_HOE,
            "§fPistol §9⚝",
            "§6§l1 Enhancement Star"
    ),

    RIFLE(
            0, 5.0, 8L, 500, 30,
            GunClassification.RIFLE,
            Material.STONE_HOE,
            "§eRifle",
            "§7A high-powered rifle that fires quickly."
    ),
    RIFLE_1STAR(
            1, 6.0, 7L, -1, 30,
            GunClassification.RIFLE,
            Material.STONE_HOE,
            "§eRifle §9⚝",
            "§6§l1 Enhancement Star"
    ),

    SHOTGUN(
            0, 4.0, 25L, 400, 5,
            GunClassification.SHOTGUN,
            Material.IRON_SHOVEL,
            "§eShotgun",
            "§7This shotgun damages multiple enemies."
    ),
    SHOTGUN_1STAR(
            1, 5.0, 25L, -1, 6,
            GunClassification.SHOTGUN,
            Material.IRON_SHOVEL,
            "§eShotgun §9⚝",
            "§6§l1 Enhancement Star"
    ),

    SUPER_WEAPON(
            0, 20.0, 1L, 10, 64,
            GunClassification.SUPER_WEAPON,
            Material.NETHERITE_HOE,
            "§cTHE SUPERWEAPON",
            "§4Need I say more?"
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
    private final String miniDescription;

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

    GunType(int enhancementStarCount, double damage, long cooldownTicks, int cost, int ammoSize,
            GunClassification classification,
            Material itemMaterial, String itemName, String miniDescription
    ) {
        this.enhancementStarCount = enhancementStarCount;
        this.damage = damage;
        this.cooldownTicks = cooldownTicks;
        this.cost = cost;
        this.ammoSize = ammoSize;
        this.classification = classification;
        this.itemMaterial = itemMaterial;
        this.itemName = Component.text(itemName);
        this.miniDescription = miniDescription;
    }

    public int getRechargeTicks() {
        return classification.getRechargeTicks();
    }

    private List<Component> generateLore() {
        if (enhancementStarCount == 0) {
            return List.of(
                    Component.text(miniDescription),
                    Component.text(""),
                    Component.text(String.format("§7Damage: §c%.0f", damage)),
                    Component.text(String.format("§7Cooldown: §f%.2fs", cooldownTicks * 0.05)),
                    Component.text(String.format("§7Capacity: §f%d", ammoSize))
            );
        } else {
            GunType root = classification.getRootGun();
            String damageMessage, cooldownMessage, capacityMessage;
            if (damage == root.getDamage()) {
                damageMessage = String.format("§7Damage: §c%.0f", damage);
            } else {
                damageMessage = String.format("§7Damage: §8%.0f §7-> §c%.0f", root.getDamage(), damage);
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
                    Component.text(miniDescription),
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
