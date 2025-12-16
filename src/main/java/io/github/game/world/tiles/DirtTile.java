// ---------------------------
// File: src/main/java/io/github/game/world/tiles/DirtTile.java
// ---------------------------
package io.github.game.world.tiles;

import io.github.game.crops.Crop;
import io.github.game.entities.Player;
import io.github.game.entities.SeedTool;
import io.github.game.entities.Tool;
import io.github.game.world.World;

public class DirtTile extends AbstractTile implements io.github.game.world.interact.Interactable {
    private Crop crop;
    private int tickCounter = 0;
    private final int ticksPerGrowth = 60;

    public DirtTile() {
        this.walkable = true;
        this.type = TileType.DIRT;
        this.crop = null;
    }

    public boolean hasCrop() {
        return crop != null;
    }

    public Crop getCrop() {
        return crop;
    }

    public void plant(Crop c) {
        this.crop = c;
    }

    public void removeCrop() {
        this.crop = null;
    }

    public void tick() {
        tickCounter = (tickCounter + 1) % ticksPerGrowth;
        if (tickCounter == 0 && crop != null)
            crop.grow();
    }

    @Override
    public void onInteract(Player player, World world, int x, int y) {

        Tool tool = player.getSelectedTool();

        // Planting
        if (crop == null && tool instanceof SeedTool seedTool) {
            crop = seedTool.createCrop();
            return;
        }

        // Harvesting
        if (crop != null && crop.isFullyGrown() && tool == null) {
            int yield = crop.getHarvestYield();

            String item = crop.getClass().getSimpleName().toLowerCase();
            player.getInventory().add(item, yield);

            crop = null;
        }
    }

    @Override
    public TileType getType() {
        return TileType.DIRT;
    }
}