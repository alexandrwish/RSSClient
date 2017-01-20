package com.news.rss.record;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class ItemRecord {

    @Element(name = "title")
    protected String title;
    @Element(name = "link")
    protected String link;
    @Element(name = "pubDate")
    protected String date;
    @Element(name = "description")
    protected String description;
    @Element(name = "comments")
    protected String comments;

    public ItemRecord() {
    }

    public ItemRecord(String title, String link, String date, String description, String comments) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.description = description;
        this.comments = comments;
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