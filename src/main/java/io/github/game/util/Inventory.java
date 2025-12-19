// ---------------------------
// File: src/main/java/io/github/game/util/Inventory.java
// ---------------------------
package io.github.game.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Inventory class to manage items and their quantities
public class Inventory {
    private final Map<String, Integer> items = new HashMap<>();

    public synchronized void add(String item, int count) {
        items.put(item, items.getOrDefault(item, 0) + count);
    }

    public synchronized boolean remove(String item, int count) {
        int curr = items.getOrDefault(item, 0);
        if (curr < count)
            return false;
        if (curr == count)
            items.remove(item);
        else
            items.put(item, curr - count);
        return true;
    }

    public synchronized int get(String item) {
        return items.getOrDefault(item, 0);
    }

    public synchronized Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(new HashMap<>(items));
    }
}