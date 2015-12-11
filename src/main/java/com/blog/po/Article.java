package com.blog.po;

import java.util.Date;

/**
 * Created by geekgao on 15-10-12.
 */
public class Article {
    private int id;
    private int author_Id;
    private String title;
    private String peek;
    private Date time;
    private int read_Num;
    private int comment_Num;
    private String deleted;

    public Article() {
    }

    public Article(int author_Id, String title, String peek, Date time, int read_Num, int comment_Num, String deleted) {
        this.author_Id = author_Id;
        this.title = title;
        this.peek = peek;
        this.time = time;
        this.read_Num = read_Num;
        this.comment_Num = comment_Num;
        this.deleted = deleted;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthor_Id() {
        return author_Id;
    }

    public void setAuthor_Id(int author_Id) {
        this.author_Id = author_Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getRead_Num() {
        return read_Num;
    }

    public void setRead_Num(int read_Num) {
        this.read_Num = read_Num;
    }

    public int getComment_Num() {
        return comment_Num;
    }

    public void setComment_Num(int comment_Num) {
        this.comment_Num = comment_Num;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getPeek() {
        return peek;
    }

    public void setPeek(String peek) {
        this.peek = peek;
    }
}
