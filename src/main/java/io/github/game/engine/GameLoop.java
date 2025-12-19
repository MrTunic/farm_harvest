// ---------------------------
// File: src/main/java/io/github/game/engine/GameLoop.java
// ---------------------------
package io.github.game.engine;

import io.github.game.engine.Renderer.AudioManager;
import io.github.game.world.World;

// GameLoop class managing the main game loop
public class GameLoop {
    private final World world;
    private final Renderer renderer;
    private final int tps;
    private volatile boolean running = false;
    private volatile boolean paused = true;
    private Thread thread;
    private final AudioManager audioManager;

    // Constructor
    public GameLoop(World world, Renderer renderer, int ticksPerSecond, AudioManager audioManager) {
        this.world = world;
        this.renderer = renderer;
        this.tps = Math.max(1, ticksPerSecond);
        this.audioManager = audioManager;

        // Pause the game whenever the overlay is shown
        this.renderer.setOverlayToggleCallback(() -> setPaused(renderer.isShowingOverlay()));
    }

    // Methods to control the game loop
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(() -> {
            final long nsPerTick = 1_000_000_000L / tps;
            long last = System.nanoTime();
            while (running) {
                long now = System.nanoTime();
                if (now - last >= nsPerTick) {
                    if (!paused) {
                        world.update(); // crops etc only update when not paused.
                    }
                    renderer.requestRender();
                    last += nsPerTick;
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }, "GameLoop");
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            if (thread != null)
                thread.join(50);
        } catch (InterruptedException ignored) {
        }
    }
}