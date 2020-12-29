package com.jpa.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lijichen
 * @date 2020/11/25 - 21:47
 */
@Entity
@Table(name = "JAP_CATEGORY")
public class Category {
    private int id;
    private String name;
    private Set<Item> items = new HashSet<>();

    @Id
    @GeneratedValue
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

    @ManyToMany(mappedBy = "categories")//不维护关联关系
    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
