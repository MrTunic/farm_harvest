// ---------------------------
// File: src/main/java/io/github/game/util/Inventory.java
// ---------------------------
package io.github.game.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player's inventory.
 * Stores item quantities and provides thread-safe access methods.
 */
public class Inventory {

    // -------------------------
    // Stored items
    // -------------------------
    private final Map<String, Integer> items = new HashMap<>();

    /**
     * Adds a quantity of an item to the inventory.
     *
     * @param item  item identifier
     * @param count amount to add
     */
    public synchronized void add(String item, int count) {
        items.put(item, items.getOrDefault(item, 0) + count);
    }

    /**
     * Removes a quantity of an item from the inventory.
     *
     * @param item  item identifier
     * @param count amount to remove
     * @return true if removal succeeded, false if insufficient quantity
     */
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

    /**
     * Returns the quantity of a given item.
     *
     * @param item item identifier
     * @return quantity owned
     */
    public synchronized int get(String item) {
        return items.getOrDefault(item, 0);
    }

    /**
     * Returns an immutable snapshot of the inventory contents.
     *
     * @return unmodifiable map of items and quantities
     */
    public synchronized Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(new HashMap<>(items));
    }
}