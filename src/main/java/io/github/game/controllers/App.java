// Farming Game - JavaFX implementation skeleton (REVISED)
// This revision applies the fixes you suggested: tiles expose TileType, Renderer crop-draw block completed,
// drawImage helper present, World getters exposed, WaterTile included, resource manager fails loud.

// ---------------------------
// File: src/main/java/io/github/game/controllers/App.java
// ---------------------------
package io.github.game.controllers;

import io.github.game.engine.GameLoop;
import io.github.game.engine.InputHandler;
import io.github.game.engine.Renderer;
import io.github.game.entities.HoeTool;
import io.github.game.entities.Player;
import io.github.game.entities.SeedTool;
import io.github.game.entities.SeedType;
import io.github.game.util.ResourceManager;
import io.github.game.world.World;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    private static final int CANVAS_WIDTH = 640;
    private static final int CANVAS_HEIGHT = 480;

    private GameLoop loop;

    @Override
    public void start(Stage primaryStage) {
        
        // Create world (this also creates the Player)
        World world = new World(16, 12);

        // Initialize player tools HERE
        Player p = world.getPlayer();

        p.getTools().add(new HoeTool(
                ResourceManager.loadImage("tools/hoe.png")));

        p.getTools().add(new SeedTool(
                SeedType.WHEAT,
                ResourceManager.loadImage("crops/wheat_stage_0.png")));

        p.getTools().add(new SeedTool(
                SeedType.TOMATO,
                ResourceManager.loadImage("crops/tomato_stage_0.png")));

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        Renderer renderer = new Renderer(world, canvas);
        InputHandler input = new InputHandler(world, renderer);

        loop = new GameLoop(world, renderer, 60);

        BorderPane root = new BorderPane(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> input.onKeyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> input.onKeyReleased(e.getCode()));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Farming Game - JavaFX");
        primaryStage.setResizable(false);
        primaryStage.show();

        canvas.requestFocus();
        loop.start();
    }

    @Override
    public void stop() throws Exception {
        if (loop != null) loop.stop();
        super.stop();
    }

    public static void main(String[] args) { launch(args); }
}

