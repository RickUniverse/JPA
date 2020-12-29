package com.jpa.dao;

import com.jpa.entities.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author lijichen
 * @date 2020/11/26 - 17:36
 */
@Repository
public class PersonDao {

    //1，怎样获取同一个线程中的EntityManager
    //加上persistenceContext注解
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Person person) {
        entityManager.persist(person);
    }

}
