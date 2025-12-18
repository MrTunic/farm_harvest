package io.github.game.engine;

import io.github.game.entities.Direction;
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
            case W, UP -> p.move(0, -1, world, Direction.UP);
            case S, DOWN -> p.move(0, 1, world, Direction.DOWN);
            case A, LEFT -> p.move(-1, 0, world, Direction.LEFT);
            case D, RIGHT -> p.move(1, 0, world, Direction.RIGHT);
            
            case E -> {
                p.startInteractHold();
                p.interact(world); // Starts the hoe animation
            }

            case DIGIT1 -> p.selectTool(-1);
            case DIGIT2 -> p.selectTool(0);
            case DIGIT3 -> p.selectTool(1);
            case DIGIT4 -> p.selectTool(2);
            default -> {}
        }

        renderer.requestRender();
    }

    public void onKeyReleased(KeyCode code) {
        if (code == KeyCode.E) {
        world.getPlayer().stopInteractHold();
        }

        world.getPlayer().stopMoving();
    }
}
