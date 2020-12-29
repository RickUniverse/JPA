package com.jpa.entities;

import javax.persistence.*;

/**
 * @author lijichen
 * @date 2020/11/25 - 21:12
 */
@Entity
@Table(name = "JAP_DEPARTMENT")
public class Department {
    private int id;
    private String name;
    private Manager manager;

    public Department() {
    }

    public Department(int id, String name, Manager manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
    }

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

    //双向一对一
    @JoinColumn(name = "manager_id", unique = true)//因为是维护外键的一方所以需要加上 唯一约束
    @OneToOne(fetch = FetchType.LAZY)//懒加载，减少无效sql语句
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manager=" + manager +
                '}';
    }
}
