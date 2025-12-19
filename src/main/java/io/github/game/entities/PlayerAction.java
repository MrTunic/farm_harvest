// ---------------------------
// File: src/main/java/io/github/game/entities/PlayerAction.java
// ---------------------------
package io.github.game.entities;

/**
 * Represents the current action state of the player.
 * Used to control animations and interaction behavior.
 */
public enum PlayerAction {

    /** Player is standing still. */
    IDLE,

    /** Player is moving between tiles. */
    WALKING,

    /** Player is using a hoe on a tile. */
    HOEING
}
