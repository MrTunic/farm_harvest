// ---------------------------
// File: src/main/java/io/github/game/crops/Growable.java
// ---------------------------
package io.github.game.crops;

public interface Growable { 
   
    /** Called once per in-game day */
    void onNewDay();

    boolean isFullyGrown(); 
}