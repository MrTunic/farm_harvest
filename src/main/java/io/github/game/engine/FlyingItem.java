// ---------------------------
// File: src/main/java/io/github/game/engine/FlyingItem.java
// ---------------------------
package io.github.game.engine;

import javafx.scene.image.Image;

/**
 * Represents an item flying from one position to another on the screen.
 * Typically used for visual feedback when a player collects a crop or item.
 */
public class FlyingItem {
    /** Current X position. */
    public double x;
    /** Current Y position. */
    public double y;
    /** Target X position to fly toward. */
    public double targetX;
    /** Target Y position to fly toward. */
    public double targetY;
    /** Progress of the animation (0.0–1.0). */
    public double progress = 0;
    /** Image of the item. */
    public final Image image;

    /**
     * Constructor.
     *
     * @param x       starting X position
     * @param y       starting Y position
     * @param targetX target X position
     * @param targetY target Y position
     * @param image   item image
     */
    public FlyingItem(double x, double y, double targetX, double targetY, Image image) {
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
        this.image = image;
    }

    /**
     * Updates the flying item's position toward its target.
     *
     * @return true if the item has reached its target, false otherwise
     */
    public boolean update() {
        progress += 0.05;
        x += (targetX - x) * 0.2;
        y += (targetY - y) * 0.2;
        return progress >= 1;
    }
}
