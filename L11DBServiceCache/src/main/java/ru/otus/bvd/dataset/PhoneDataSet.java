package ru.otus.bvd.dataset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Z_PHONE")
public class PhoneDataSet extends DataSet {

    @ManyToOne(optional=true)    
    @JoinColumn(name="USER_ID", nullable=true)
    private UserDataSet user;
    
    @Column(name = "NUMBER")
    private String number;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number, UserDataSet user) {
        this.number = number;
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }

}
