package com.news.rss.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity(nameInDb = "item", indexes = {@Index(value = "title, date", unique = true)})
public class ItemEntity {

    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    private String title;
    private String link;
    @Index(unique = true)
    private String date;
    private String description;
    private String comments;

    @Generated(hash = 227470636)
    public ItemEntity(Long id, String title, String link, String date,
                      String description, String comments) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.date = date;
        this.description = description;
        this.comments = comments;
    }

    @Generated(hash = 365170573)
    public ItemEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}