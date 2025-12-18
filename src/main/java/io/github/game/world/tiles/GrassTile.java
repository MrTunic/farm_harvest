// ---------------------------
// File: src/main/java/io/github/game/world/tiles/GrassTile.java
// ---------------------------
package io.github.game.world.tiles;

import io.github.game.entities.Player;
import io.github.game.entities.ToolType;
import io.github.game.world.World;

public class GrassTile extends AbstractTile {
    public GrassTile() { 
        this.walkable = true; 
        this.type = TileType.GRASS; }

    @Override
    public void onInteract(Player player, World world, int x, int y) {
        
        // Only till if the player is holding a hoe
    if (player.getSelectedTool() != null && 
        player.getSelectedTool().getType() == ToolType.HOE) {

        // Only till if the player has held the interaction long enough
        if (player.isInteractReady()) {
            world.setTile(x, y, new DirtTile());
         }
    }
    }

    @Override
    public TileType getType() { return TileType.GRASS; }
}
