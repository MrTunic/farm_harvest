// ---------------------------
// File: src/main/java/io/github/game/world/World.java
// ---------------------------
package io.github.game.world;

import io.github.game.entities.Player;
import io.github.game.world.tiles.AbstractTile;
import io.github.game.world.tiles.DirtTile;
import io.github.game.world.tiles.GrassTile;
import io.github.game.world.tiles.WaterTile;

// Creates and manages the game world, including tiles and day/night cycle
public class World {
    private final int width;
    private final int height;
    private final AbstractTile[][] tiles;
    private final Player player;

    // Day/Night cycle
    private final DayCycle dayCycle; // manages day/night timing

    // World constructor
    public World(int width, int height, int dayLength, int nightLength) {
        this.width = width;
        this.height = height;
        tiles = new AbstractTile[width][height];
        initTiles();
        player = new Player(1, 1);
        dayCycle = new DayCycle(dayLength, nightLength);
    }

    // Tile initialization
    private void initTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    tiles[x][y] = new WaterTile();
                } else {
                    tiles[x][y] = new GrassTile();
                }
            }
        }
    }

    /** Update world logic: crops, tiles, etc. */
    public void update() {
        dayCycle.tick(); // advance the day/night cycle

        if (dayCycle.getCurrentTick() == 0) {
            onNewDay(); // trigger crops
        }
    }

    private void onNewDay() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y] instanceof DirtTile dirt && dirt.hasCrop()) {
                    dirt.onNewDay();
                }
            }
        }
    }

    // Get tile at x,y
    public AbstractTile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return null;
        return tiles[x][y];
    }

    // Set tile at x,y
    public void setTile(int x, int y, AbstractTile tile) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return;
        tiles[x][y] = tile;
    }

    // World getters
    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public DayCycle getDayCycle() {
        return dayCycle;
    }

}
