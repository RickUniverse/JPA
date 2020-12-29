package com.jpa.manager;

import com.jpa.entities.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * @author lijichen
 * @date 2020/11/25 - 16:16
 */
public class TestJPA {
    public static void main(String[] args) {
        //1,创建EntitymanagerFactory
        String persistenceName = "myPersistenceUnit";
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(persistenceName);

        //2,创建EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //3,开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        //4,进行持久化操作
        Customer customer = new Customer(0,"sdfadfd",123,new Date());
        entityManager.persist(customer);

        //5,提交事务
        transaction.commit();

        //6,关闭EntityManager
        entityManager.close();

        //7,关闭EntityManagerFactory
        entityManagerFactory.close();
    }
}
