package io.github.game.world;

import io.github.game.abstracts.AbstractCrop;
import io.github.game.utils.ResourceManager;
import javafx.scene.image.Image;

public class Crop extends AbstractCrop {

    private final String name;

    public Crop(String name) {
        this.name = name;

        this.stages = new Image[] {
                ResourceManager.load("tiles/crop.png")
        };
    }

    @Override
    public void grow() {
        if (stage < stages.length - 1) stage++;
    }
}
