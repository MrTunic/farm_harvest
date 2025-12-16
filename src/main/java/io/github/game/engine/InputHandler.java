// ---------------------------
// File: src/main/java/io/github/game/engine/InputHandler.java
// ---------------------------
package io.github.game.engine;

import io.github.game.entities.Player;
import io.github.game.world.World;
import javafx.scene.input.KeyCode;

public class InputHandler {
    private final World world;
    private final Renderer renderer;

    public InputHandler(World world, Renderer renderer) {
        this.world = world;
        this.renderer = renderer;
    }

    public void onKeyPressed(KeyCode code) {
        Player p = world.getPlayer();
        switch (code) {
            case UP, W -> p.move(0, -1, world);
            case DOWN, S -> p.move(0, 1, world);
            case LEFT, A -> p.move(-1, 0, world);
            case RIGHT, D -> p.move(1, 0, world);
            case E -> p.interact(world);

            case DIGIT1 -> p.selectTool(-1); // hand
            case DIGIT2 -> p.selectTool(0); // hoe
            case DIGIT3 -> p.selectTool(1); // wheat
            case DIGIT4 -> p.selectTool(2); // tomato

            default -> {
            }
        }
        renderer.requestRender();
    }

    public void onKeyReleased(KeyCode code) {
    }
}