package io.github.game.abstracts;

import io.github.game.interfaces.Growable;
import javafx.scene.image.Image;

public abstract class AbstractCrop implements Growable {

    protected int stage = 0;
    protected Image[] stages;

    public Image getCurrentStageImage() {
        return stages[stage];
    }
}
