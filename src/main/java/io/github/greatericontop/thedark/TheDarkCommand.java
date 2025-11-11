package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.enemy.other.EmeraldVindicator;
import io.github.greatericontop.thedark.enemy.other.FatDebugZombie;
import io.github.greatericontop.thedark.enemy.pigs.PigBrute;
import io.github.greatericontop.thedark.enemy.pigs.PigZombie;
import io.github.greatericontop.thedark.enemy.zombies.BasicZombie;
import io.github.greatericontop.thedark.enemy.zombies.MilitantZombie;
import io.github.greatericontop.thedark.enemy.zombies.StandardZombie;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillager;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillagerBaby;
import io.github.greatericontop.thedark.guns.BuyGunUtil;
import io.github.greatericontop.thedark.guns.GunClassification;
import io.github.greatericontop.thedark.menus.SignListener;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.rounds.RoundUtil;
import io.github.greatericontop.thedark.rounds.operation.OperationContext;
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

        if (args[0].equals("spawnMob")) {
            if (args.length < 3) {
                player.sendMessage("§c/thedark spawnMob <className> <count>");
                return true;
            }
            String className = args[1];
            Class<? extends BaseEnemy> clazz;
            // ZOMBIES
            if (className.equalsIgnoreCase("BasicZombie"))  {
                clazz = BasicZombie.class;
            } else if (className.equalsIgnoreCase("StandardZombie"))  {
                clazz = StandardZombie.class;
            } else if (className.equalsIgnoreCase("MilitantZombie"))  {
                clazz = MilitantZombie.class;
            } else if (className.equalsIgnoreCase("ZombieVillager"))  {
                clazz = ZombieVillager.class;
            } else if (className.equalsIgnoreCase("ZombieVillagerBaby"))  {
                clazz = ZombieVillagerBaby.class;
            }
            // PIGS
            else if (className.equalsIgnoreCase("PigZombie"))  {
                clazz = PigZombie.class;
            } else if (className.equalsIgnoreCase("PigBrute"))  {
                clazz = PigBrute.class;
            }
            // OTHER
            else if (className.equalsIgnoreCase("EmeraldVindicator"))  {
                clazz = EmeraldVindicator.class;
            } else if (className.equalsIgnoreCase("FatDebugZombie"))  {
                clazz = FatDebugZombie.class;
            } else {
                player.sendMessage("§cUnknown className " + className);
                return true;
            }
            int count = Integer.parseInt(args[2]);
            for (int i = 0; i < count; i++) {
                plugin.getGameManager().spawnEnemy(clazz, player.getLocation());
            }
            return true;
        }
        if (args[0].equals("addMe")) {
            PlayerProfile profile = new PlayerProfile(player);
            profile.coins = Integer.parseInt(args[1]);
            plugin.getGameManager().playerProfiles.put(player.getUniqueId(), profile);
            return true;
        }
        if (args[0].equals("addCoins")) {
            plugin.getGameManager().playerProfiles.get(player.getUniqueId()).coins += Integer.parseInt(args[1]);
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
            GunClassification toGive = GunClassification.valueOf(args[1]);
            if (args.length < 3) {
                BuyGunUtil.debugGiveGun(toGive, player, null);
            } else {
                BuyGunUtil.debugGiveGun(toGive, player, Integer.parseInt(args[2]));
            }
            return true;
        }
        if (args[0].equals("buyGun")) {
            GunClassification toGive = GunClassification.valueOf(args[1]);
            BuyGunUtil.attemptGive(toGive, player, player.getInventory().getHeldItemSlot());
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
