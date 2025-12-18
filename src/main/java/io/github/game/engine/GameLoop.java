// ---------------------------
// File: src/main/java/io/github/game/engine/GameLoop.java
// ---------------------------
package io.github.game.engine;

import io.github.game.world.World;

public class GameLoop {
    private final World world;
    private final Renderer renderer;
    private final int tps;
    private volatile boolean running = false;
    private Thread thread;

    public GameLoop(World world, Renderer renderer, int ticksPerSecond) {
        this.world = world;
        this.renderer = renderer;
        this.tps = Math.max(1, ticksPerSecond);
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
                    world.update(); // crops etc.
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