package com.firstpenguin.tinyurl.restservice;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Target;

import javax.persistence.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Entity
@Table(name="URL", indexes = {
        @Index(columnList = "shortUrl", name = "short_url_hidx")
})
public class Url {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private String id;

    @Column(name="longUrl")
    @Getter
    @Setter
    private String url;

    @Column(name="shortUrl")
    @Getter
    @Setter
    private String shortUrl;

    public Url() { id = ""; url = ""; shortUrl = ""; }

    public Url(String id, String url, String shortUrl) {
        this.id = id;
        this.url = url;
        this.shortUrl = shortUrl;
    }
}
