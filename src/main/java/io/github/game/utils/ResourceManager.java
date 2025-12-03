package io.github.game.utils;

import javafx.scene.image.Image;

public class ResourceManager {

    public static Image load(String path) {
        return new Image(
                ResourceManager.class.getResourceAsStream("/" + path)
        );
    }
}
