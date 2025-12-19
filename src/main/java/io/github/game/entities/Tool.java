// ---------------------------
// File: src/main/java/io/github/game/entities/Tool.java
// ---------------------------
package io.github.game.entities;

import javafx.scene.image.Image;

// Abstract Tool class representing a general tool in the game
public abstract class Tool {
    private final String name;
    private final Image sprite;
    private final ToolType type;

    // Constructor for Tool
    protected Tool(String name, Image sprite, ToolType type) {
        this.name = name;
        this.sprite = sprite;
        this.type = type;
    }

    // Getters for Tool properties
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
