// ---------------------------
// File: src/main/java/io/github/game/world/tiles/DirtTile.java
// ---------------------------
package io.github.game.world.tiles;

import io.github.game.entities.Player;
import io.github.game.world.World;
import io.github.game.crops.Crop;
import io.github.game.crops.Wheat;

public class DirtTile extends AbstractTile implements io.github.game.world.interact.Interactable {
    private Crop crop;
    private int tickCounter = 0;
    private final int ticksPerGrowth = 60;

    public DirtTile() { this.walkable = true; this.type = TileType.DIRT; }

    public boolean hasCrop() { return crop != null; }
    public Crop getCrop() { return crop; }
    public void plant(Crop c) { this.crop = c; }
    public void removeCrop() { this.crop = null; }

    public void tick() {
        tickCounter = (tickCounter + 1) % ticksPerGrowth;
        if (tickCounter == 0 && crop != null) crop.grow();
    }

    @Override
    public void onInteract(Player player, World world, int x, int y) {
        if (crop == null) { plant(new Wheat()); }
        else if (crop.isFullyGrown()) {
            int yield = crop.getHarvestYield();
            player.getInventory().add("wheat", yield);
            removeCrop();
        }
    }

    @Override
    public TileType getType() { return TileType.DIRT; }
}