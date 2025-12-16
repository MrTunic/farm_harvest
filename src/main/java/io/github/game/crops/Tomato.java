// ---------------------------
// File: src/main/java/io/github/game/crops/Tomato.java
// ---------------------------
package io.github.game.crops;

public class Tomato extends Crop {
    public Tomato() { 
        this.growthStage = 0; 
        this.maxStage = 5; 
    }

    @Override 
    public int getHarvestYield() { 
        return isFullyGrown() ? 3 : 1; 
    }
}