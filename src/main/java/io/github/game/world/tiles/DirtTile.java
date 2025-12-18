package io.github.game.world.tiles;

import io.github.game.crops.Crop;
import io.github.game.entities.Player;
import io.github.game.entities.SeedTool;
import io.github.game.entities.Tool;
import io.github.game.util.ResourceManager;
import io.github.game.world.World;
import javafx.scene.image.Image;

public class DirtTile extends AbstractTile implements io.github.game.world.interact.Interactable {
    private Crop crop;

    public DirtTile() {
        this.walkable = true;
        this.type = TileType.DIRT;
        this.crop = null;
    }

    public void onNewDay() {
        if (crop != null) {
            crop.onNewDay();
        }
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

    @Override
    public void onInteract(Player player, World world, int x, int y) {
        Tool tool = player.getSelectedTool();

        // Plant
        if (crop == null && tool instanceof SeedTool seedTool) {
            crop = seedTool.createCrop();
            return;
        }

        // Harvest
        if (crop != null && crop.isFullyGrown() && tool == null) {
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
