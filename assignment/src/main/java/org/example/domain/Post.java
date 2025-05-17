package org.example.domain;


public class Post {
    private Integer id;
    private String Title;

    public Post(Integer id, String title) {
        this.id = id;
        Title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }
}
