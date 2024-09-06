package com.antonio.curso.springboot.jpa.springboot_jpa.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Embeddable
public class Audit {

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @PrePersist
    public void prePersist() {
        System.out.println("evento del cilo de vida del objet entity pre-presist");
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("evento del cilo de vida del objet entity pre-update");
        this.updatedAt = LocalDateTime.now();
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
