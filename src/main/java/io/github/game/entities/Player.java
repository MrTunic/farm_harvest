// ---------------------------
// File: src/main/java/io/github/game/entities/Player.java
// ---------------------------
package io.github.game.entities;

import io.github.game.util.Inventory;
import io.github.game.world.World;

public class Player {
    private int x;
    private int y;
    private final Inventory inventory = new Inventory();

    public Player(int startX, int startY) { this.x = startX; this.y = startY; }

    public void move(int dx, int dy, World world) {
        int nx = x + dx, ny = y + dy;
        if (nx < 0 || ny < 0 || nx >= world.getWidth() || ny >= world.getHeight()) return;
        if (world.getTile(nx, ny).isWalkable()) {
            x = nx; y = ny;
            world.getTile(x, y).onStep(this, world, x, y);
        }
    }

    public void interact(World world) { world.getTile(x, y).onInteract(this, world, x, y); }

    public Inventory getInventory() { return inventory; }
    public int getX() { return x; }
    public int getY() { return y; }
}
