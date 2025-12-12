// ---------------------------
// File: src/main/java/io/github/game/crops/Wheat.java
// ---------------------------
package io.github.game.crops;

public class Wheat extends Crop {
    public Wheat() { this.growthStage = 0; this.maxStage = 5; }
    @Override public int getHarvestYield() { return isFullyGrown() ? 3 : 1; }
}