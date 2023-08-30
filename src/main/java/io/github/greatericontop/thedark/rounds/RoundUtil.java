package io.github.greatericontop.thedark.rounds;

import io.github.greatericontop.thedark.TheDark;
import io.github.greatericontop.thedark.rounds.data.RoundData;
import io.github.greatericontop.thedark.rounds.operation.BaseOperation;
import io.github.greatericontop.thedark.rounds.operation.OperationContext;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class RoundUtil {

    public static void executeRound(OperationContext ctx, int roundNumber) {
        for (BaseOperation operation : RoundData.ROUNDS[roundNumber]) {
            operation.execute(ctx);
        }
    }
    public static void executeRound(TheDark plugin, int roundNumber) {
        List<Location> locations = new ArrayList<>();
        for (Object o : plugin.getConfig().getList("valid-spawn-locations")) {
            List<Object> ol = (List<Object>) o; // hope that the input is valid...
        }
        //OperationContext ctx = new OperationContext(plugin);
        //executeRound(ctx, roundNumber);
    }

}
