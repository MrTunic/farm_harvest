// ---------------------------
// File: src/main/java/io/github/game/world/tiles/AbstractTile.java
// ---------------------------
package io.github.game.world.tiles;

import io.github.game.entities.Player;
import io.github.game.world.World;

/**
 * Base class for all tiles in the game world.
 * Tiles define walkability, type, and interaction behavior.
 */
public abstract class AbstractTile {

    /** Whether the tile can be walked on by the player. */
    protected boolean walkable = true;

    /** Type identifier for this tile. */
    protected TileType type;

    /**
     * Returns whether this tile is walkable.
     *
     * @return true if walkable
     */
    public boolean isWalkable() {
        return walkable;
    }

    /**
     * Called when a player interacts with this tile.
     *
     * @param player interacting player
     * @param world  reference to the world
     * @param x      tile x-coordinate
     * @param y      tile y-coordinate
     */
    public abstract void onInteract(Player player, World world, int x, int y);

    /**
     * Returns the tile type.
     *
     * @return tile type
     */
    public TileType getType() {
        return type;
    }
}