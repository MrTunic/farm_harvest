// ---------------------------
// File: src/main/java/io/github/game/engine/InputHandler.java
// ---------------------------
package io.github.game.engine;

import io.github.game.entities.Direction;
import io.github.game.entities.HoeTool;
import io.github.game.entities.Player;
import io.github.game.world.World;
import javafx.scene.input.KeyCode;

/**
 * Handles keyboard input and converts it into player actions or game commands.
 */
public class InputHandler {
    private final World world;
    private final Renderer renderer;

    /** Constructor. */
    public InputHandler(World world, Renderer renderer) {
        this.world = world;
        this.renderer = renderer;
    }

    /**
     * Handles key press events.
     *
     * @param code key code pressed
     */
    public void onKeyPressed(KeyCode code) {
        Player p = world.getPlayer();

        switch (code) {
            case W, UP -> p.move(0, -1, world, Direction.UP);
            case S, DOWN -> p.move(0, 1, world, Direction.DOWN);
            case A, LEFT -> p.move(-1, 0, world, Direction.LEFT);
            case D, RIGHT -> p.move(1, 0, world, Direction.RIGHT);

            case E, SPACE -> {

                if (p.getSelectedTool() instanceof HoeTool) {
                    p.startHoeHold();
                }
                p.interact(world); // planting/harvesting works instantly
            }

            case ENTER -> renderer.toggleControlsOverlay();

            case DIGIT1 -> p.selectTool(0);
            case DIGIT2 -> p.selectTool(1);
            case DIGIT3 -> p.selectTool(2);
            case DIGIT4 -> p.selectTool(3);
            default -> {
            }
        }

        renderer.requestRender();
    }

    /**
     * Handles key release events.
     *
     * @param code key code released
     */
    public void onKeyReleased(KeyCode code) {
        Player p = world.getPlayer();
        if (code == KeyCode.SPACE && p.getSelectedTool() instanceof HoeTool) {
            p.stopHoeHold();
        }
        if (code == KeyCode.E) {
            p.stopHoeHold();
        }
        p.stopMoving();
    }
}