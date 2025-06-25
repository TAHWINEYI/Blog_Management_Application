package com.lisbrown.lisbon_blog.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
public class Users{

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="user_id")
    private Long userId;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Email
    private String email;
    @CreatedDate
    @Column(name="date_created")
    private Instant createdAt;
    @Size(min =10)
    private String password;
    @Transient
    @Column(name="password_retry")
    private String passwordRetry;
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Set<Roles> roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Posts> posts;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Comments> comments;

    public String getFirstName() {
        return firstName;
    }

    public Set<Roles> getRoles() {
        return roles;
    }
    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public Users addPosts(Posts post) {
        posts.add(post);
        post.setUser(this);
        return this;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public Users addComments(Comments comment) {
        comments.add(comment);
        comment.setUser(this);
        return this;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPasswordRetry() {
        return passwordRetry;
    }

    public void setPasswordRetry(String passwordRetry) {
        this.passwordRetry = passwordRetry;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", password='" + password + '\'' +
                ", passwordRetry='" + passwordRetry + '\'' +
                ", roles=" + roles +
                ", posts=" + posts +
                ", comments=" + comments +
                '}';
    }
}
