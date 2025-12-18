package io.github.game.world;

public class DayCycle {
    private final int dayLength, nightLength;
    private int tick = 0;
    private int dayCount = 1;

    public DayCycle(int dayLength, int nightLength) {
        this.dayLength = dayLength;
        this.nightLength = nightLength;
    }

    public void tick() {
        tick++;
        int total = dayLength + nightLength;
        if (tick >= total) {
            tick = 0;
            dayCount++;
        }
    }

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

    public double getNightAlpha() {
        if (tick < dayLength) {
            int fadeStart = dayLength - 50;
            if (tick >= fadeStart) return (tick - fadeStart) / 50.0;
            return 0;
        } else {
            int nightTick = tick - dayLength;
            if (nightTick < nightLength) return 1 - nightTick / (double) nightLength;
            return 0;
        }
    }
}
