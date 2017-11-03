package ru.otus.bvd.ms.dataset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Z_ADDRESS")
public class AddressDataSet extends DataSet {
    @Column(name="STREET")
    private String street;
    
    @Column (name="ZIP")
    private int zip;
    
    public AddressDataSet(String street, int zip) {
        this.street = street;
        this.zip = zip;
    }
    public AddressDataSet() {}

    public String getStreet() {
        return street;
    }
    public int getZip() {
        return zip;
    }
    @Override
    public String toString() {
        return "AddressDataSet{" +
               "zip=" + getZip() +
               ",street=" + getStreet() +
               "}";
    };
    
    
    
    
}
