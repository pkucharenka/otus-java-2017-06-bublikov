package ru.otus.bvd.db.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;

import ru.otus.bvd.ms.dataset.PhoneDataSet;

public class PhoneDataSetDaoHibernate {
    private Session session;
    
    public PhoneDataSetDaoHibernate(Session session) {
        this.session = session;
    }
    
    public void save(PhoneDataSet phone) {
        session.save(phone);
    }
    
    public PhoneDataSet read(long id) {
        return session.load(PhoneDataSet.class, id);
    }
    
    public List<PhoneDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PhoneDataSet> criteria = builder.createQuery(PhoneDataSet.class);
        criteria.from(PhoneDataSet.class);
        return session.createQuery(criteria).list();
    }

}
