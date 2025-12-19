// ---------------------------
// File: src/main/java/io/github/game/crops/Growable.java
// ---------------------------
package io.github.game.crops;

/**
 * Interface for crops that can grow over time.
 */
public interface Growable { 
   
    /**
     * Called once per in-game day to advance growth.
     */
    void onNewDay();

    /**
     * Checks if the crop is fully grown.
     * 
     * @return true if fully grown, false otherwise
     */
    boolean isFullyGrown(); 
}
