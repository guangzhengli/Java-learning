package com.ligz.concurrent.lock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWrite {
    private final Map<String, String> m = new TreeMap<>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public String get(String key) {
        char[] strs = key.toCharArray();
        Arrays.toString(strs);
        r.lock();
        try {
            return m.get(key);
        }
        finally {
            r.unlock();
        }
    }

    public List<String> allKeys() {
        r.lock();
        try { return new ArrayList<>(m.keySet()); }
        finally { r.unlock(); }
    }

    public String put(String key, String value) {
        w.lock();
        try { return m.put(key, value); }
        finally { w.unlock(); }
    }

    public void clear() {
        w.lock();
        try { m.clear(); }
        finally { w.unlock(); }
    }
}
