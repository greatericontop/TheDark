package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.enemy.zombies.BasicZombie;
import io.github.greatericontop.thedark.enemy.other.EmeraldVindicator;
import io.github.greatericontop.thedark.enemy.other.FatDebugZombie;
import io.github.greatericontop.thedark.enemy.zombies.StandardZombie;
import io.github.greatericontop.thedark.guns.BuyGunManager;
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.menus.SignListener;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.rounds.RoundUtil;
import io.github.greatericontop.thedark.rounds.operation.OperationContext;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

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
        if (args[0].equals("spawnRandomAssault")) {
            player.sendMessage("§3Assault begins in 10 seconds.");
            Location savedLoc = player.getLocation();
            Bukkit.getScheduler().runTaskLater(plugin, () -> debug_spawnEnemies(3, 1, savedLoc), 200L);
            Bukkit.getScheduler().runTaskLater(plugin, () -> debug_spawnEnemies(5, 2, savedLoc), 600L);
            Bukkit.getScheduler().runTaskLater(plugin, () -> debug_spawnEnemies(10, 5, savedLoc), 1000L);
            Bukkit.getScheduler().runTaskLater(plugin, () -> debug_spawnEnemies(10, 10, savedLoc), 1600L);
            Bukkit.getScheduler().runTaskLater(plugin, () -> debug_spawnEnemies(10, 10, savedLoc), 2200L);
            Bukkit.getScheduler().runTaskLater(plugin, () -> player.sendMessage("§3This is the last wave!"), 2700L);
            Bukkit.getScheduler().runTaskLater(plugin, () -> debug_spawnEnemies(20, 15, savedLoc), 2800L);
            return true;
        }
        if (args[0].equals("spawnVindicator")) {
            for (int i = 0; i < 15; i++) {
                plugin.getGameManager().spawnEnemy(EmeraldVindicator.class, player.getLocation());
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
            BuyGunManager.attemptGive(toGive, player, player.getInventory().getHeldItemSlot());
            return true;
        }
        if (args[0].equals("emerald")) {
            plugin.getGameManager().playerProfiles.get(player.getUniqueId()).emeralds += 1;
            return true;
        }
        if (args[0].equals("startRound")) {
            int round = Integer.parseInt(args[1]);
            player.sendMessage("§7Starting round!");
            RoundUtil.executeRound(plugin, round);
        }
        if (args[0].equals("startRoundHere")) {
            int round = Integer.parseInt(args[1]);
            player.sendMessage("§7Starting round here!");
            RoundUtil.executeRound(new OperationContext(plugin, new Location[]{player.getLocation()}), round);
        }

        return false;
    }

    private void debug_spawnEnemies(int count1, int count2, Location loc) {
        for (int i = 0; i < count1; i++) {
            Location adjustedLoc = loc.clone().add(new Vector(4 * (Math.random() - 0.5), 0, 4 * (Math.random() - 0.5)));
            plugin.getGameManager().spawnEnemy(BasicZombie.class, adjustedLoc);
        }
        for (int i = 0; i < count2; i++) {
            Location adjustedLoc = loc.clone().add(new Vector(4 * (Math.random() - 0.5), 0, 4 * (Math.random() - 0.5)));
            plugin.getGameManager().spawnEnemy(StandardZombie.class, adjustedLoc);
        }
    }

}
