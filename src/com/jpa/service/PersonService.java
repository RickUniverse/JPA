package com.jpa.service;

import com.jpa.dao.PersonDao;
import com.jpa.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lijichen
 * @date 2020/11/26 - 17:45
 */
@Service
public class PersonService {
    @Autowired
    private PersonDao personDao;

    //事务方法
    @Transactional
    public void save(Person person,Person person2) {
        personDao.save(person);

//        int i = 10 / 0;

        personDao.save(person2);
    }
}
