package ru.otus.bvd.ms.core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tully
 */
public final class Address {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final String id;
    private final AddressGroup addressGroup;
    
//    public Address(AddressGroup addressGroup) {
//        this( addressGroup, String.valueOf(ID_GENERATOR.getAndIncrement()) );
//    }

    public Address(AddressGroup addressGroup, String id) {
        this.id = id;
        this.addressGroup = addressGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return id != null ? id.equals(address.id) : address.id == null;
    }

    public AddressGroup getAddressGroup() {
    	return addressGroup;
    }
    
    public String getId() {
    	return id;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
