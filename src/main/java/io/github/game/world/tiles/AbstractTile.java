// ---------------------------
// File: src/main/java/io/github/game/world/tiles/AbstractTile.java
// ---------------------------
package io.github.game.world.tiles;

import io.github.game.entities.Player;
import io.github.game.world.World;

// Abstract class representing a tile in the game world.
public abstract class AbstractTile {
    protected boolean walkable = true;
    protected TileType type;

    public boolean isWalkable() {
        return walkable;
    }

    // Called when a player interacts with this tile.
    public abstract void onInteract(Player player, World world, int x, int y);

    // Called when a player steps on this tile.
    public void onStep(Player player, World world, int x, int y) {
    }

    // Getters and Setters
    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public TileType getTileTypeField() {
        return type;
    }
}