package com.lisbrown.lisbon_blog.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="comment_id")
    private Long commentId;
    @Column(name="content", nullable = false)
    private String content;
    @CreatedDate
    @Column(name="created_date")
    private Instant createdOn;
    @CreatedBy
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonBackReference
    private Users user;
    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    @JsonBackReference
    private Posts post;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", createdOn=" + createdOn +
                ", user=" + user +
                ", post=" + post +
                '}';
    }
}
