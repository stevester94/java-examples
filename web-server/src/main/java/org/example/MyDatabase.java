package org.example;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyDatabase {
    final private HashMap<String, byte[]> hash = new HashMap<>();
    final private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    final private Lock writeLock = lock.writeLock();
    final private Lock readLock = lock.readLock();

    public byte[] get( String item ) {
        readLock.lock();
        byte[] i = hash.get(item);
        readLock.unlock();
        return i;
    }

    public void put( String item, byte[] val ) {
        writeLock.lock();
        hash.put( item, val );
        writeLock.unlock();
    }

    public boolean has( String item ) {
        readLock.lock();
        boolean has = hash.containsKey( item );
        readLock.unlock();
        return has;
    }
}
