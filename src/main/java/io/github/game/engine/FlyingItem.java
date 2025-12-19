// ---------------------------
// File: src/main/java/io/github/game/engine/FlyingItem.java
// ---------------------------
package io.github.game.engine;

import javafx.scene.image.Image;

// FlyingItem class representing an item flying from one point to another
public class FlyingItem {
    public double x, y;
    public double targetX, targetY;
    public double progress = 0;
    public final Image image;

    // Constructor
    public FlyingItem(double x, double y, double targetX, double targetY, Image image) {
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
        this.image = image;
    }

    // Update the flying item's position. Returns true if it has reached its target.
    public boolean update() {
        progress += 0.05;
        x += (targetX - x) * 0.2;
        y += (targetY - y) * 0.2;
        return progress >= 1;
    }
}
