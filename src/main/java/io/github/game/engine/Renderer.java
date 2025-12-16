package io.github.game.engine;

import io.github.game.crops.Crop;
import io.github.game.crops.Tomato;
import io.github.game.crops.Wheat;
import io.github.game.entities.Player;
import io.github.game.entities.Tool;
import io.github.game.util.ResourceManager;
import io.github.game.world.World;
import io.github.game.world.tiles.AbstractTile;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public final class Renderer {
    private final World world;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final int tileSize = 40;

    private final Image grassImg;
    private final Image dirtImg;
    private final Image waterImg;
    private final Image[] wheatStages;
    private final Image[] tomatoStages;
    private final Image playerImg;

    private final Image hoeImg;
    private final Image wheatSeedImg;
    private final Image tomatoSeedImg;

    public Renderer(World world, Canvas canvas) {
        this.world = world;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        grassImg = ResourceManager.loadImage("tiles/grass.png");
        dirtImg = ResourceManager.loadImage("tiles/dirt.png");
        waterImg = ResourceManager.loadImage("tiles/water.png");
        playerImg = ResourceManager.loadImage("player/player.png");

        wheatStages = new Image[6];
        tomatoStages = new Image[6];

        for (int i = 0; i < 6; i++) {
            wheatStages[i] = ResourceManager.loadImage("crops/wheat_stage_" + i + ".png");
            tomatoStages[i] = ResourceManager.loadImage("crops/tomato_stage_" + i + ".png");
        }

        hoeImg = ResourceManager.loadImage("tools/hoe.png");
        wheatSeedImg = ResourceManager.loadImage("crops/wheat_stage_0.png");
        tomatoSeedImg = ResourceManager.loadImage("crops/tomato_stage_0.png");

        requestRender();
    }

    public void requestRender() {
        Platform.runLater(this::render);
    }

    private void render() {
        int cols = world.getWidth();
        int rows = world.getHeight();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw tiles & crops
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                AbstractTile t = world.getTile(x, y);
                Image base = switch (t.getType()) {
                    case GRASS -> grassImg;
                    case DIRT -> dirtImg;
                    case WATER -> waterImg;
                };
                drawImage(base, x, y);

                // Draw crop if present
                if (t instanceof io.github.game.world.tiles.DirtTile dirt && dirt.hasCrop()) {
                    Crop c = dirt.getCrop();
                    int stage = c.getGrowthStage();
                    Image cropImg = null;
                    if (c instanceof Wheat && stage >= 0 && stage < wheatStages.length)
                        cropImg = wheatStages[stage];
                    else if (c instanceof Tomato && stage >= 0 && stage < tomatoStages.length)
                        cropImg = tomatoStages[stage];

                    if (cropImg != null)
                        drawImage(cropImg, x, y);
                }
            }
        }

        // Draw player
        Player p = world.getPlayer();
        drawImage(playerImg, p.getX(), p.getY());

        // Draw inventory (left side)
        drawInventory(p);

        // Draw bottom toolbar (tools)
        drawToolBar(p);
    }

    private void drawImage(Image img, int tx, int ty) {
        if (img == null)
            return;
        gc.drawImage(img, tx * tileSize, ty * tileSize, tileSize, tileSize);
    }

    private void drawToolBar(Player p) {
        int slotSize = 35;
        int spacing = 10;
        int totalWidth = p.getTools().size() * (slotSize + spacing);

        int startX = ((int) canvas.getWidth() - totalWidth) / 2;
        int y = (int) canvas.getHeight() - slotSize - 2;

        for (int i = 0; i < p.getTools().size(); i++) {
            Tool tool = p.getTools().get(i);
            int x = startX + i * (slotSize + spacing);

            gc.drawImage(tool.getSprite(), x, y, slotSize, slotSize);

            if (p.getSelectedToolIndex() == i) {
                gc.setStroke(Color.YELLOW);
                gc.strokeRect(x, y, slotSize, slotSize);
            }
        }
    }

    private void drawInventory(Player p) {
        int panelPadding = 6;
        int slotSize = 35; // smaller icons
        int spacing = 20; // vertical spacing
        int startX = 3;
        int startY = 200;

        // --- Define all possible crops to show ---
        String[] crops = { "wheat", "tomato" };
        int numItems = crops.length;

        // --- Draw semi-transparent background panel ---
        // gc.setFill(Color.rgb(50, 50, 50, 0.7));
        // gc.fillRoundRect(
                // startX - panelPadding,
                // startY - panelPadding,
                // slotSize + 2 * panelPadding,
                // numItems * (slotSize + spacing) + 2 * panelPadding,
                // 10, 10);

        int i = 0;
        for (String name : crops) {
            int quantity = p.getInventory().get(name); // returns 0 if not picked up
            int y = startY + i * (slotSize + spacing);

            // --- Select stage-5 crop image ---
            Image img = switch (name) {
                case "wheat" -> wheatStages[5];
                case "tomato" -> tomatoStages[5];
                default -> null;
            };

            if (img != null) {
                // --- Draw grayed-out if quantity = 0 ---
                if (quantity == 0) {
                    gc.save();
                    gc.setGlobalAlpha(0.3);
                    gc.drawImage(img, startX, y, slotSize, slotSize);
                    gc.restore();
                } else {
                    gc.drawImage(img, startX, y, slotSize, slotSize);
                }
            }

            // --- Draw count below icon ---
            gc.setFill(Color.WHITE);
            gc.fillText(
                    String.valueOf(quantity),
                    startX + slotSize / 2 - 4, // center below icon
                    y + slotSize + 12);

            i++;
        }
    }
}
