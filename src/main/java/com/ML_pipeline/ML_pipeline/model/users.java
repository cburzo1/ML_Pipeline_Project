package com.ML_pipeline.ML_pipeline.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class authDB {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "username")
    String user_name;

    @Column(name = "password")
    String pass_word;

    @Column(name = "email")
    String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    @Column(name = "enabled")
    Boolean enabled;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
