// ---------------------------
// File: src/main/java/io/github/game/crops/Growable.java
// ---------------------------
package io.github.game.crops;

// Growable interface for crops that can grow over time
public interface Growable { 
   
    /** Called once per in-game day */
    void onNewDay();

    boolean isFullyGrown(); 
}