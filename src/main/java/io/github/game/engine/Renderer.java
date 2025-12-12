// ---------------------------
// File: src/main/java/io/github/game/engine/Renderer.java
// ---------------------------
package io.github.game.engine;

import io.github.game.crops.Crop;
import io.github.game.entities.Player;
import io.github.game.util.ResourceManager;
import io.github.game.world.World;
import io.github.game.world.tiles.AbstractTile;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public final class Renderer {
    private final World world;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final int tileSize = 40;

    private final Image grassImg;
    private final Image dirtImg;
    private final Image waterImg;
    private final Image[] wheatStages;
    private final Image playerImg;

    public Renderer(World world, Canvas canvas) {
        this.world = world;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        grassImg = ResourceManager.loadImage("tiles/grass.png");
        dirtImg = ResourceManager.loadImage("tiles/dirt.png");
        waterImg = ResourceManager.loadImage("tiles/water.png");
        playerImg = ResourceManager.loadImage("player/player.png");

        wheatStages = new Image[6];
        for (int i = 0; i < wheatStages.length; i++) {
            wheatStages[i] = ResourceManager.loadImage("tiles/wheat_stage_" + i + ".png");
        }

        requestRender();
    }

    public void requestRender() { Platform.runLater(this::render); }

    private void render() {
        int cols = world.getWidth();
        int rows = world.getHeight();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                AbstractTile t = world.getTile(x, y);
                Image base = grassImg;
                switch (t.getType()) {
                    case GRASS -> base = grassImg;
                    case DIRT -> base = dirtImg;
                    case WATER -> base = waterImg;
                }
                drawImage(base, x, y);

                // complete crop drawing block with safe checks
                if (t instanceof io.github.game.world.tiles.DirtTile dirt) {
                    if (dirt.hasCrop()) {
                        Crop c = dirt.getCrop();
                        int stage = c.getGrowthStage();
                        if (stage >= 0 && stage < wheatStages.length && wheatStages[stage] != null) {
                            drawImage(wheatStages[stage], x, y);
                        }
                    }
                }
            }
        }

        Player p = world.getPlayer();
        drawImage(playerImg, p.getX(), p.getY());

        gc.fillText("Inventory: " + p.getInventory().getItems().toString(), 10, canvas.getHeight() - 10);
    }

    private void drawImage(Image img, int tx, int ty) {
        if (img == null) return;
        gc.drawImage(img, tx * tileSize, ty * tileSize, tileSize, tileSize);
    }
}