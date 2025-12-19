// ---------------------------
// File: src/main/java/io/github/game/world/tiles/WaterTile.java
// ---------------------------
package io.github.game.world.tiles;

import io.github.game.entities.Player;
import io.github.game.world.World;

// WaterTile class representing a water tile in the game world
public class WaterTile extends AbstractTile {
    public WaterTile() { this.walkable = false; this.type = TileType.WATER; }

    @Override
    public void onInteract(Player player, World world, int x, int y) { /* no-op */ }

    @Override
    public TileType getType() { return TileType.WATER; }
}