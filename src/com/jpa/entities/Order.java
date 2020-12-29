package com.jpa.entities;

import javax.persistence.*;

/**
 * @author lijichen
 * @date 2020/11/25 - 19:26
 */
@Entity
@Table(name = "JAP_ORDER")
public class Order {
    private int id;
    private String orderName;
    private Customer customer;

    public Order() {
    }

    public Order(int id, String orderName, Customer customer) {
        this.id = id;
        this.orderName = orderName;
        this.customer = customer;
    }

    @Id
    @TableGenerator(name = "order_id",
            table = "jpa_id_generator",
            pkColumnName = "pk_name",
            pkColumnValue = "ORDER_ID",
            valueColumnName = "pk_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    //单线多对一
    @JoinColumn(name = "CUSTOMER_ID")//映射外键
    @ManyToOne(fetch = FetchType.LAZY)//是指懒加载
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", customer=" + customer +
                '}';
    }
}
