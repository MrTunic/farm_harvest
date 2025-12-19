// ---------------------------
// File: src/main/java/io/github/game/util/ResourceManager.java
// ---------------------------
package io.github.game.util;

import java.io.InputStream;

import javafx.scene.image.Image;

// ResourceManager class for loading game resources
public class ResourceManager {
    public static Image loadImage(String relativePath) {
        InputStream is = ResourceManager.class.getClassLoader().getResourceAsStream(relativePath);
        if (is == null) {
            System.err.println("Missing resource: " + relativePath);
            return null;
        }
        try {
            return new Image(is);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + relativePath + " -> " + e.getMessage());
            return null;
        }
    }
}
