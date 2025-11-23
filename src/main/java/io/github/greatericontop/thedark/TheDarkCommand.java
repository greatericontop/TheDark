package io.github.greatericontop.thedark;

import io.github.greatericontop.thedark.enemy.BaseEnemy;
import io.github.greatericontop.thedark.enemy.zombies.BasicZombie;
import io.github.greatericontop.thedark.enemy.zombies.ChainmailZombie;
import io.github.greatericontop.thedark.enemy.zombies.HelmetZombie;
import io.github.greatericontop.thedark.enemy.zombies.IronZombie;
import io.github.greatericontop.thedark.enemy.zombies.ZombiePiglin;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillager;
import io.github.greatericontop.thedark.guns.GunBuying;
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.menus.SignListener;
import io.github.greatericontop.thedark.miscmechanic.CashGeneration;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.rounds.RoundSpawner;
import io.github.greatericontop.thedark.rounds.data.RoundData;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.OperationContext;
import io.github.greatericontop.thedark.rounds.operation.SpawnOneAtATime;
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
            if (className.equalsIgnoreCase("BasicZombie")) {
                clazz = BasicZombie.class;
            } else if (className.equalsIgnoreCase("HelmetZombie")) {
                clazz = HelmetZombie.class;
            } else if (className.equalsIgnoreCase("ChainmailZombie")) {
                clazz = ChainmailZombie.class;
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
            profile.coins = 500; // Starting cash
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
            GunType toGive = GunType.valueOf(args[1]);
            if (args.length < 3) {
                GunBuying.debugGiveGun(toGive, player, null);
            } else {
                GunBuying.debugGiveGun(toGive, player, Integer.parseInt(args[2]));
            }
            return true;
        }
        if (args[0].equals("buyGun")) {
            GunType toGive = GunType.valueOf(args[1]);
            GunBuying.attemptGive(toGive, player, player.getInventory().getHeldItemSlot());
            return true;
        }
        if (args[0].equals("emerald")) {
            plugin.getGameManager().playerProfiles.get(player.getUniqueId()).emeralds += 1;
            return true;
        }
        if (args[0].equals("startRound")) {
            int round = Integer.parseInt(args[1]);
            player.sendMessage("§7Starting round!");
            RoundSpawner.executeRound(plugin, round);
            return true;
        }
        if (args[0].equals("startRoundHere")) {
            int round = Integer.parseInt(args[1]);
            player.sendMessage("§7Starting round here!");
            RoundSpawner.executeRound(new OperationContext(plugin, new Location[]{player.getLocation()}), round);
            return true;
        }
        if (args[0].equals("startGame")) {
            player.sendMessage("§7Starting game!");
            plugin.getRoundManager().startGame();
            return true;
        }
        if (args[0].equals("startGameAt")) {
            player.sendMessage("§7Starting game!");
            int r = Integer.parseInt(args[1]);
            plugin.getRoundManager().startGameAt(r);
            return true;
        }
        if (args[0].equals("calculateCoins")) {
            int start = Integer.parseInt(args[1]);
            int end = Integer.parseInt(args[2]);
            double totalHp = 0.0;
            double totalCoins = 0.0;
            for (int r = start; r <= end; r++) {
                BaseOperation[] round = RoundData.ROUNDS[r];
                for (BaseOperation baseOp : round) {
                    if (baseOp instanceof SpawnOneAtATime op) {
                        int enemyCount = op.getCount();
                        double enemyHealth;
                        if (op.getEnemyClass() == BasicZombie.class) {
                            enemyHealth = 20;
                        } else if (op.getEnemyClass() == HelmetZombie.class) {
                            enemyHealth = 40;
                        } else if (op.getEnemyClass() == ChainmailZombie.class) {
                            enemyHealth = 80;
                        } else if (op.getEnemyClass() == IronZombie.class) {
                            enemyHealth = 160;
                        } else if (op.getEnemyClass() == ZombieVillager.class) {
                            enemyHealth = 350 + 4*160;
                        } else if (op.getEnemyClass() == ZombiePiglin.class) {
                            enemyHealth = 900 + 4 * (350+4*160);
                        } else {
                            enemyHealth = 0;
                        }
                        totalHp += enemyCount * enemyHealth;
                        totalCoins += enemyCount * enemyHealth * 0.38 * CashGeneration.getCashScale(r);
                    }
                }
            }
            player.sendMessage(String.format("§eHealth from round %d to %d: §a%.2f §7(including scaling)", start, end, totalHp));
            player.sendMessage(String.format("§eEstimated coins: §a%.2f §7(including scaling)", start, end, totalCoins));
            return true;
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
            plugin.getGameManager().spawnEnemy(HelmetZombie.class, adjustedLoc);
        }
    }

}
