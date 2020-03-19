package com.firstpenguin.tinyurl.restservice;

import javax.persistence.Table;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Entity
@Table(name="GREETING")
public class Greeting implements Serializable {

    //private static final long serialVersionUID = -2343243243242432341L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="content")
    private String content;

    // no-arg constructor needed for hibernate to create the bean
    public Greeting() {}

    public Greeting(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return this.content;
    }
}
