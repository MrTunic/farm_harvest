package io.github.game.entities;

import javafx.scene.image.Image;

public abstract class Tool {
    private final String name;
    private final Image sprite;

    protected Tool(String name, Image sprite) {
        this.name = name;
        this.sprite = sprite;
    }

    public String getName() {
        return name;
    }

    public Image getSprite() {
        return sprite;
    }
}
