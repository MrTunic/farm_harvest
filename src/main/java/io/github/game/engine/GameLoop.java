// ---------------------------
// File: src/main/java/io/github/game/engine/GameLoop.java
// ---------------------------
package io.github.game.engine;

import io.github.game.engine.Renderer.AudioManager;
import io.github.game.world.World;

/**
 * Manages the main game loop, updating the world, rendering, and handling pause
 * state.
 */
public class GameLoop {
    private final World world;
    private final Renderer renderer;
    private final int tps; // ticks per second
    private volatile boolean running = false;
    private volatile boolean paused = true;
    private Thread thread;
    private final AudioManager audioManager;

    /**
     * Constructor.
     *
     * @param world          the game world
     * @param renderer       renderer for drawing the game
     * @param ticksPerSecond update frequency
     * @param audioManager   handles background music
     */
    public GameLoop(World world, Renderer renderer, int ticksPerSecond, AudioManager audioManager) {
        this.world = world;
        this.renderer = renderer;
        this.tps = Math.max(1, ticksPerSecond);
        this.audioManager = audioManager;

        // Automatically pause when overlay is shown
        this.renderer.setOverlayToggleCallback(() -> setPaused(renderer.isShowingOverlay()));
    }

    /** Sets whether the game loop is paused. */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /** Checks if the game loop is paused. */
    public boolean isPaused() {
        return paused;
    }

    /** Starts the game loop on a separate daemon thread. */
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
                        world.update(); // only update when not paused
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

    /** Stops the game loop and waits for the thread to finish. */
    public void stop() {
        running = false;
        try {
            if (thread != null)
                thread.join(50);
        } catch (InterruptedException ignored) {
        }
    }
}
