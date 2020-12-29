package com.jpa.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lijichen
 * @date 2020/11/26 - 17:36
 */
@Entity
@Table(name = "jpa_person")
public class Person {
    private int id;
    private String pname;
    private String email;
    private Date birth;

    public Person() {
    }

    public Person(int id, String pname, String email, Date birth) {
        this.id = id;
        this.pname = pname;
        this.email = email;
        this.birth = birth;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "p_name")
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", pname='" + pname + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}';
    }
}
