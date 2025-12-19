// ---------------------------
// File: src/main/java/io/github/game/world/interact/Interactable.java
// ---------------------------
package io.github.game.world.interact;

import io.github.game.entities.Player;
import io.github.game.world.World;

// Interactable interface for objects that can be interacted with in the game world
public interface Interactable {
    void onInteract(Player player, World world, int x, int y);
}