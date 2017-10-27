package ru.otus.bvd.ms.app;

import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.Addressee;

/**
 * Created by tully.
 */
public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";
    
    private final Address from;
    private final Address to;
    
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final String className;
    
    protected Msg(Class<?> klass, Address from, Address to) {
        this.className = klass.getName();
        this.from = from;
        this.to = to;
    }
    
    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);
    
}
