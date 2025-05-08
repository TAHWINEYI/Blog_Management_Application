package com.lisbrown.lisbon_blog.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Categories{

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long category_id;
    private String category;
    @OneToOne(mappedBy = "category")
    private Posts post;

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "category_id=" + category_id +
                ", category='" + category + '\'' +
                ", post=" + post +
                '}';
    }
}


