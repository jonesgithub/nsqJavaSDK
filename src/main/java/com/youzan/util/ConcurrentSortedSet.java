/**
 * 
 */
package com.youzan.util;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Blocking when try-lock. It is for the small size collection
 * 
 * @author zhaoxi (linzuxiong)
 * @email linzuxiong1988@gmail.com
 *
 */
@ThreadSafe
public class ConcurrentSortedSet<T> {
    private static final long serialVersionUID = -4747846630389873940L;
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentSortedSet.class);

    private SortedSet<T> set = null;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReadLock r = lock.readLock();
    private final WriteLock w = lock.writeLock();

    public ConcurrentSortedSet() {
        set = new TreeSet<>();
    }

    public T[] newArray(T[] a) {
        r.lock();
        try {
            return set.toArray(a);
        } finally {
            r.unlock();
        }
    }

    public void clear() {
        w.lock();
        try {
            set.clear();
        } finally {
            w.unlock();
        }
    }

    public int size() {
        r.lock();
        try {
            return set.size();
        } finally {
            r.unlock();
        }
    }

    public boolean addAll(Collection<? extends T> c) {
        if (c == null || c.isEmpty()) {
            return true;
        }
        w.lock();
        try {
            return set.addAll(c);
        } finally {
            w.unlock();
        }
    }

    /**
     * Replace inner-data into the specified target.
     * 
     * @param target
     */
    public void swap(SortedSet<T> target) {
        if (target == null) {
            throw new IllegalArgumentException("Your input is null pointer!");
        }
        w.lock();
        final SortedSet<T> tmp = set;
        try {
            set = target;
        } finally {
            w.unlock();
        }
        tmp.clear();
    }

    public void add(T e) {
        if (e == null) {
            return;
        }
        w.lock();
        try {
            set.add(e);
        } finally {
            w.unlock();
        }
    }

    /**
     * @param address
     */
    public void remove(T e) {
        w.lock();
        try {
            set.remove(e);
        } finally {
            w.unlock();
        }
    }

    public boolean isEmpty() {
        r.lock();
        try {
            return set.isEmpty();
        } finally {
            r.unlock();
        }
    }

    /**
     * Never return null
     * 
     * @return
     */
    public SortedSet<T> newSortedSet() {
        final SortedSet<T> s = new TreeSet<>(set);
        r.lock();
        try {
            s.addAll(set);
        } finally {
            r.unlock();
        }
        return s;
    }

    @Override
    public String toString() {
        r.lock();
        try {
            return set.toString();
        } finally {
            r.unlock();
        }
    }
}