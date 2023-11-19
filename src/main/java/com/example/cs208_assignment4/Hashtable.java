package com.example.cs208_assignment4;

import java.util.LinkedList;

/**
 * This class uses a simple array of linked list for collision handling. The program defines a custom hash table data structure.
 * Each entry in the hash table consists of a key and a value, encapsulated in the Entry class.
 * @author Christopher Duran
 */
public class Hashtable {
    private LinkedList<Entry>[] table;
    private int capacity;
    private int size;
    private final double loadFactorThreshold;

    private static class Entry {
        final int key;
        int value;

        Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public Hashtable(int initialCapacity) {
        this.capacity = initialCapacity;
        this.loadFactorThreshold = 3;
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
        this.size = 0;
    }

    private int getBucketIndex(int key) {
        return key % capacity;
    }

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

    public int get(int key) {
        int bucketIndex = getBucketIndex(key);
        for (Entry entry : table[bucketIndex]) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return -1; // Key not found
    }

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
