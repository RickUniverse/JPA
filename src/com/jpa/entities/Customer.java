package com.jpa.entities;

import net.sf.ehcache.search.expression.Or;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lijichen
 * @date 2020/11/25 - 16:09
 */
@NamedQuery(name = "testNamedQuery", query = "FROM Customer c WHERE c.lastName != ?1")
@Cacheable(true)
@Table(name = "JPA_Customer")
@Entity
public class Customer {

    private int id;
    private String lastName;
    private int age;
    private Date birth;
    private Date createTime;
    private Set<Product> products = new HashSet<>();
    private Set<Order> orders = new HashSet<>();

    //双向一对多关系
    //mappedBy = "customer" : 右多的一端的customer 维护关联关系，有效减少update语句
    //如果 1 的这一端加上了mappedBy = "customer" 就不能使用 @JoinColumn
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE, mappedBy = "customer")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    //单向一对多关系
    //FetchType.EAGER ： 不使用懒加载，默认是懒加载
    //cascade = CascadeType.REMOVE ： 级联删除
    @JoinColumn(name = "CUSTOMER_ID")//关联外键
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Temporal(TemporalType.TIMESTAMP)//精确到秒，数据库中类型为datetime
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Customer() {
    }

    public Customer(String lastName, int age) {
        this.lastName = lastName;
        this.age = age;
    }

    public Customer(int id, String lastName, int age, Date birth) {
        this.id = id;
        this.lastName = lastName;
        this.age = age;
        this.birth = birth;
    }

    @Column(name = "ID")//主键
//    @GeneratedValue(strategy = GenerationType.AUTO)///主键生成策略
    @TableGenerator(name = "id_generator",
            table = "jpa_id_generator",//生成表
            pkColumnName = "pk_name",//标识列名
            pkColumnValue = "CUSTOMER_ID",//标识列的分辨值
            valueColumnName = "pk_value",//表增长值
            allocationSize = 100)//步长
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator")//使用表的生成策略
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "LAST_NAME", length = 10, nullable = false)//表中列名为：last_name，长度为：10，不能为空
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Basic//默认便为Basic ， 表中字段名则为：birth
    @Temporal(TemporalType.DATE)//精确到天：数据库中类型是date
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", birth=" + birth +
                '}';
    }

    @Transient//不需要映射到表中的字段
    public String getInfo() {
        return "info";
    }
}
