// ---------------------------
// File: src/main/java/io/github/game/world/World.java
// ---------------------------
package io.github.game.world;

import io.github.game.entities.Player;
import io.github.game.world.tiles.AbstractTile;
import io.github.game.world.tiles.GrassTile;
import io.github.game.world.tiles.WaterTile;

public class World {
    private final int width;
    private final int height;
    private final AbstractTile[][] tiles;
    private final Player player;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new AbstractTile[width][height];
        initTiles();
        player = new Player(1, 1);
    }

    private void initTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) tiles[x][y] = new WaterTile();
                else tiles[x][y] = new GrassTile();
            }
        }
    }

    public void update() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                AbstractTile t = tiles[x][y];
                if (t instanceof io.github.game.world.tiles.DirtTile dirtTile) {
                    dirtTile.tick();
                }
            }
        }
    }

    public AbstractTile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return null;
        return tiles[x][y];
    }

    public void setTile(int x, int y, AbstractTile tile) {
        if (x < 0 || y < 0 || x >= width || y >= height) return;
        tiles[x][y] = tile;
    }

    public Player getPlayer() { return player; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}