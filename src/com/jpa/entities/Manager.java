package com.jpa.entities;

import javax.persistence.*;

/**
 * @author lijichen
 * @date 2020/11/25 - 21:12
 */
@Entity
@Table(name = "JAP_MANAGER")
public class Manager {
    private int id;
    private String name;
    private Department department;

    public Manager() {
    }

    public Manager(int id, String name, Department department) {
        this.id = id;
        this.name = name;
        this.department = department;
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

    @OneToOne(mappedBy = "manager")//不维护关联关系，右有外键的一方维护
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department=" + department +
                '}';
    }
}
