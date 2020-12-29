package com.jpa.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lijichen
 * @date 2020/11/25 - 21:47
 */
@Entity
@Table(name = "JAP_ITEM")
public class Item {
    private int id;
    private String name;
    private Set<Category> categories = new HashSet<>();

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

    //双向多对多
    @ManyToMany
    @JoinTable(name = "ITEM_CATEGORY",
            joinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "cateogry_id",referencedColumnName = "id")})
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
