// ---------------------------
// File: src/main/java/io/github/game/world/tiles/DirtTile.java
// ---------------------------
package io.github.game.world.tiles;

import io.github.game.crops.Crop;
import io.github.game.entities.Player;
import io.github.game.entities.SeedTool;
import io.github.game.entities.Tool;
import io.github.game.util.ResourceManager;
import io.github.game.world.World;
import javafx.scene.image.Image;

/**
 * Represents a farmable dirt tile.
 * Dirt tiles can hold crops and support planting and harvesting.
 */
public class DirtTile extends AbstractTile implements io.github.game.world.interact.Interactable {
    private Crop crop;

    /**
     * Constructs an empty dirt tile.
     */
    public DirtTile() {
        this.walkable = true;
        this.type = TileType.DIRT;
        this.crop = null;
    }

    /**
     * Advances crop growth when a new in-game day begins.
     */
    public void onNewDay() {
        if (crop != null) {
            crop.onNewDay();
        }
    }

    /**
     * Checks whether a crop is planted on this tile.
     *
     * @return true if a crop exists
     */
    public boolean hasCrop() {
        return crop != null;
    }

    /**
     * Returns the planted crop.
     *
     * @return crop or null
     */
    public Crop getCrop() {
        return crop;
    }

    /**
     * Plants a crop on this tile.
     *
     * @param c crop to plant
     */
    public void plant(Crop c) {
        this.crop = c;
    }

    /**
     * Removes the planted crop.
     */
    public void removeCrop() {
        this.crop = null;
    }

    /**
     * Handles planting seeds or harvesting crops.
     */
    @Override
    public void onInteract(Player player, World world, int x, int y) {
        Tool tool = player.getSelectedTool();

        // Plant seeds
        if (crop == null && tool instanceof SeedTool seedTool) {
            crop = seedTool.createCrop();
            return;
        }

        // Harvest crop
        if (crop != null && crop.isFullyGrown()) {
            int yield = crop.getHarvestYield();

            String item = crop.getClass().getSimpleName().toLowerCase();
            player.getInventory().add(item, yield);

            // Trigger flying animation
            Image img = switch (item) {
                case "wheat" -> ResourceManager.loadImage("crops/wheat_stage_5.png");
                case "tomato" -> ResourceManager.loadImage("crops/tomato_stage_5.png");
                default -> null;
            };

            if (img != null)
                player.requestPickupAnimation(x, y, item, img);

            crop = null;
        }
    }

    @Override
    public TileType getType() {
        return TileType.DIRT;
    }
}
