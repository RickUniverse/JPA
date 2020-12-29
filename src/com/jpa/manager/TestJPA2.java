package com.jpa.manager;

import com.jpa.entities.*;
import net.sf.ehcache.search.expression.Or;
import org.hibernate.jpa.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import javax.sound.midi.Soundbank;
import java.util.Date;
import java.util.List;

/**
 * @author lijichen
 * @date 2020/11/25 - 17:49
 */
public class TestJPA2 {

    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;
    private EntityTransaction entityTransaction;

    @Before
    public void before() {
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
    }

    @After
    public void after() {
        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }


    //TestUpdateAndDELETE  : JPQL操作
    @Test
    public void testUpdateAndDELETE(){
        Query query = entityManager.createQuery("update Customer c set c.lastName= ?1 where c.id = ?2")
                .setParameter(1,"qq").setParameter(2,2012);
        query.executeUpdate();
    }



    //TestSubQuery子查询
    @Test
    public void testSubQuery(){
//        Query query = entityManager.createQuery("select c FROM Customer c " +
//                "where c.lastName = (select o.orderName from  Order o where o.id = ?1)").setParameter(1,2);
        Query query = entityManager.createQuery("select o FROM Order o " +
                "where o.customer = (select c from  Customer c where c.lastName = ?1)").setParameter(1,"e");
        List<Order> resultList = query.getResultList();
        System.out.println(resultList.get(0));
    }



    //Testleft outer join fetch
    @Test
    public void testLeftOuterJoinFetch(){
        Query query = entityManager.createQuery("FROM Customer c " +
                "left outer join fetch c.orders where c.id != 1");
        List<Customer> resultList = query.getResultList();
        System.out.println(resultList.get(0));
    }



    //TestOrderByAndGroupBy
    @Test
    public void testOrderByAndGroupBy(){
        Query query = entityManager.createQuery("SELECT new Customer(c.lastName, c.age) FROM Customer c " +
                "GROUP BY c.id " +
                "HAVING COUNT(c.id) >= 1 ORDER BY c.id");
        List resultList = query.getResultList();
        System.out.println(resultList.get(0));
    }

    //TestSecondLevenQueryCache
    @Test
    public void testSecondLevenQueryCache(){
        Query query = entityManager.createQuery("SELECT new Customer(c.lastName, c.age) FROM Customer c")
                .setHint(QueryHints.HINT_CACHEABLE, true);//使用二级缓存
        List<Customer> resultList = query.getResultList();
        System.out.println(resultList.size());
        System.out.println(resultList.get(0));

        query = entityManager.createQuery("SELECT new Customer(c.lastName, c.age) FROM Customer c")
                .setHint(QueryHints.HINT_CACHEABLE, true);//使用二级缓存
        List<Customer> resultList2 = query.getResultList();
        System.out.println(resultList2.size());
        System.out.println(resultList2.get(0));
    }


    //TestNativeQuery,返回Object类型
    @Test
    public void testNativeQuery(){
        Query query = entityManager.createNativeQuery("select * from JPA_Customer");
        List<Customer> resultList = query.getResultList();
        System.out.println(resultList.size());
        System.out.println(resultList.get(0));
    }


    //TestCreateQuery
    @Test
    public void testCreateQuery(){
        Query query = entityManager.createQuery("SELECT new Customer(c.lastName, c.age) FROM Customer c");
        List<Customer> resultList = query.getResultList();
        System.out.println(resultList.size());
        System.out.println(resultList.get(0));
    }

    //TestNamedQuery
    @Test
    public void testNamedQuery(){
        Query query = entityManager.createNamedQuery("testNamedQuery").setParameter(1, "q");
        List resultList = query.getResultList();
        System.out.println(resultList.size());
    }



    //二级缓存
    @Test
    public void testSecondLevelEhcache(){
        Customer customer = entityManager.find(Customer.class,2212);
        Customer customer2 = entityManager.find(Customer.class,2212);
        entityTransaction.commit();
        entityManager.close();

        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer3 = entityManager.find(Customer.class,2212);
        Customer customer4 = entityManager.find(Customer.class,2212);
    }



    //双向多对多
    @Test
    public void testManyToMany(){
        Category category = new Category();
        category.setName("c1n");
        Category category2 = new Category();
        category2.setName("c2n");

        Item item = new Item();
        item.setName("i1n");
        Item item2 = new Item();
        item2.setName("i2n");

        //关联关系
        category.getItems().add(item);
        category.getItems().add(item2);
        category2.getItems().add(item);
        category2.getItems().add(item2);

        item.getCategories().add(category);
        item.getCategories().add(category2);
        item2.getCategories().add(category);
        item2.getCategories().add(category2);

        //保存
        entityManager.persist(item);
        entityManager.persist(item2);
        entityManager.persist(category);
        entityManager.persist(category2);
    }




    //双向一对一
    @Test
    public void testOneToOne() {
        Manager manager = new Manager();
        manager.setName("m1");

        Department department = new Department();
        department.setName("d1");

        //设置关联关系
        manager.setDepartment(department);
        department.setManager(manager);

        //先插入不维护关联关系的一方
        entityManager.persist(manager);
        entityManager.persist(department);

        //获取
        Manager manager1 = entityManager.find(Manager.class, 11);
        System.out.println(manager.getDepartment().getName());
    }



    //測試单向以对多关系,此类操作跟hibernate一样
    @Test
    public void testOneToMany() {
        Customer customer = new Customer();
        customer.setLastName("a");

        Product product = new Product();
        product.setName("a-a");
        Product product2 = new Product();
        product2.setName("a-b");

        Order order = new Order();
        order.setOrderName("a-a");
        Order order2 = new Order();
        order2.setOrderName("a-b");

        //设置关系
        customer.getOrders().add(order);
        customer.getOrders().add(order);

        //设置关系
        customer.getProducts().add(product);
        customer.getProducts().add(product2);

        //保存
        entityManager.persist(product);
        entityManager.persist(product2);

        entityManager.persist(customer);

        //获取
        Customer customer1 = entityManager.find(Customer.class, 2112);
        System.out.println(customer1.getProducts().size());
    }

    //測試单向多对一关系,此类操作跟hibernate一样
    @Test
    public void testManyToOne() {
        Customer customer = new Customer();
        customer.setLastName("a");

        Order order = new Order();
        order.setOrderName("a-a");
        Order order2 = new Order();
        order2.setOrderName("a-b");

        //设置关系
        order.setCustomer(customer);
        order2.setCustomer(customer);

        //保存
        entityManager.persist(customer);
        entityManager.persist(order);
        entityManager.persist(order2);

        //获取
        Order order1 = entityManager.find(Order.class, 2);
        order1.getCustomer().setLastName("ser");//修改
    }

    //同与hibernate中的refresh方法,更新内存中的对象
    @Test
    public void testres() {
        Customer customer = entityManager.find(Customer.class,712);
        customer = entityManager.find(Customer.class,712);

        entityManager.refresh(customer);

        System.out.println(customer);
    }

    //同与hibernate中的flush方法
    @Test
    public void testFlush() {
        Customer customer = entityManager.find(Customer.class,712);
        customer.setLastName("ff");

        entityManager.flush();

        System.out.println(customer);
    }


    //类似与hibernate中的saveOrUpdate方法
    @Test
    public void testMerge() {
        Customer customer = entityManager.find(Customer.class,712);
        System.out.println("-----------------");
        customer.setLastName("ddd");

        //总是会返回一个新的对象
        Customer customer2 = entityManager.merge(customer);
        System.out.println(customer2 == customer);

        System.out.println(customer);
    }


    //类似与hibernate中的delete方法
    //只能删除持久化对象，不能删除游离对象，hibernate都可以删除
    @Test
    public void testRemove() {
//        Customer customer = new Customer();
//        customer.setId(12);

        Customer customer = entityManager.getReference(Customer.class,12);
        System.out.println("-----------------");
        entityManager.remove(customer);
        System.out.println(customer);
    }

    //类似与hibernate中的save方法
    //区别是游离对象不能设有id，否则会报错，hibernate不会报错
    @Test
    public void testPersistence() {
        Customer customer = new Customer();
//        customer.setId(1);
        customer.setBirth(new Date());
        customer.setLastName("sd");
        System.out.println("-----------------");
        entityManager.persist(customer);

        System.out.println(customer);
    }

    //类似与hibernate中的load方法
    @Test
    public void testGetReference() {
        Customer customer = entityManager.getReference(Customer.class,12);
        System.out.println("-----------------");

        System.out.println(customer);
    }

    //类似与hibernate中的get方法
    @Test
    public void testFind() {
        Customer customer = entityManager.find(Customer.class,12);
        System.out.println("-----------------");
        System.out.println(customer);
    }
}
