// ---------------------------
// File: src/main/java/io/github/game/entities/HoeTool.java
// ---------------------------
package io.github.game.entities;

import javafx.scene.image.Image;

// HoeTool class representing a hoe tool
public class HoeTool extends Tool {
    public HoeTool(Image icon) {
        super("Hoe", icon, ToolType.HOE);
    }
}
