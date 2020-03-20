package com.firstpenguin.tinyurl.restservice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Id;
import javax.persistence.Column;


@Entity
@Table(name="URL", indexes = {
        @Index(columnList = "longUrlHash", name = "long_url_hash_hidx")
})
public class Url {
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
