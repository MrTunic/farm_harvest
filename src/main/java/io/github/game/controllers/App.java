// ---------------------------
// File: src/main/java/io/github/game/controllers/App.java
// ---------------------------
package io.github.game.controllers;

import io.github.game.engine.GameLoop;
import io.github.game.engine.InputHandler;
import io.github.game.engine.Renderer;
import io.github.game.engine.Renderer.AudioManager;
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

/**
 * Main JavaFX application class for the Farming Game.
 * <p>
 * Responsible for initializing the game world, player, renderer,
 * input handling, audio, and starting the main game loop.
 */
public class App extends Application {

    /** Width of the game canvas in pixels. */
    private static final int CANVAS_WIDTH = 640;

    /** Height of the game canvas in pixels. */
    private static final int CANVAS_HEIGHT = 480;

    /** The main game loop controlling updates and rendering. */
    private GameLoop loop;

    /**
     * JavaFX entry point after launch().
     * Sets up the game world, player, tools, renderer, input handling,
     * audio, and starts the game loop.
     *
     * @param primaryStage the primary stage provided by JavaFX
     */
    @Override
    public void start(Stage primaryStage) {

        // Create world (this also initializes the Player)
        World world = new World(16, 12, 800, 400);

        // Initialize player tools
        Player p = world.getPlayer();
        p.getTools().add(new HoeTool(ResourceManager.loadImage("tools/hoe.png")));
        p.getTools().add(new SeedTool(SeedType.WHEAT, ResourceManager.loadImage("crops/wheat_stage_0.png")));
        p.getTools().add(new SeedTool(SeedType.TOMATO, ResourceManager.loadImage("crops/tomato_stage_0.png")));

        // Setup JavaFX canvas and renderer
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        Renderer renderer = new Renderer(world, canvas, p);

        // Input handling
        InputHandler input = new InputHandler(world, renderer);

        // Audio
        AudioManager audioManager = renderer.new AudioManager();

        // Main game loop at 60 FPS
        loop = new GameLoop(world, renderer, 60, audioManager);

        // Scene and stage setup
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

    /**
     * Stops the game loop when the application exits.
     *
     * @throws Exception if an error occurs while stopping
     */
    @Override
    public void stop() throws Exception {
        if (loop != null) loop.stop();
        super.stop();
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
