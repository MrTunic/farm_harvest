// ---------------------------
// File: src/main/java/io/github/game/crops/Tomato.java
// ---------------------------
package io.github.game.crops;

// Tomato class representing a tomato crop
public class Tomato extends Crop {

    public Tomato() {
        this.maxStage = 5;
    }

    /** Tomatoes take ~5 days */
    @Override
    protected double getDailyGrowth() {
        return 1;
    }

    @Override
    public int getHarvestYield() {
        return isFullyGrown() ? 2 : 1;
    }
}
