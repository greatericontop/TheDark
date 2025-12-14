package io.github.greatericontop.thedark.util;

/*
 * Copyright (C) 2023-present greateric.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Util {

    public static ItemStack createItemStack(Material mat, int amount, String name, String... lore) {
        ItemStack stack = new ItemStack(mat, amount);
        ItemMeta im = stack.getItemMeta();
        im.displayName(Component.text(name));
        im.setLore(java.util.Arrays.asList(lore));
        stack.setItemMeta(im);
        return stack;
    }

    public static ItemStack createLeatherArmor(Material mat, int rgbColor) {
        ItemStack stack = new ItemStack(mat, 1);
        LeatherArmorMeta im = (LeatherArmorMeta) stack.getItemMeta();
        im.setColor(Color.fromRGB(rgbColor));
        stack.setItemMeta(im);
        return stack;
    }

    public static void playSuccessSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
    }

    public static void playFailSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
    }

    public static double randomDouble(double min, double max) {
        return (max - min) * Math.random() + min;
    }

    public static int roundNumber(double number) {
        int intPart = (int) number;
        double fracPart = number - intPart;
        return intPart + (Math.random() < fracPart ? 1 : 0);
    }

}
