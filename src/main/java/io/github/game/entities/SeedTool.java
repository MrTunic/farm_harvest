// ---------------------------
// File: src/main/java/io/github/game/entities/SeedTool.java
// ---------------------------
package io.github.game.entities;

import io.github.game.crops.Crop;
import io.github.game.crops.Tomato;
import io.github.game.crops.Wheat;
import javafx.scene.image.Image;

/**
 * Tool used for planting seeds into tilled soil.
 * Each SeedTool corresponds to a specific {@link SeedType}.
 */
public class SeedTool extends Tool {

    private final SeedType seedType;

    /**
     * Constructs a SeedTool for a specific crop type.
     *
     * @param seedType type of seed
     * @param sprite   icon image for the seed
     */
    public SeedTool(SeedType seedType, Image sprite) {
        super(seedType.name() + " Seeds", sprite, ToolType.SEED);
        this.seedType = seedType;
    }

    /**
     * @return the type of seed this tool plants
     */
    public SeedType getSeedType() {
        return seedType;
    }

    /**
     * Creates a new Crop instance corresponding to the seed type.
     *
     * @return a newly planted Crop
     */
    public Crop createCrop() {
        return switch (seedType) {
            case WHEAT -> new Wheat();
            case TOMATO -> new Tomato();
        };
    }
}
