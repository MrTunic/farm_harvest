// ---------------------------
// File: src/main/java/io/github/game/world/World.java
// ---------------------------
package io.github.game.world;

import io.github.game.entities.Player;
import io.github.game.world.tiles.AbstractTile;
import io.github.game.world.tiles.DirtTile;
import io.github.game.world.tiles.GrassTile;
import io.github.game.world.tiles.WaterTile;

/**
 * Represents the game world.
 * Manages tiles, player state, and the day/night cycle.
 */
public class World {

    private final int width;
    private final int height;
    private final AbstractTile[][] tiles;
    private final Player player;
    private final DayCycle dayCycle;

    /**
     * Constructs a new game world.
     *
     * @param width       world width in tiles
     * @param height      world height in tiles
     * @param dayLength   ticks per day
     * @param nightLength ticks per night
     */
    public World(int width, int height, int dayLength, int nightLength) {
        this.width = width;
        this.height = height;
        tiles = new AbstractTile[width][height];

        initTiles();

        player = new Player(1, 1);
        dayCycle = new DayCycle(dayLength, nightLength);
    }

    /**
     * Initializes world tiles.
     */
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

    /**
     * Updates world state each tick.
     */
    public void update() {
        dayCycle.tick(); // advance the day/night cycle

        if (dayCycle.getCurrentTick() == 0) {
            onNewDay(); // trigger crops
        }
    }

    /**
     * Triggers crop growth for a new day.
     */
    private void onNewDay() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y] instanceof DirtTile dirt && dirt.hasCrop()) {
                    dirt.onNewDay();
                }
            }
        }
    }

    /**
     * Returns the tile at the given world coordinates.
     * Returns null if the coordinates are out of bounds.
     */
    public AbstractTile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return null;
        return tiles[x][y];
    }

    /**
     * Replaces the tile at the given coordinates if they are in bounds.
     */
    public void setTile(int x, int y, AbstractTile tile) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return;
        tiles[x][y] = tile;
    }

    // Getters
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
