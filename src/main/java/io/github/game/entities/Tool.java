// ---------------------------
// File: src/main/java/io/github/game/entities/Tool.java
// ---------------------------
package io.github.game.entities;

import javafx.scene.image.Image;

/**
 * Abstract base class for all tools usable by the player.
 * Each tool has a name, icon sprite, and tool type.
 */
public abstract class Tool {
    private final String name;
    private final Image sprite;
    private final ToolType type;

    /**
     * Constructs a new Tool.
     *
     * @param name   display name of the tool
     * @param sprite icon image used in UI
     * @param type   tool category
     */
    protected Tool(String name, Image sprite, ToolType type) {
        this.name = name;
        this.sprite = sprite;
        this.type = type;
    }

    // Getters for Tool properties
    /**
     * @return the tool's category
     */
    public ToolType getType() {
        return type;
    }

    /**
     * @return the display name of the tool
     */
    public String getName() {
        return name;
    }

    /**
     * @return the sprite image used for rendering
     */
    public Image getSprite() {
        return sprite;
    }
}
