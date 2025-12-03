package io.github.game.controllers;

import io.github.game.world.*;
import io.github.game.utils.ResourceManager;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class GameController {

    @FXML private Canvas gameCanvas;

    private static final int TILE_SIZE = 40;
    private static final int ROWS = 10;
    private static final int COLS = 15;

    private Tile[][] tiles;
    private Player player;

    private boolean up, down, left, right;

    private long lastMoveTime = 0;
    private static final long MOVE_INTERVAL = 200_000_000; // 200ms

    private long lastGrowTime = 0;
    private static final long GROW_INTERVAL = 5_000_000_000L; // 5s

    @FXML
    public void initialize() {
        initWorld();

        gameCanvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) setupInput(newScene);
        });

        startGameLoop();
    }

    private void initWorld() {
        player = new Player(0, 0);

        tiles = new Tile[ROWS][COLS];
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                TileType type = switch ((x + y) % 3) {
                    case 0 -> TileType.WATER;
                    case 1 -> TileType.DIRT;
                    default -> TileType.GRASS;
                };
                tiles[y][x] = new Tile(type);
            }
        }
    }

    private void setupInput(Scene scene) {
        scene.setOnKeyPressed(this::onKeyPress);
        scene.setOnKeyReleased(this::onKeyRelease);
    }

    private void onKeyPress(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code == KeyCode.UP) up = true;
        if (code == KeyCode.DOWN) down = true;
        if (code == KeyCode.LEFT) left = true;
        if (code == KeyCode.RIGHT) right = true;

        if (code == KeyCode.SPACE) interact();
    }

    private void onKeyRelease(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code == KeyCode.UP) up = false;
        if (code == KeyCode.DOWN) down = false;
        if (code == KeyCode.LEFT) left = false;
        if (code == KeyCode.RIGHT) right = false;
    }

    private void startGameLoop() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
                render(gc);
            }
        };
        timer.start();
    }

    private void update(long now) {
        if (now - lastMoveTime >= MOVE_INTERVAL) {
            lastMoveTime = now;

            if (up && player.getY() > 0) player.move(0, -1);
            if (down && player.getY() < ROWS - 1) player.move(0, 1);
            if (left && player.getX() > 0) player.move(-1, 0);
            if (right && player.getX() < COLS - 1) player.move(1, 0);
        }

        if (now - lastGrowTime >= GROW_INTERVAL) {
            lastGrowTime = now;

            for (Tile[] row : tiles)
                for (Tile t : row)
                    if (t.getCrop() != null)
                        t.getCrop().grow();
        }
    }

    private void render(GraphicsContext gc) {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                Tile t = tiles[y][x];
                gc.drawImage(t.getImage(),
                        x * TILE_SIZE, y * TILE_SIZE,
                        TILE_SIZE, TILE_SIZE);

                if (t.getCrop() != null) {
                    gc.drawImage(
                        t.getCrop().getCurrentStageImage(),
                        x * TILE_SIZE, y * TILE_SIZE,
                        TILE_SIZE, TILE_SIZE
                    );
                }
            }
        }

        gc.setFill(Color.RED);
        gc.fillRect(player.getX() * TILE_SIZE, player.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private void interact() {
        int x = player.getX();
        int y = player.getY();

        Tile tile = tiles[y][x];
        tile.interact(player);
    }
}
