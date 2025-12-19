// ---------------------------
// File: src/main/java/io/github/game/entities/Player.java
// ---------------------------
package io.github.game.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import io.github.game.util.Inventory;
import io.github.game.world.World;
import javafx.scene.image.Image;

// Player class representing the player entity
public class Player {
    private int x, y;
    private double renderX, renderY;
    private double targetX, targetY;

    private static final double MOVE_SPEED = 0.15;

    private Direction direction = Direction.DOWN;
    private PlayerAction action = PlayerAction.IDLE;

    private int animTick = 0;
    private int animFrame = 0;

    private static final int WALK_ANIM_SPEED = 10;
    private static final int HOE_ANIM_SPEED = 12;

    private final Inventory inventory = new Inventory();
    private Tool selectedTool;
    private final List<Tool> tools = new ArrayList<>();
    private int selectedToolIndex = -1;

    private static final int TILL_TIME_TICKS = 60; // 1 second @ 60 TPS
    private int interactHoldTicks = 0;
    private boolean holdingInteract = false;

    private Consumer<PickupRequest> pickupCallback;

    // Constructor
    public Player(int startX, int startY) {
        x = startX;
        y = startY;

        renderX = x;
        renderY = y;
        targetX = x;
        targetY = y;
    }

    // Update player state
    public void update() {
        // Smooth movement
        renderX += (targetX - renderX) * MOVE_SPEED;
        renderY += (targetY - renderY) * MOVE_SPEED;

        // Stop walking when close enough
        if (Math.abs(renderX - targetX) < 0.01 &&
                Math.abs(renderY - targetY) < 0.01) {
            renderX = targetX;
            renderY = targetY;

            if (action == PlayerAction.WALKING && animFrame == 0) {
                action = PlayerAction.IDLE;
            }
        }

        // Animation ticking
        animTick++;
        int speed = (action == PlayerAction.HOEING) ? HOE_ANIM_SPEED : WALK_ANIM_SPEED;

        if (animTick >= speed) {
            animTick = 0;
            animFrame++;
        }

        int maxFrames = (action == PlayerAction.HOEING) ? 2 : (action == PlayerAction.WALKING ? 4 : 1);
        animFrame %= maxFrames;

        // Interaction hold timer
        if (holdingInteract) {
            interactHoldTicks++;
        } else {
            interactHoldTicks = 0;
        }
    }

    // Player movement
    public void move(int dx, int dy, World world, Direction dir) {
        if (action == PlayerAction.WALKING || action == PlayerAction.HOEING)
            return;

        int nx = x + dx;
        int ny = y + dy;

        if (nx < 0 || ny < 0 || nx >= world.getWidth() || ny >= world.getHeight())
            return;

        if (!world.getTile(nx, ny).isWalkable())
            return;

        // Only update direction & animation after validation
        direction = dir;
        action = PlayerAction.WALKING;
        animFrame = 0;

        x = nx;
        y = ny;

        targetX = x;
        targetY = y;
    }

    public void stopMoving() {
        action = PlayerAction.IDLE;
        animFrame = 0;
    }

    // Player interaction with the world
    public void interact(World world) {

        // Only play hoeing animation if holding a hoe
        if (selectedTool != null && selectedTool.getType() == ToolType.HOE) {
            action = PlayerAction.HOEING;
            animFrame = 0;
        } else {
            action = PlayerAction.IDLE;
            // animFrame = 0;
        }

        world.getTile(x, y).onInteract(this, world, x, y);
    }

    // Interaction hold for tools like hoe
    public void startHoeHold() {
        holdingInteract = true;
    }

    public void stopHoeHold() {
        holdingInteract = false;
        interactHoldTicks = 0;
        if (action == PlayerAction.HOEING)
            action = PlayerAction.IDLE;
    }

    public boolean isInteractReady() {
        return interactHoldTicks >= TILL_TIME_TICKS;
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getRenderX() {
        return renderX;
    }

    public double getRenderY() {
        return renderY;
    }

    public Direction getDirection() {
        return direction;
    }

    public PlayerAction getAction() {
        return action;
    }

    public int getAnimFrame() {
        return animFrame;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public int getSelectedToolIndex() {
        return selectedToolIndex;
    }

    public void selectTool(int index) {
        if (index < 0 || index >= tools.size()) {
            selectedTool = null;
            selectedToolIndex = -1;
        } else {
            selectedToolIndex = index;
            selectedTool = tools.get(index);
        }

        // Stop hoeing if tool changes
        if (action == PlayerAction.HOEING) {
            action = PlayerAction.IDLE;
            animFrame = 0;
        }
    }

    // --- Pickup animation ---
    public void setPickupCallback(Consumer<PickupRequest> cb) {
        pickupCallback = cb;
    }

    public void requestPickupAnimation(int tileX, int tileY, String cropType, Image img) {
        if (pickupCallback != null)
            pickupCallback.accept(new PickupRequest(tileX, tileY, cropType, img));
    }

    public static class PickupRequest {
        public final int tileX, tileY;
        public final String cropType;
        public final Image img;

        public PickupRequest(int tileX, int tileY, String cropType, Image img) {
            this.tileX = tileX;
            this.tileY = tileY;
            this.cropType = cropType;
            this.img = img;
        }
    }
}
