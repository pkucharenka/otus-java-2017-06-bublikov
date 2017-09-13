package ru.otus.bvd.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

/**
 * Created by tully.
 */
public class CacheEngineImpl<K, V> implements CacheEngine<K, V>, CacheEngineAdmin {
    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<CacheElement<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    public void put(CacheElement<K, V> elementStrongRef) {
        K key = elementStrongRef.getKey();
        
        SoftReference<CacheElement<K,V>> element = new SoftReference<>(elementStrongRef);
        
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        elements.put(key, element);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    public CacheElement<K, V> get(K key) {
        SoftReference<CacheElement<K, V>> elementSoftRef = elements.get(key);
        if (elementSoftRef==null) {
            miss++;
            return null;
        }
        CacheElement<K, V> element = elementSoftRef.get();
        if (element==null) {
            miss++;
            return null;
        }
        
        hit++;
        element.setAccessed();
        return element;
    }
    
    public int getSize() {
        return elements.size();
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                SoftReference<CacheElement<K, V>> elementSoftRef = elements.get(key);
                if (elementSoftRef==null) {
                    this.cancel();
                    return;
                }
                CacheElement<K, V> element = elementSoftRef.get();
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    @Override
    public boolean isEternal() {
        return isEternal;
    }

    @Override
    public long idleTimeMs() {
        return idleTimeMs;
    }

    @Override
    public long lifeTimeMs() {
        return lifeTimeMs;
    }

    @Override
    public int maxElements() {
        return maxElements;
    }
}
