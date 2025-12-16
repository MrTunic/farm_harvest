// ---------------------------
// File: src/main/java/io/github/game/crops/Crop.java
// ---------------------------
package io.github.game.crops;

public abstract class Crop implements Growable {
    
    protected int growthStage = 0;
    protected int maxStage = 5;

    @Override
    public void grow() { 
        if (growthStage < maxStage) 
            growthStage++; 
    }

    @Override
    public boolean isFullyGrown() { 
        return growthStage >= maxStage; 
    }

    public int getGrowthStage() { 
        return growthStage; 
    }

    public abstract int getHarvestYield();
}