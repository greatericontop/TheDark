package io.github.greatericontop.thedark;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PlaceholderManager extends PlaceholderExpansion {

    private final TheDark plugin;
    public PlaceholderManager(TheDark plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "greateric";
    }

    @Override
    public String getIdentifier() {
        return "thedark";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String args) {
        if (args.startsWith("round")) {
            return String.valueOf(plugin.getRoundManager().getCurrentRound());
        }
        if (args.startsWith("enemiesleft")) {
            return String.valueOf(plugin.getGameManager().activeEnemies.size());
        }
        return null;
    }

}
