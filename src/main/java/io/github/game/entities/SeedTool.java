package io.github.game.entities;

import io.github.game.crops.Crop;
import io.github.game.crops.Tomato;
import io.github.game.crops.Wheat;
import javafx.scene.image.Image;

public class SeedTool extends Tool {

    private final SeedType seedType;

    public SeedTool(SeedType seedType, Image sprite) {
        super(seedType.name() + " Seeds", sprite);
        this.seedType = seedType;
    }

    public SeedType getSeedType() {
        return seedType;
    }

    public Crop createCrop() {
        return switch (seedType) {
            case WHEAT -> new Wheat();
            case TOMATO -> new Tomato();
        };
    }
}
