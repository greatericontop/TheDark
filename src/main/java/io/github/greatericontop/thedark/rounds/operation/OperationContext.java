package io.github.greatericontop.thedark.rounds.operation;

import io.github.greatericontop.thedark.TheDark;
import org.bukkit.Location;

public record OperationContext(TheDark plugin, Location[] locations) {
}
