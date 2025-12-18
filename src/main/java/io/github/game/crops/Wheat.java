// ---------------------------
// File: src/main/java/io/github/game/crops/Wheat.java
// ---------------------------
package io.github.game.crops;

public class Wheat extends Crop {
    
    public Wheat() {
        this.maxStage = 5;
    }

    /** 
     * Wheat takes ~3 days to fully grow
     * 5 stages / 3 days ≈ 1.67 stages per day
     */
    @Override
    protected double getDailyGrowth() {
        return 1.7;
    }

    @Override
    public int getHarvestYield() {
        return isFullyGrown() ? 3 : 1;
    }
}