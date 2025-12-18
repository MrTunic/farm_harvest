// ---------------------------
// File: src/main/java/io/github/game/crops/Crop.java
// ---------------------------
package io.github.game.crops;

public abstract class Crop implements Growable {

    protected int growthStage = 0;
    protected int maxStage = 5;
    protected double growthProgress = 0.0;

    /** Called once per in-game day */
    @Override
    public void onNewDay() {
        growthProgress += getDailyGrowth();

        while (growthProgress >= 1.0 && growthStage < maxStage) {
            growthStage++;
            growthProgress -= 1.0;
        }
    }
    /** Daily growth rate based on days per stage */
    protected abstract double getDailyGrowth();    
    
    @Override
    public boolean isFullyGrown() {
        return growthStage >= maxStage;
    }

    public int getGrowthStage() {
        return growthStage;
    }

    public abstract int getHarvestYield();
}