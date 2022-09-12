package com.example.instapic.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private  Post post;

    @Column(nullable = false)
    private String userName;
    @Column(columnDefinition = "text", nullable = false)
    private String message;
    @Column(nullable = false)
    private Long userId;
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void created(){
        this.createdDate = LocalDateTime.now();
    }



}
