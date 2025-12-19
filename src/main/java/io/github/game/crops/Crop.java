// ---------------------------
// File: src/main/java/io/github/game/crops/Crop.java
// ---------------------------
package io.github.game.crops;

/**
 * Abstract class representing a general crop in the game.
 * Implements the {@link Growable} interface.
 */
public abstract class Crop implements Growable {

    /** Current growth stage of the crop. */
    protected int growthStage = 0;

    /** Maximum growth stage of the crop. */
    protected int maxStage = 5;

    /** Progress toward the next growth stage (0.0 - 1.0). */
    protected double growthProgress = 0.0;

    /**
     * Advances the crop's growth by one day.
     * Updates the growth stage if progress reaches the threshold.
     */
    @Override
    public void onNewDay() {
        growthProgress += getDailyGrowth();

        while (growthProgress >= 1.0 && growthStage < maxStage) {
            growthStage++;
            growthProgress -= 1.0;
        }
    }

    /**
     * Gets the daily growth rate of this crop.
     * Subclasses define specific growth rates.
     *
     * @return growth increment per day
     */
    protected abstract double getDailyGrowth();

    /**
     * Checks if the crop has reached its maximum growth stage.
     *
     * @return true if fully grown, false otherwise
     */
    @Override
    public boolean isFullyGrown() {
        return growthStage >= maxStage;
    }

    /**
     * Gets the current growth stage of the crop.
     *
     * @return integer representing growth stage
     */
    public int getGrowthStage() {
        return growthStage;
    }

    /**
     * Returns the yield amount when harvested.
     * Subclasses should define specific harvest values.
     *
     * @return number of items produced when harvested
     */
    public abstract int getHarvestYield();
}
