// ---------------------------
// File: src/main/java/io/github/game/world/tiles/AbstractTile.java
// ---------------------------
package io.github.game.world.tiles;

import io.github.game.entities.Player;
import io.github.game.world.World;

public abstract class AbstractTile {
    protected boolean walkable = true;
    protected TileType type; // FIX #1: each tile stores its TileType

    public boolean isWalkable() { return walkable; }

    public abstract void onInteract(Player player, World world, int x, int y);

    public void onStep(Player player, World world, int x, int y) { }

    public abstract TileType getType();

    public TileType getTileTypeField() { return type; } // optional accessor
}