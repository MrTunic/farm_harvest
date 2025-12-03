package io.github.game.world;

import io.github.game.abstracts.AbstractCrop;
import io.github.game.interfaces.Interactable;
import io.github.game.utils.ResourceManager;

import javafx.scene.image.Image;

public class Tile implements Interactable {

    private TileType type;
    private Image image;
    private AbstractCrop crop;

    public Tile(TileType type) {
        this.type = type;
        loadTexture();
    }

    private void loadTexture() {
        this.image = switch (type) {
            case GRASS -> ResourceManager.load("tiles/grass.png");
            case DIRT -> ResourceManager.load("tiles/dirt.png");
            case WATER -> ResourceManager.load("tiles/water.png");
        };
    }

    public Image getImage() { return image; }
    public AbstractCrop getCrop() { return crop; }
    public TileType getType() { return type; }

    @Override
    public void interact(Player player) {
        if (type == TileType.GRASS) {
            type = TileType.DIRT;
            loadTexture();
        }
        else if (type == TileType.DIRT && crop == null) {
            crop = new Crop("Wheat");
        }
    }
}
