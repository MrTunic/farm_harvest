// ---------------------------
// File: src/main/java/io/github/game/world/tiles/GrassTile.java
// ---------------------------
package io.github.game.world.tiles;

import io.github.game.entities.Player;
import io.github.game.world.World;

public class GrassTile extends AbstractTile {
    public GrassTile() { this.walkable = true; this.type = TileType.GRASS; }

    @Override
    public void onInteract(Player player, World world, int x, int y) {
        world.setTile(x, y, new DirtTile());
    }

    @Override
    public TileType getType() { return TileType.GRASS; }
}