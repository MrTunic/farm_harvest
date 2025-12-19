// ---------------------------
// File: src/main/java/io/github/game/world/interact/Interactable.java
// ---------------------------
package io.github.game.world.interact;

import io.github.game.entities.Player;
import io.github.game.world.World;

/**
 * Interface for objects in the world that can be interacted with by the player.
 */
public interface Interactable {

    /**
     * Called when a player interacts with the object.
     *
     * @param player the interacting player
     * @param world  reference to the game world
     * @param x      tile x-coordinate
     * @param y      tile y-coordinate
     */
    void onInteract(Player player, World world, int x, int y);
}