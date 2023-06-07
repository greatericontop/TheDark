package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.enemy.BasicZombie;
import io.github.greatericontop.thedark.enemy.FatDebugZombie;
import io.github.greatericontop.thedark.enemy.StandardZombie;
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.guns.GunUtil;
import io.github.greatericontop.thedark.menus.BuyGunManager;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.menus.SignListener;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class TheDarkCommand implements CommandExecutor {

    private final TheDark plugin;
    public TheDarkCommand(TheDark plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cPlayers only!");
            return true;
        }

        if (args[0].equals("info")) {
            player.sendMessage("§cTheDark v" + plugin.getDescription().getVersion());
            player.sendMessage(String.format("§7Enemy count: §f%d", plugin.getGameManager().activeEnemies.size()));
            return true;
        }

        if (args[0].equals("spawnBasicZombies")) {
            for (int i = 0; i < 5; i++) {
                plugin.getGameManager().spawnEnemy(BasicZombie.class, player.getLocation());
            }
            return true;
        }
        if (args[0].equals("spawnDebugZombie")) {
            plugin.getGameManager().spawnEnemy(FatDebugZombie.class, player.getLocation());
            return true;
        }
        if (args[0].equals("spawnSomeEnemies")) {
            for (int i = 0; i < 10; i++) {
                plugin.getGameManager().spawnEnemy(BasicZombie.class, player.getLocation());
                plugin.getGameManager().spawnEnemy(StandardZombie.class, player.getLocation());
            }
            return true;
        }
        if (args[0].equals("addMe")) {
            PlayerProfile profile = new PlayerProfile(player);
            profile.coins = Integer.parseInt(args[1]);
            plugin.getGameManager().playerProfiles.put(player.getUniqueId(), profile);
            return true;
        }
        if (args[0].equals("setSign")) {
            Block lookingAt = player.getTargetBlock(10);
            if (lookingAt == null || (lookingAt.getType() != Material.OAK_WALL_SIGN && lookingAt.getType() != Material.OAK_SIGN)) {
                player.sendMessage("§cYou must be looking at a sign!");
                return true;
            }
            Sign sign = (Sign) lookingAt.getState(false); // get real state as we're going to modify it
            sign.getPersistentDataContainer().set(SignListener.SIGN_TYPE_KEY, PersistentDataType.STRING, args[1]);
            player.sendMessage("§3Set your sign to be: §7" + args[1]);
            return true;
        }
        if (args[0].equals("makeGun")) {
            ItemMeta im = player.getInventory().getItemInMainHand().getItemMeta();
            GunType gunType = GunType.valueOf(args[1]);
            im.getPersistentDataContainer().set(GunUtil.GUN_KEY, PersistentDataType.STRING, args[1]);
            im.lore(Arrays.asList(
                    Component.text(String.format("§7Damage: §f%.0f", gunType.getDamage())),
                    Component.text(String.format("§7Fire Rate: §f%.2fs", gunType.getCooldownTicks()*0.05))
            ));
            player.getInventory().getItemInMainHand().setItemMeta(im);
            return true;
        }
        if (args[0].equals("giveGun")) {
            GunType toGive = GunType.valueOf(args[1]);
            if (args.length < 3) {
                BuyGunManager.debugGiveGun(toGive, player, null);
            } else {
                BuyGunManager.debugGiveGun(toGive, player, Integer.parseInt(args[2]));
            }
            return true;
        }
        if (args[0].equals("forceBuyGun")) {
            GunType toGive = GunType.valueOf(args[1]);
            BuyGunManager.attemptBuy(toGive, player, player.getInventory().getHeldItemSlot());
            return true;
        }

        return false;
    }

}
