package com.firstpenguin.tinyurl.restservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Id;
import javax.persistence.Column;
import java.io.Serializable;

@Entity
@Table(name="URL", indexes = {
        @Index(columnList = "longUrlHash", name = "long_url_hash_hidx")
})
public class Url implements Serializable {

    // For serializing and deserializing
    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @Setter
    private String id;

    @Column(name="longUrl")
    @Getter
    @Setter
    private String url;

    @Column(name="longUrlHash")
    @Getter
    @Setter
    private String longUrlHash;

    public Url() { id = ""; url = ""; longUrlHash = ""; }

    public Url(String id, String url, String longUrlHash) {
        this.id = id;
        this.url = url;
        this.longUrlHash = longUrlHash;
    }
}
