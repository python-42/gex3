package com.giftexchange.gex3.item;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "item")
public class ItemTable {

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private boolean bought;

    @Column()
    private String boughtBy;

    @Column(nullable = false)
    private Date entryDate;
    

    public ItemTable(){}

    public ItemTable(String owner, String name, String url, String title, String comment){
        this.owner = owner;
        this.name = name;
        this.url = url;
        this.title = title;
        this.comment = comment;
        this.bought = false;
        this.entryDate = new Date();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isBought() {
        return this.bought;
    }

    public boolean getBought() {
        return this.bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public String getBoughtBy() {
        return this.boughtBy;
    }

    public void setBoughtBy(String boughtBy) {
        this.boughtBy = boughtBy;
    }

    public String getEntryDateAsString(){
        return this.entryDate.toString();
    }

    public Date getEntryDate() {
        return this.entryDate;
    }

}
