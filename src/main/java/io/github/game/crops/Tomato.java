// ---------------------------
// File: src/main/java/io/github/game/crops/Tomato.java
// ---------------------------
package io.github.game.crops;

public class Tomato extends Crop {

    public Tomato() {
        this.maxStage = 5;
    }

    /** Tomatoes take ~4 days */
    @Override
    protected double getDailyGrowth() {
        return 1.25;
    }

    @Override
    public int getHarvestYield() {
        return isFullyGrown() ? 2 : 1;
    }
}
