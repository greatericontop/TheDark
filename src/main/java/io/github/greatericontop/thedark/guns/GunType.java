package io.github.greatericontop.thedark.guns;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public enum GunType {

    PISTOL(
            5.0, 11L, 125, 10, 30,
            Material.WOODEN_HOE,
            "§fPistol",
            "§7A basic pistol."
    ),

    RIFLE(
            6.0, 8L, 500, 30, 30,
            Material.STONE_HOE,
            "§eRifle",
            "§7A high-powered rifle that fires quickly."
    ),

    SHOTGUN(
            5.0, 25L, 400, 5, 30,
            Material.IRON_SHOVEL,
            "§eShotgun",
            "§7This shotgun damages multiple enemies."
    ),

    SUPER_WEAPON(
            20.0, 1L, 10, 64, 30,
            Material.NETHERITE_HOE,
            "§cTHE SUPERWEAPON",
            "§4Need I say more?"
    ),

    ;

    private final double damage;
    private final long cooldownTicks;
    private final int cost;
    private final int ammoSize;
    private final int rechargeTicks;

    private final Material itemMaterial;
    private final Component itemName;
    private final List<Component> itemLore;

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
    public int getRechargeTicks() {
        return rechargeTicks;
    }

    GunType(double damage, long cooldownTicks, int cost, int ammoSize, int rechargeTicks, Material itemMaterial, String itemName, String miniDescription) {
        this.damage = damage;
        this.cooldownTicks = cooldownTicks;
        this.cost = cost;
        this.ammoSize = ammoSize;
        this.rechargeTicks = rechargeTicks;
        this.itemMaterial = itemMaterial;
        this.itemName = Component.text(itemName);
        this.itemLore = List.of(
                Component.text(miniDescription),
                Component.text(""),
                Component.text(String.format("§7Damage: §c%.0f", damage)),
                Component.text(String.format("§7Cooldown: §f%.2fs", cooldownTicks*0.05)),
                Component.text(String.format("§7Capacity: §f%d", ammoSize))
        );
    }

    public ItemStack createFullyLoadedItemStack() {
        ItemStack stack = new ItemStack(itemMaterial, ammoSize);
        ItemMeta im = stack.getItemMeta();
        im.displayName(itemName);
        im.lore(itemLore);
        im.getPersistentDataContainer().set(GunUtil.GUN_KEY, PersistentDataType.STRING, this.name());
        stack.setItemMeta(im);
        return stack;
    }

    public int getMaxDurability() {
        return itemMaterial.getMaxDurability();
    }

}
