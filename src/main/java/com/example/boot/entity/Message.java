package com.example.boot.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Length(max = 255, message = "Too long (max 255)")
    @Length(min = 3, message = "Message must be min 3 letters long")
    private String text;
    @Length(max = 255, message = "Too long (max 255)")
    @Length(min = 3, message = "Tag must be min 3 letters long")
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Message() {

    }

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public String getAuthorName(){
        return author == null ? "" : author.getUsername();
    }

    public boolean getTagExist(){
        return tag.length() != 0;
    }

    public long getAuthorId(){
        return author == null ? -1 : author.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
