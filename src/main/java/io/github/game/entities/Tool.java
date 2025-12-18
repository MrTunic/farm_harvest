package io.github.game.entities;

import javafx.scene.image.Image;

public abstract class Tool {
    private final String name;
    private final Image sprite;
    private final ToolType type;


    protected Tool(String name, Image sprite, ToolType type) {
        this.name = name;
        this.sprite = sprite;
        this.type = type;
    }

    public ToolType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Image getSprite() {
        return sprite;
    }
}
