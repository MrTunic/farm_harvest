// ---------------------------
// File: src/main/java/io/github/game/entities/HoeTool.java
// ---------------------------
package io.github.game.entities;

import javafx.scene.image.Image;

/**
 * Tool representing a hoe used to till soil.
 */
public class HoeTool extends Tool {

    /**
     * Creates a new HoeTool.
     *
     * @param icon sprite image for the hoe
     */
    public HoeTool(Image icon) {
        super("Hoe", icon, ToolType.HOE);
    }
}
