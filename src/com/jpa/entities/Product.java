package com.jpa.entities;

import javax.persistence.*;

/**
 * @author lijichen
 * @date 2020/11/25 - 19:56
 */
@Entity
@Table(name = "JAP_PRODUCT")
public class Product {
    private int id;
    private String name;

    public Product() {
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @TableGenerator(name = "product_id",
            table = "jpa_id_generator",
            pkColumnName = "pk_name",
            pkColumnValue = "PRODUCT_ID",
            valueColumnName = "pk_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "product_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
