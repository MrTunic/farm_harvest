// ---------------------------
// File: src/main/java/io/github/game/crops/Wheat.java
// ---------------------------
package io.github.game.crops;

/**
 * Represents a wheat crop.
 * Growth takes ~3 days.
 */
public class Wheat extends Crop {

    /** Constructor sets the maximum growth stage. */
    public Wheat() {
        this.maxStage = 5;
    }

    /**
     * Defines daily growth for wheat.
     * 5 stages / 3 days ≈ 1.7 stages per day.
     *
     * @return growth per day
     */
    @Override
    protected double getDailyGrowth() {
        return 1.7;
    }

    /**
     * Returns the harvest yield for wheat.
     *
     * @return 3 if fully grown, otherwise 1
     */
    @Override
    public int getHarvestYield() {
        return isFullyGrown() ? 3 : 1;
    }
}
