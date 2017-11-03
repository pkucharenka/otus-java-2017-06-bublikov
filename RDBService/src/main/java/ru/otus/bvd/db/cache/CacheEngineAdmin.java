package ru.otus.bvd.db.cache;

public interface CacheEngineAdmin {
    public boolean isEternal();

    public long idleTimeMs();

    public long lifeTimeMs();

    public int maxElements();
    
}
