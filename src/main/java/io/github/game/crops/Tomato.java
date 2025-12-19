// ---------------------------
// File: src/main/java/io/github/game/crops/Tomato.java
// ---------------------------
package io.github.game.crops;

/**
 * Represents a tomato crop.
 * Growth takes ~5 days.
 */
public class Tomato extends Crop {

    /** Constructor sets the maximum growth stage. */
    public Tomato() {
        this.maxStage = 5;
    }

    /**
     * Defines daily growth for tomatoes.
     *
     * @return growth per day
     */
    @Override
    protected double getDailyGrowth() {
        return 1;
    }

    /**
     * Returns the harvest yield for tomatoes.
     *
     * @return 2 if fully grown, otherwise 1
     */
    @Override
    public int getHarvestYield() {
        return isFullyGrown() ? 2 : 1;
    }
}
