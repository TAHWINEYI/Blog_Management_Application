package com.lisbrown.lisbon_blog.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Blob;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="post_id")
    private Long postId;
    @Column(name="tittle", nullable = false)
    private String  tittle;
    @Column(name="content", nullable = false)
    private String content;
    @OneToOne(mappedBy = "post")
    private Categories categories;
    @CreatedBy
    @JoinColumn(name="user_id", nullable = false)
    @ManyToOne
    @JsonBackReference
    private Users user;
    @CreatedDate
    @Column(name = "created_date")
    private Instant createdOn;
    private Blob image;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch =FetchType.LAZY)
    @Column(name="post_comments")
    @JsonManagedReference
    private List<Comments> comments;


    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Categories getCategory() {
        return categories;
    }

    public void setCategory(Categories category) {
        this.categories = category;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public void addComments(Comments comment){
        if(comments == null)
          {
              comments = new ArrayList<>();
          }
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comments comment){
        if(comments != null){
            comments.remove(comment);
            comment.setPost(null);
        }
    }

    @Override
    public String toString() {
        return "Posts{" +
                "postId=" + postId +
                ", tittle='" + tittle + '\'' +
                ", content='" + content + '\'' +
                ", category=" + categories +
                ", user=" + user +
                ", createdOn=" + createdOn +
                ", image=" + image +
                ", comments=" + comments +
                '}';
    }
}
