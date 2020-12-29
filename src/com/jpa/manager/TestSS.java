package com.jpa.manager;

import com.jpa.entities.Person;
import com.jpa.service.PersonService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author lijichen
 * @date 2020/11/26 - 17:29
 */
public class TestSS {
    private static ApplicationContext applicationContext;
    private PersonService personService;

    {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        personService = applicationContext.getBean(PersonService.class);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class,"dataSource");
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testTransaction() {
        Person person = new Person();
        person.setEmail("sdfaf");
        person.setBirth(new Date());
        Person person2 = new Person();
        person.setEmail("2asdf");
        person.setBirth(new Date());

        System.out.println(personService.getClass().getName());
        personService.save(person,person2);
    }
}
