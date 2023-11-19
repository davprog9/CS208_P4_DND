package com.example.cs208_assignment4;

import java.util.LinkedList;

/**
 * Custom implementation of a hash table using separate chaining for collision handling.
 * The hash table can dynamically resize based on a load factor threshold to maintain efficient operations.
 * It stores data in a linked list array, where each entry consists of a key and a value.
 *
 * @author Christopher Duran
 */
public class Hashtable {
    private LinkedList<Entry>[] table;
    private int capacity;
    private int size;
    private final double loadFactorThreshold;

    /**
     * Represents a key-value pair in the hash table.
     */
    private static class Entry {
        final int key;
        int value;

        /**
         * Constructs an Entry with a specified key and value.
         *
         * @param key   The key associated with the entry.
         * @param value The value associated with the key.
         */
        Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Constructs a Hashtable with specified initial capacity and load factor threshold.
     *
     * @param initialCapacity     The initial capacity of the hash table.
     *
     */
    public Hashtable(int initialCapacity) {
        this.capacity = initialCapacity;
        this.loadFactorThreshold = 3;
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
        this.size = 0;
    }

    /**
     * Calculates the bucket index for a given key.
     *
     * @param key The key for which the bucket index is to be calculated.
     * @return The bucket index.
     */
    private int getBucketIndex(int key) {
        return key % capacity;
    }

    /**
     * Inserts or updates a key-value pair in the hash table.
     *
     * @param key   The key to be inserted or updated.
     * @param value The value to be associated with the key.
     */
    public void put(int key, int value) {
        int bucketIndex = getBucketIndex(key);
        for (Entry entry : table[bucketIndex]) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }

        table[bucketIndex].add(new Entry(key, value));
        size++;

        if ((1.0 * size) / capacity >= loadFactorThreshold) {
            rehash();
        }
    }

    /**
     * Retrieves the value associated with a specified key.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the specified key, or -1 if the key is not found.
     */
    public int get(int key) {
        int bucketIndex = getBucketIndex(key);
        for (Entry entry : table[bucketIndex]) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return -1; // Key not found
    }

    /**
     * Resizes the hash table and rehashes all existing entries.
     * This method is called when the current size exceeds the load factor threshold.
     */
    private void rehash() {
        LinkedList<Entry>[] oldTable = table;
        capacity *= 2;
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;

        for (LinkedList<Entry> bucket : oldTable) {
            for (Entry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }
}
