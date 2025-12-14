package io.github.greatericontop.thedark;

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

import io.github.greatericontop.thedark.enemy.zombies.BasicZombie;
import io.github.greatericontop.thedark.enemy.zombies.ChainmailZombie;
import io.github.greatericontop.thedark.enemy.zombies.HelmetZombie;
import io.github.greatericontop.thedark.enemy.zombies.IronZombie;
import io.github.greatericontop.thedark.enemy.zombies.MegaPiglinBrute;
import io.github.greatericontop.thedark.enemy.zombies.PiglinBrute;
import io.github.greatericontop.thedark.enemy.zombies.ZombiePiglin;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillager;
import io.github.greatericontop.thedark.enemy.zombies.ZombieVillagerBaby;
import io.github.greatericontop.thedark.guns.GunBuying;
import io.github.greatericontop.thedark.guns.GunType;
import io.github.greatericontop.thedark.menus.SignListener;
import io.github.greatericontop.thedark.miscmechanic.CashGeneration;
import io.github.greatericontop.thedark.miscmechanic.GameDifficulty;
import io.github.greatericontop.thedark.player.PlayerProfile;
import io.github.greatericontop.thedark.rounds.data.LateGame;
import io.github.greatericontop.thedark.rounds.data.RoundData;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.SpawnOneAtATime;
import io.github.greatericontop.thedark.util.GreatCommands;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class TheDarkCommand implements CommandExecutor {

    private final TheDark plugin;
    public TheDarkCommand(TheDark plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cTheDark v" + plugin.getDescription().getVersion());
            return false;
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cPlayers only!");
            return true;
        }


        if (args[0].equals("give")) {
            Player p2 = GreatCommands.argumentPlayer(1, args);
            if (p2 == null) {
                player.sendMessage("§cSpecify a player!");
                return true;
            }
            PlayerProfile profile = plugin.getGameManager().getPlayerProfile(player);
            PlayerProfile profile2 = plugin.getGameManager().getPlayerProfile(p2);
            if (profile == null) {
                player.sendMessage("§cYou are not in the game!");
                return true;
            }
            if (profile2 == null) {
                player.sendMessage("§cThat player is not in the game!");
                return true;
            }
            Integer amount = GreatCommands.argumentInteger(2, args);
            if (amount == null) {
                player.sendMessage("§cSpecify an amount!");
                return true;
            }
            if (amount <= 0) {
                player.sendMessage("§cAmount must be positive!");
                return true;
            }
            if (profile.coins < amount) {
                player.sendMessage("§cYou don't have enough coins!");
                return true;
            }
            profile.coins -= amount;
            profile2.coins += amount;
            player.sendMessage(String.format("§3You gave §b%d §3coins to §b%s§3!", amount, p2.getName()));
            p2.sendMessage(String.format("§3You received §b%d §3coins from §b%s§3!", amount, player.getName()));
            return true;
        }

        if (args[0].equals("setSign")) {
            String arg = GreatCommands.argumentString(1, args);
            if (arg == null) {
                player.sendMessage("§cSpecify what type of sign to set!");
                return true;
            }
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


        // Debug commands


        if (args[0].equals("_addMe")) {
            PlayerProfile profile = new PlayerProfile(player);
            profile.coins = 500; // Starting cash
            plugin.getGameManager().playerProfiles.put(player.getUniqueId(), profile);
            return true;
        }
        if (args[0].equals("_addCoins")) {
            plugin.getGameManager().playerProfiles.get(player.getUniqueId()).coins += Integer.parseInt(args[1]);
            return true;
        }
        if (args[0].equals("_giveGun")) {
            GunType toGive = GunType.valueOf(args[1]);
            if (args.length < 3) {
                GunBuying.debugGiveGun(toGive, player, null);
            } else {
                GunBuying.debugGiveGun(toGive, player, Integer.parseInt(args[2]));
            }
            return true;
        }
        if (args[0].equals("_startGame")) {
            player.sendMessage("§7Starting game!");
            plugin.getRoundManager().startGame();
            return true;
        }
        if (args[0].equals("_startGameAt")) {
            player.sendMessage("§7Starting game!");
            int r = Integer.parseInt(args[1]);
            plugin.getRoundManager().startGameAt(r);
            return true;
        }
        if (args[0].equals("_calculate")) {
            int start = Integer.parseInt(args[1]);
            int end = Integer.parseInt(args[2]);
            double totalHp = 0.0;
            double totalCoins = 0.0;
            for (int r = start; r <= end; r++) {
                BaseOperation[] round;
                if (r <= 50) {
                    round = RoundData.ROUNDS[r];
                } else {
                    round = LateGame.generateRound(r);
                }
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
                            enemyHealth = 990;
                        } else if (op.getEnemyClass() == ZombiePiglin.class) {
                            enemyHealth = 4860;
                        } else if (op.getEnemyClass() == PiglinBrute.class) {
                            //enemyHealth = 17780;
                            enemyHealth = 12920;
                        } else if (op.getEnemyClass() == ZombieVillagerBaby.class) {
                            enemyHealth = 400+160;
                        } else if (op.getEnemyClass() == MegaPiglinBrute.class) {
                            enemyHealth = 10000 + 12920*2;
                        } else {
                            enemyHealth = 0;
                        }
                        totalHp += enemyCount * enemyHealth;
                        totalCoins += enemyCount * enemyHealth * 0.38 * CashGeneration.getCashScale(r);
                    }
                }
            }
            player.sendMessage(String.format("§eHealth from round %d to %d: §a%.2f", start, end, totalHp));
            player.sendMessage(String.format("§eEstimated coins: §a%.2f §7(including scaling)", totalCoins));
            return true;
        }
        if (args[0].equals("_difficulty")) {
            GameDifficulty difficulty = GameDifficulty.valueOf(args[1]);
            plugin.getGameManager().setDifficulty(difficulty);
            player.sendMessage("§3Set game difficulty to §6" + difficulty.name());
        }

        return false;
    }


}
