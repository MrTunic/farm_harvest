// ---------------------------
// File: src/main/java/io/github/game/world/World.java
// ---------------------------
package io.github.game.world;

import io.github.game.entities.Player;
import io.github.game.world.tiles.AbstractTile;
import io.github.game.world.tiles.DirtTile;
import io.github.game.world.tiles.GrassTile;
import io.github.game.world.tiles.WaterTile;

public class World {
    private final int width;
    private final int height;
    private final AbstractTile[][] tiles;
    private final Player player;

    // Day/Night cycle
    private final DayCycle dayCycle; // manages day/night timing
    
    // private int dayTick = 0; // current tick in the day/night cycle
    // private int dayCount = 0;

    public World(int width, int height, int dayLength, int nightLength) {
        this.width = width;
        this.height = height;
        tiles = new AbstractTile[width][height];
        initTiles();
        player = new Player(1, 1);
        dayCycle = new DayCycle(dayLength, nightLength);
    }

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
   /** Called every game tick */
    public void update() {
        dayCycle.tick();    // advance the day/night cycle

        if (dayCycle.getCurrentTick() == 0) {
            onNewDay();   // trigger crops
        }

        // Future uses:
        // - particle systems
        // - weather timers
        // - NPC movement
        // - temporary effects.
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

    /**
     * Returns the alpha value for the night overlay.
     * 0 = fully day, 1 = fully night.
     * Smooth transitions at day/night boundaries.
     */
    // public double getDayLightAlpha() {
    // if (dayTick < DAY_LENGTH) {
    // // last 50 ticks of day fade into night
    // int fadeStart = DAY_LENGTH - 50;
    // if (dayTick >= fadeStart) {
    // return (dayTick - fadeStart) / 50.0; // 0 → 1
    // }
    // return 0;
    // } else {
    // // night -> day fade
    // int nightTick = dayTick - DAY_LENGTH;
    // if (nightTick < NIGHT_LENGTH) {
    // return 1 - (nightTick / (double) NIGHT_LENGTH); // 1 → 0
    // }
    // return 0;
    // }
    // }

    /** Get tile at x,y */
    public AbstractTile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return null;
        return tiles[x][y];
    }

    /** Replace tile at x,y */
    public void setTile(int x, int y, AbstractTile tile) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return;
        tiles[x][y] = tile;
    }

    /** World getters */
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
