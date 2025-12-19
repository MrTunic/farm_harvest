// ---------------------------
// File: src/main/java/io/github/game/world/tiles/WaterTile.java
// ---------------------------
package io.github.game.world.tiles;

import io.github.game.entities.Player;
import io.github.game.world.World;

/**
 * Represents a water tile.
 * Water tiles are not walkable and cannot be interacted with.
 */
public class WaterTile extends AbstractTile {
    public WaterTile() {
        this.walkable = false;
        this.type = TileType.WATER;
    }

    @Override
    public void onInteract(Player player, World world, int x, int y) {
        // no interaction yet
    }

    @Override
    public TileType getType() {
        return TileType.WATER;
    }
}