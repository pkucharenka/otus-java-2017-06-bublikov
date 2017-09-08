package ru.otus.bvd.cache;

/**
 * Created by tully.
 */
public interface CacheEngine<K, V> {

    void put(CacheElement<K, V> element);

    CacheElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
    
    int getSize();
}
