// ---------------------------
// File: src/main/java/io/github/game/engine/Renderer.java
// ---------------------------
package io.github.game.engine;

import java.nio.file.Paths;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Renderer is responsible for drawing the entire game state onto a JavaFX
 * Canvas.
 * <p>
 * This includes:
 * <ul>
 * <li>The game world tiles (grass, dirt, water)</li>
 * <li>Crops and their growth stages</li>
 * <li>Player character animations (walking, hoeing)</li>
 * <li>Flying item pickups from tiles to the inventory</li>
 * <li>UI elements such as inventory, toolbar, day counter, and overlays</li>
 * <li>Night/day visual effects</li>
 * </ul>
 * <p>
 * The Renderer also handles animation updates and rendering requests via the
 * JavaFX Platform thread.
 */
public final class Renderer {

    // World and canvas
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
    private boolean showControlsOverlay = true;
    private Runnable onOverlayToggled;
    private double menuAnimTime = 0; // Menu animation

    /**
     * Constructs a Renderer for the given world and canvas.
     * <p>
     * Loads tile and crop images, player animations, and sets up the pickup
     * callback.
     *
     * @param world  the game world to render
     * @param canvas the JavaFX Canvas to draw on
     * @param player the Player object, used for animations and pickup callbacks
     */
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

    /**
     * Requests a render on the JavaFX application thread.
     * This ensures thread-safety for UI updates.
     */
    public void requestRender() {
        Platform.runLater(this::render);
    }

    /**
     * Renders the entire game scene: world, player, items, UI, day/night overlay.
     * Called internally via {@link #requestRender()}.
     */
    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawWorld();
        drawFlyingItems();
        drawPlayer();
        drawInventory(world.getPlayer());
        drawToolBar(world.getPlayer());
        drawNightOverlay();
        drawDayCounter();
        if (showControlsOverlay)
            drawControlsOverlay();

        menuAnimTime += 0.05;

    }

    // ---------------- WORLD ----------------

    /**
     * Draws the entire world tiles and any crops present.
     * Grass, dirt, and water tiles are drawn according to tile type.
     * Crops are drawn on dirt tiles if present.
     */
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

    /**
     * Draws the player sprite on the canvas according to current position,
     * direction, action (walking or hoeing), and animation frame.
     */
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

    /**
     * Draws all flying items currently animating toward the inventory.
     * Updates their position and removes items whose animation is complete.
     */
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

    /**
     * Draws the toolbar at the bottom of the screen showing all tools
     * and highlights the currently selected tool.
     *
     * @param p the player whose tools are displayed
     */
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

    /**
     * Draws the player's inventory with crops and their quantities.
     * Semi-transparent images indicate zero quantity.
     *
     * @param p the player whose inventory is displayed
     */
    private void drawInventory(Player p) {
        int slotSize = 35;
        int spacing = 20;
        int startX = 3;
        int startY = 200;

        String[] crops = { "wheat", "tomato" };

        gc.setFont(FONT_SMALL); // smaller font just for counts

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
            gc.fillText(
                    "x" + qty,
                    startX + 6,
                    y + slotSize + 14);
        }
    }

    /**
     * Draws a night-time overlay to simulate darkness based on the
     * world’s day/night cycle.
     */
    private void drawNightOverlay() {
        double alpha = world.getDayCycle().getNightAlpha();
        if (alpha > 0) {
            gc.setFill(Color.rgb(0, 0, 50, alpha));
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    /**
     * Draws a single tile image at the specified tile coordinates.
     *
     * @param img the image to draw
     * @param tx  x-coordinate in tiles
     * @param ty  y-coordinate in tiles
     */
    private void drawImage(Image img, int tx, int ty) {
        gc.drawImage(img, tx * tileSize, ty * tileSize, tileSize, tileSize);
    }

    /**
     * Draws the day counter at the top of the screen with the current day
     * and percentage of the day that has passed.
     */
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

        gc.setFont(FONT_SMALL);
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        // Slightly lower than before to avoid clipping
        drawCenteredText(text, 26);
    }

    /**
     * Toggles the controls overlay (pause/menu) visibility.
     * If an overlay toggle callback is set, it will be invoked (used to pause the
     * game loop).
     */
    public void toggleControlsOverlay() {
        showControlsOverlay = !showControlsOverlay;
        if (onOverlayToggled != null) {
            onOverlayToggled.run(); // tells GameLoop to pause/unpause
        }
    }

    /**
     * Draws the controls overlay (pause menu) showing title, description,
     * controls list, and start prompt with animations.
     */
    private void drawControlsOverlay() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        // Background
        gc.setFill(Color.rgb(0, 0, 0, 0.88));
        gc.fillRect(0, 0, width, height);

        // Animation timers
        double titleFloat = Math.sin(menuAnimTime) * 12;
        double pulse = 0.6 + 0.4 * Math.sin(menuAnimTime * 2);

        // ---------- TITLE ----------
        gc.setFont(Font.font(pixelFont.getName()));
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.setFill(Color.WHITE);

        gc.setFont(FONT_LARGE);
        drawCenteredText("FARM HARVEST", height * 0.20 + titleFloat);

        // ---------- DESCRIPTION ----------
        gc.setFont(FONT_SMALL);
        gc.setFont(Font.font(pixelFont.getName()));
        String[] desc = {
                "A cozy farming experience.",
                "Plant crops, harvest produce",
                "and watch your farm grow."
        };

        double descY = height * 0.30;
        for (int i = 0; i < desc.length; i++) {
            drawCenteredText(desc[i], descY + i * 24);
        }

        // ---------- CONTROLS ----------
        // Controls Title
        gc.setFont(Font.font(pixelFont.getName()));
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.setFill(Color.WHITE);

        gc.setFont(FONT_MEDIUM);
        drawCenteredText("Controls:", height * 0.51);

        // Controls List
        gc.setFont(FONT_SMALL);
        gc.setFont(Font.font(pixelFont.getName()));
        String[] controls = {
                "WASD / Arrow Keys — Move player",
                "E or SPACE — Use tool / interact / harvest ",
                "1 – 4 — Select tool or seed",
                "ENTER — Toggle controls / pause menu"
        };

        double controlsY = height * 0.58;
        for (int i = 0; i < controls.length; i++) {
            drawCenteredText(controls[i], controlsY + i * 32);
        }

        // ---------- START PROMPT ----------
        gc.setGlobalAlpha(pulse);
        gc.setFont(Font.font(pixelFont.getName(), 24));
        drawCenteredText("Press ENTER to Start", height * 0.90);
        gc.setGlobalAlpha(1.0);
    }

    /**
     * Returns whether the controls overlay (pause menu) is currently shown.
     *
     * @return true if the overlay is visible, false otherwise
     */
    public boolean isShowingOverlay() {
        return showControlsOverlay;
    }

    /**
     * Sets a callback to be executed whenever the overlay visibility changes.
     * Typically used to pause or resume the game loop.
     *
     * @param callback a Runnable to execute on overlay toggle
     */
    public void setOverlayToggleCallback(Runnable callback) {
        this.onOverlayToggled = callback;
    }

    /**
     * Draws a text string horizontally centered at the specified y-coordinate.
     *
     * @param text the text to draw
     * @param y    the vertical position on the canvas
     */
    private void drawCenteredText(String text, double y) {
        Text temp = new Text(text);
        temp.setFont(gc.getFont());
        double textWidth = temp.getLayoutBounds().getWidth();

        gc.strokeText(text, canvas.getWidth() / 2 - textWidth / 2, y);
        gc.fillText(text, canvas.getWidth() / 2 - textWidth / 2, y);
    }

    // ---------------- PICKUP SPAWN ----------------

    /**
     * Spawns a flying item animation from a tile to the player's inventory.
     *
     * @param cropType the type of crop ("wheat" or "tomato")
     * @param tileX    the x-coordinate of the tile
     * @param tileY    the y-coordinate of the tile
     * @param img      the image of the item to animate
     */
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

    /**
     * Inner class representing an item flying from a tile to the inventory.
     * Used for pickup animations.
     */
    private static class FlyingItem {
        double x, y;
        final double tx, ty;
        double progress = 0;
        final Image image;

        /**
         * Creates a FlyingItem instance.
         *
         * @param x   starting x-coordinate
         * @param y   starting y-coordinate
         * @param tx  target x-coordinate
         * @param ty  target y-coordinate
         * @param img image to render
         */
        FlyingItem(double x, double y, double tx, double ty, Image img) {
            this.x = x;
            this.y = y;
            this.tx = tx;
            this.ty = ty;
            this.image = img;
        }

        /**
         * Updates the item's position based on a linear interpolation.
         *
         * @return true if the animation has reached its target, false otherwise
         */
        boolean update() {
            progress += 0.05;
            x += (tx - x) * 0.2;
            y += (ty - y) * 0.2;
            return progress >= 1;
        }
    }

    // ---------------- MUSIC ----------------

    /**
     * AudioManager handles background music playback for the game.
     * Plays a looping track and allows stopping the music.
     */
    public class AudioManager {
        private MediaPlayer bgMusic;

        /**
         * Loads and starts playing the background music in a loop.
         */
        public AudioManager() {
            try {
                String path = Paths.get("src/main/resources/audio/background.mp3").toUri().toString();
                Media media = new Media(path);
                bgMusic = new MediaPlayer(media);
                bgMusic.setCycleCount(MediaPlayer.INDEFINITE); // loop
                bgMusic.setVolume(0.3); // optional: adjust volume
                bgMusic.play();
            } catch (Exception e) {
                System.err.println("Failed to load background music: " + e.getMessage());
            }
        }

        /**
         * Stops the background music if currently playing.
         */
        public void stop() {
            if (bgMusic != null)
                bgMusic.stop();
        }
    }

    // ---------------- FONT ----------------

    /** Custom game font */
    Font pixelFont = Font.loadFont(
            getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"),
            20);

    private final Font FONT_SMALL = Font.loadFont(
            getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"),
            12);

    private final Font FONT_MEDIUM = Font.loadFont(
            getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"),
            18);

    private final Font FONT_LARGE = Font.loadFont(
            getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"),
            32);

}
