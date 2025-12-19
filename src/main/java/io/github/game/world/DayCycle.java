// ---------------------------
// File: src/main/java/io/github/game/world/DayCycle.java
// ---------------------------
package io.github.game.world;

/**
 * Manages the day/night cycle timing and transitions.
 */
public class DayCycle {
    private final int dayLength, nightLength;
    private int tick = 0;
    private int dayCount = 1;

    /**
     * Constructs a day/night cycle controller.
     *
     * @param dayLength   number of ticks in a day
     * @param nightLength number of ticks in a night
     */
    public DayCycle(int dayLength, int nightLength) {
        this.dayLength = dayLength;
        this.nightLength = nightLength;
    }

    /**
     * Advances the cycle by one tick.
     */
    public void tick() {
        tick++;
        int total = dayLength + nightLength;
        if (tick >= total) {
            tick = 0;
            dayCount++;
        }
    }

    // Getters
    public int getDayCount() {
        return dayCount;
    }

    public int getCurrentTick() {
        return tick;
    }

    public int getDayLength() {
        return dayLength;
    }

    public int getNightLength() {
        return nightLength;
    }

    /**
     * Computes night overlay opacity for rendering.
     *
     * @return alpha value between 0 and 1
     */
    public double getNightAlpha() {
        if (tick < dayLength) {
            int fadeStart = dayLength - 50;
            if (tick >= fadeStart)
                return (tick - fadeStart) / 50.0;
            return 0;
        } else {
            int nightTick = tick - dayLength;
            if (nightTick < nightLength)
                return 1 - nightTick / (double) nightLength;
            return 0;
        }
    }
}
