package ru.otus.bvd.cache;

public interface CacheEngineAdmin {
    public boolean isEternal();

    public long idleTimeMs();

    public long lifeTimeMs();

    public int maxElements();
    
}
