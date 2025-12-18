package io.github.game.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.game.crops.Crop;
import io.github.game.crops.Tomato;
import io.github.game.crops.Wheat;
import io.github.game.entities.Player;
import io.github.game.entities.PlayerAction;
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

    // Tiles
    private final Image grassImg;
    private final Image dirtImg;
    private final Image waterImg;

    // Crops
    private final Image[] wheatStages = new Image[6];
    private final Image[] tomatoStages = new Image[6];

    // Player animations
    private final Image[][] walkSprites = new Image[4][4];
    private final Image[][] hoeSprites = new Image[4][2];
    private static final int WALK_SIZE = 40;
    private static final int HOE_SIZE = 60;

    // UI
    private final List<FlyingItem> flyingItems = new ArrayList<>();

    public Renderer(World world, Canvas canvas, Player player) {
        this.world = world;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        // Tiles
        grassImg = ResourceManager.loadImage("tiles/grass.png");
        dirtImg = ResourceManager.loadImage("tiles/dirt.png");
        waterImg = ResourceManager.loadImage("tiles/water.png");

        // Crops
        for (int i = 0; i < 6; i++) {
            wheatStages[i] = ResourceManager.loadImage("crops/wheat_stage_" + i + ".png");
            tomatoStages[i] = ResourceManager.loadImage("crops/tomato_stage_" + i + ".png");
        }

        // Player animations
        for (int d = 0; d < 4; d++) {
            for (int f = 0; f < 4; f++)
                walkSprites[d][f] = ResourceManager.loadImage("player/walk_" + d + "_" + f + ".png");
            for (int f = 0; f < 2; f++)
                hoeSprites[d][f] = ResourceManager.loadImage("player/hoe_" + d + "_" + f + ".png");
        }

        // Pickup animation callback
        player.setPickupCallback(pickup -> spawnPickup(pickup.cropType, pickup.tileX, pickup.tileY, pickup.img));

        requestRender();
    }

    public void requestRender() {
        Platform.runLater(this::render);
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawWorld();
        drawFlyingItems();
        drawPlayer();
        drawInventory(world.getPlayer());
        drawToolBar(world.getPlayer());
        drawNightOverlay();
        drawDayCounter();
    }

    // ---------------- WORLD ----------------

    private void drawWorld() {
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                AbstractTile t = world.getTile(x, y);
                Image base = switch (t.getType()) {
                    case GRASS -> grassImg;
                    case DIRT -> dirtImg;
                    case WATER -> waterImg;
                };
                drawImage(base, x, y);

                if (t instanceof io.github.game.world.tiles.DirtTile dirt && dirt.hasCrop()) {
                    Crop c = dirt.getCrop();
                    int stage = c.getGrowthStage();
                    Image img = (c instanceof Wheat) ? wheatStages[stage]
                            : (c instanceof Tomato) ? tomatoStages[stage] : null;
                    if (img != null)
                        drawImage(img, x, y);
                }
            }
        }
    }

    // ---------------- PLAYER ----------------

    private void drawPlayer() {
        Player p = world.getPlayer();
        p.update(); // advance animation

        int dir = p.getDirection().ordinal();
        int frame = p.getAnimFrame();

        Image sprite = (p.getAction() == PlayerAction.HOEING)
                ? hoeSprites[dir][frame]
                : walkSprites[dir][frame];

        int size = (p.getAction() == PlayerAction.HOEING)
                ? 120
                : tileSize;

        gc.drawImage(sprite,
                p.getRenderX() * tileSize - (size - tileSize) / 2,
                p.getRenderY() * tileSize - (size - tileSize) / 2,
                size,
                size);
    }

    // ---------------- PICKUPS ----------------

    private void drawFlyingItems() {
        Iterator<FlyingItem> it = flyingItems.iterator();
        while (it.hasNext()) {
            FlyingItem item = it.next();
            if (item.update()) {
                it.remove();
                continue;
            }
            gc.drawImage(item.image, item.x, item.y, 24, 24);
        }
    }

    // ---------------- UI ----------------

    private void drawToolBar(Player p) {
        int slotSize = 35;
        int spacing = 10;
        int startX = ((int) canvas.getWidth() -
                p.getTools().size() * (slotSize + spacing)) / 2;
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
        int slotSize = 35;
        int spacing = 20;
        int startX = 3;
        int startY = 200;

        String[] crops = { "wheat", "tomato" };

        for (int i = 0; i < crops.length; i++) {
            String crop = crops[i];
            int qty = p.getInventory().get(crop);
            int y = startY + i * (slotSize + spacing);

            Image img = crop.equals("wheat") ? wheatStages[5] : tomatoStages[5];

            if (qty == 0) {
                gc.save();
                gc.setGlobalAlpha(0.3);
                gc.drawImage(img, startX, y, slotSize, slotSize);
                gc.restore();
            } else {
                gc.drawImage(img, startX, y, slotSize, slotSize);
            }

            gc.setFill(Color.WHITE);
            gc.fillText(String.valueOf(qty),
                    startX + slotSize / 2 - 4,
                    y + slotSize + 12);
        }
    }

    private void drawNightOverlay() {
        double alpha = world.getDayCycle().getNightAlpha();
        if (alpha > 0) {
            gc.setFill(Color.rgb(0, 0, 50, alpha));
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    private void drawImage(Image img, int tx, int ty) {
        gc.drawImage(img, tx * tileSize, ty * tileSize, tileSize, tileSize);
    }

    private void drawDayCounter() {
        int canvasWidth = (int) canvas.getWidth();

        // Fetch day info from DayCycle
        int dayCount = world.getDayCycle().getDayCount();
        int dayTick = world.getDayCycle().getCurrentTick();
        int dayLength = world.getDayCycle().getDayLength();

        // Compute percent of day passed
        double percent;
        if (dayTick < dayLength) {
            percent = (dayTick / (double) dayLength) * 100;
        } else {
            percent = 100;
        }

        String text = "Day " + dayCount + " - " + (int) percent + "%";

        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeText(text, canvasWidth / 2.0 - 30, 20);
        gc.fillText(text, canvasWidth / 2.0 - 30, 20);
    }

    // ---------------- PICKUP SPAWN ----------------

    public void spawnPickup(String cropType, int tileX, int tileY, Image img) {
        int startX = 3;
        int startY = 200;
        int spacing = 20;
        int slotSize = 35;

        int index = cropType.equals("tomato") ? 1 : 0;

        flyingItems.add(new FlyingItem(
                tileX * tileSize,
                tileY * tileSize,
                startX,
                startY + index * (slotSize + spacing),
                img));
    }

    private static class FlyingItem {
        double x, y;
        final double tx, ty;
        double progress = 0;
        final Image image;

        FlyingItem(double x, double y, double tx, double ty, Image img) {
            this.x = x;
            this.y = y;
            this.tx = tx;
            this.ty = ty;
            this.image = img;
        }

        boolean update() {
            progress += 0.05;
            x += (tx - x) * 0.2;
            y += (ty - y) * 0.2;
            return progress >= 1;
        }
    }
}
